package it.polimi.ingsw.cg26.client;

import it.polimi.ingsw.cg26.client.controller.Controller;
import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.client.ui.*;
import it.polimi.ingsw.cg26.client.ui.actions.AcquireDialog;
import it.polimi.ingsw.cg26.client.ui.actions.BuildDialog;
import it.polimi.ingsw.cg26.client.ui.actions.BuildKingDialog;
import it.polimi.ingsw.cg26.client.ui.actions.ChangeBPTDialog;
import it.polimi.ingsw.cg26.client.ui.actions.ElectDialog;
import it.polimi.ingsw.cg26.client.view.OutView;
import it.polimi.ingsw.cg26.client.view.rmi.ClientRMIInView;
import it.polimi.ingsw.cg26.client.view.rmi.ClientRMIOutView;
import it.polimi.ingsw.cg26.client.view.socket.ClientSocketInView;
import it.polimi.ingsw.cg26.client.view.socket.ClientSocketOutView;
import it.polimi.ingsw.cg26.common.commands.AcquireCommand;
import it.polimi.ingsw.cg26.common.commands.AdditionalMainActionCommand;
import it.polimi.ingsw.cg26.common.commands.BuildCommand;
import it.polimi.ingsw.cg26.common.commands.BuildKingCommand;
import it.polimi.ingsw.cg26.common.commands.ChangeBPTCommand;
import it.polimi.ingsw.cg26.common.commands.EngageAssistantCommand;
import it.polimi.ingsw.cg26.common.commands.Command;
import it.polimi.ingsw.cg26.common.dto.CityColorDTO;
import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import it.polimi.ingsw.cg26.common.rmi.ServerRMIViewInterface;
import it.polimi.ingsw.cg26.common.rmi.ServerRMIWelcomeViewInterface;
import it.polimi.ingsw.cg26.common.update.Update;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class Client extends Application implements it.polimi.ingsw.cg26.common.observer.Observer<Update> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final double RATIO = 2.0d / 1.7d;

    private static final String DEFAULT_IP = "127.0.0.1";

    private static final int DEFAULT_SOCKET_PORT = 29999;

    private static final int DEFAULT_RMI_PORT = 52365;

    private static final String INTERFACE_NAME = "WELCOME_VIEW";

    private OutView outView;

    private ExecutorService executor;

    private Model model;

    private Controller controller;

    private Map<CityDTO, CityPane> citiesPanes = new LinkedHashMap<>();
    
    private List<RewardTilePane> tilesPanes = new ArrayList<>();

    private Collection<Observer> observers;

    private static final List<Point2D> citiesOrigins = Arrays.asList(new Point2D(0.050, 0.060), new Point2D(0.035, 0.240), new Point2D(0.210, 0.110), new Point2D(0.200, 0.270), new Point2D(0.100, 0.380), new Point2D(0.350, 0.060), new Point2D(0.335, 0.240), new Point2D(0.400, 0.380), new Point2D(0.510, 0.110), new Point2D(0.500, 0.270), new Point2D(0.700, 0.060), new Point2D(0.680, 0.240), new Point2D(0.680, 0.400), new Point2D(0.810, 0.150), new Point2D(0.800, 0.350));

    private static final int KING_DECK_SIZE = 5;

    private Pane root;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        if (establishConnection())
            buildGUI(primaryStage);
    }

    /**
     * Displays a Dialog then tries to establish the desired connection
     * @return true iff the desired ui is gui
     */
    private boolean establishConnection() {

        Optional<Conf> result = buildDialog().showAndWait();
        //Optional<Conf> result = Optional.of(new Conf(true, "", true, "127.0.0.1"));

        if (!result.isPresent()) {
            Platform.exit();
            System.exit(0);
        }

        executor = Executors.newCachedThreadPool();
        model = new Model();
        controller = new Controller(model);
        executor.submit(controller);

        while (true) {
            try {
                if (result.get().socket)
                    outView = startSocketClient(result.get().ip, DEFAULT_SOCKET_PORT, result.get().name);
                else
                    outView = startRMIClient(result.get().ip, DEFAULT_RMI_PORT, result.get().name);
                break;
            } catch (Exception e) {
                log.error("Connection failed.",e);
                TextInputDialog ipDialog = new TextInputDialog();
                ipDialog.setTitle("Error");
                ipDialog.setHeaderText("Cannot connect to server, try another address");
                Optional<String> res = ipDialog.showAndWait();
                if (!res.isPresent()) {
                    Platform.exit();
                    System.exit(0);
                }
                result.get().ip = res.get();
            }
        }

        if(!result.get().gui) {
            CLI cli = new CLI(new Scanner(System.in), new PrintWriter(System.out), outView, model);
            model.registerObserver(cli);
            executor.submit(cli);
            return false;
        }
        model.registerObserver(this);
        return true;
    }

    /**
     * Builds and returns a dialog to ask for connection/ui settings
     * @return the dialog
     */
    private Dialog<Conf> buildDialog() {

        Dialog<Conf> dialog = new Dialog<>();
        dialog.setTitle("Welcome to Council of Four");
        dialog.setHeaderText("Select game configuration");

        Label label1 = new Label("UI: ");
        Label label2 = new Label("Name: ");
        Label label3 = new Label("ip: ");
        Label label4 = new Label("Connection type: ");

        TextField text1 = new TextField();
        TextField text2 = new TextField();
        text2.setText(DEFAULT_IP);

        ToggleGroup group1 = new ToggleGroup();
        RadioButton rb1 = new RadioButton("GUI");
        rb1.setToggleGroup(group1);
        rb1.setSelected(true);
        RadioButton rb2 = new RadioButton("CLI");
        rb2.setToggleGroup(group1);

        ToggleGroup group2 = new ToggleGroup();
        RadioButton rb3 = new RadioButton("Socket");
        rb3.setToggleGroup(group2);
        rb3.setSelected(true);
        RadioButton rb4 = new RadioButton("RMI");
        rb4.setToggleGroup(group2);

        GridPane grid = new GridPane();
        grid.setHgap(25.0);
        grid.setVgap(10.0);
        grid.add(label1, 1, 0);
        grid.add(rb1, 2, 0);
        grid.add(rb2, 3, 0);
        grid.add(label2, 1, 1);
        grid.add(text1, 2, 1, 2, 1);
        grid.add(label3, 1, 2);
        grid.add(text2, 2, 2, 2, 1);
        grid.add(label4, 1, 3);
        grid.add(rb3, 2, 3);
        grid.add(rb4, 3, 3);
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(b -> {
            if (b == buttonTypeOk)
                return new Conf(rb1.isSelected(), text1.getText(), rb3.isSelected(), text2.getText());
            return null;
        });

        return dialog;
    }

    /**
     * Establish the connection with the server through Sockets
     * @param ip is the ip/host name of the server
     * @param port is the port of the server
     * @param name is the name of the player that has to be communicated to the server
     * @return the OutView that works with Sockets
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private OutView startSocketClient(String ip, int port, String name) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(ip, port);
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

        ClientSocketInView inView = new ClientSocketInView(inputStream);
        inView.registerObserver(controller);
        executor.submit(inView);

        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

        log.info("Connection created, waiting to start...");

        outputStream.writeObject(name);

        return new ClientSocketOutView(outputStream);
    }

    /**
     * Establish the connection with the server through RMI
     * @param ip is the ip/host name of the server
     * @param port is the port of the server
     * @param name is the name of the player that has to be communicated to the server
     * @return the OutView that works with RMI
     * @throws RemoteException
     * @throws NotBoundException
     */
    private OutView startRMIClient(String ip, int port, String name) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(ip, port);
        ServerRMIWelcomeViewInterface welcomeView = (ServerRMIWelcomeViewInterface) registry.lookup(INTERFACE_NAME);

        ClientRMIInView inView = new ClientRMIInView();
        inView.registerObserver(controller);
        executor.submit(inView);

        ServerRMIViewInterface server = welcomeView.registerClient(inView, name);
        log.info("Connection created, waiting to start...");

        return new ClientRMIOutView(server);
    }

    /**
     * Builds the game main window
     * @param primaryStage
     */
    private void buildGUI(Stage primaryStage) {
        observers = new LinkedList<>();
        root = new AnchorPane();
        root.setStyle("-fx-background-image: url(" + getClass().getResource("/img/map.png") + ");" +
                "-fx-background-position: center;" +
                "-fx-background-size: 100% 100%;");

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        double maxWidth = RATIO * primaryScreenBounds.getHeight();
        double maxHeight = primaryScreenBounds.getHeight();

        primaryStage.setMaxWidth(maxWidth);
        primaryStage.setMaxHeight(maxHeight);
        primaryStage.setResizable(false);
        Scene scene = new Scene(root, maxWidth, maxHeight);

        while (model.getLocalPlayer() == null) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                log.error("Model not ready yet.", e);
                Thread.currentThread().interrupt();
            }
        }

        buildVictoryTrack();
        buildCities();
        buildBPT();
        buildBalconies();
        buildNobilityTrack();
        buildCoinsTrack();
        buildTiles();
        
        buildActionsPane();
        buildStatePane();
        buildChatPane();
        buildMarket();
        
        primaryStage.setTitle("Council of Four");
        primaryStage.setScene(scene);
        primaryStage.show();

        final Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, event -> refreshScene()),
                new KeyFrame(Duration.millis(500))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void refreshScene() {
    	buildTiles();
        for (Observer o: observers)
            o.update(null, null);
    }

    /**
     * Builds the cities
     */
    private void buildCities() {
        List<CityDTO> cities = new LinkedList<>();
        cities.addAll(model.getRegions().get(0).getCities());
        cities.addAll(model.getRegions().get(1).getCities());
        cities.addAll(model.getRegions().get(2).getCities());

        for (CityDTO city: cities) {
            Point2D o = citiesOrigins.get(cities.indexOf(city));
            Point2D origin = new Point2D(o.getX() * root.getWidth(), o.getY() * root.getHeight());
            CityPane cityPane = new CityPane(origin, 0.15 * root.getHeight(), city, model);
            citiesPanes.put(city, cityPane);
            observers.add(cityPane);
        }

        //moveKing(model.getKing());

        linkCities(cities);

        for (CityDTO city: citiesPanes.keySet()) {
            root.getChildren().add(citiesPanes.get(city));
        }
    }

    /**
     * Draws the routes between linked cities
     * @param cities is a list of CityDTO
     */
    private void linkCities(List<CityDTO> cities) {
        Collection<String> alreadyVisited = new LinkedList<>();
        for (CityDTO city: citiesPanes.keySet()) {
            for (String nearName: city.getNearCities()) {
                CityDTO nearCity = null;
                for (CityDTO c: citiesPanes.keySet()) {
                    if (c.getName().equalsIgnoreCase(nearName)) {
                        nearCity = c;
                        break;
                    }
                }
                if (nearCity != null && !alreadyVisited.contains(nearName)) {
                    Point2D p1 = citiesOrigins.get(cities.indexOf(city));
                    Point2D p2 = citiesOrigins.get(cities.indexOf(nearCity));
                    Point2D startPoint = new Point2D((p1.getX() + 0.075) * root.getWidth(), (p1.getY() + 0.075) * root.getHeight());
                    Point2D endPoint = new Point2D((p2.getX() + 0.075) * root.getWidth(), (p2.getY() + 0.075) * root.getHeight());

                    final Shape line = buildRoute(startPoint, endPoint);
                    root.getChildren().add(line);
                }
            }
            alreadyVisited.add(city.getName());
        }
    }

    /**
     * Builds a route between startPoint and endPoint
     * @param startPoint is the point where the route begins
     * @param endPoint is the point where the route ends
     * @return the street created
     */
    private Shape buildRoute(Point2D startPoint, Point2D endPoint) {

        double wobble = Math.sqrt((endPoint.getX() - startPoint.getX()) * (endPoint.getX() - startPoint.getX()) + (endPoint.getY() - startPoint.getY()) * (endPoint.getY() - startPoint.getY())) / 25 + 0.5;

        double r1 = Math.random();
        double r2 = Math.random();

        double xfactor = (Math.random() > 0.5 ? wobble : -wobble) * 3.0;
        double yfactor = (Math.random() > 0.5 ? wobble : -wobble) * 3.0;

        Point2D control1 = new Point2D((endPoint.getX() - startPoint.getX()) * r1 + startPoint.getX() + xfactor, (endPoint.getY() - startPoint.getY()) * r1 + startPoint.getY() + yfactor);
        Point2D control2 = new Point2D((endPoint.getX() - startPoint.getX()) * r2 + startPoint.getX() - xfactor, (endPoint.getY() - startPoint.getY()) * r2 + startPoint.getY() - yfactor);

        MoveTo startMove = new MoveTo(startPoint.getX(), startPoint.getY());
        CubicCurveTo curve = new CubicCurveTo(control1.getX(), control1.getY(),
                control2.getX(), control2.getY(),
                endPoint.getX(), endPoint.getY());

        InnerShadow shadow = new InnerShadow();
        shadow.setRadius(10.0);
        //shadow.setColor(Color.rgb(137, 122, 92));
        shadow.setColor(Color.web("#6B5832"));

        Path path = new Path(startMove, curve);
        path.setStrokeLineCap(StrokeLineCap.ROUND);
        path.setStroke(Color.rgb(172, 146, 83));
        path.setEffect(shadow);
        double strokeWidth = 15.0;
        path.setStrokeWidth(strokeWidth + (strokeWidth * (Math.random() - 0.5) / 8.0));
        path.setStrokeType(StrokeType.CENTERED);
        return path;
    }

    /**
     * Builds and displays the Business Permit Tiles
     */
    private void buildBPT() {
        //List<Point2D> bptOrigins = Arrays.asList(new Point2D(0.065, 0.587), new Point2D(0.140, 0.587), new Point2D(0.215, 0.587), new Point2D(0.364, 0.587), new Point2D(0.439, 0.587), new Point2D(0.513, 0.587), new Point2D(0.698, 0.587), new Point2D(0.773, 0.587), new Point2D(0.847, 0.587));
        List<Point2D> bptOrigins = Arrays.asList(new Point2D(0.067 * root.getWidth(), 0.595 * root.getHeight()), new Point2D(0.367 * root.getWidth(), 0.595 * root.getHeight()), new Point2D(0.701 * root.getWidth(), 0.595 * root.getHeight()));
        int i = 0;
        for (RegionDTO r: model.getRegions()) {
            BPTDeckPane p = new BPTDeckPane(bptOrigins.get(i), 0.215 * root.getWidth(), 0.085 * root.getHeight(), model, i);
            root.getChildren().addAll(p);
            observers.add(p);
            i++;
        }
    }

    /**
     * Builds and displays the Balconies
     */
    private void buildBalconies() {
        List<Point2D> balconiesOrigins = Arrays.asList(new Point2D(0.140, 0.676), new Point2D(0.439, 0.676), new Point2D(0.773, 0.676));

        BalconyPane kingBalcony = new BalconyPane(new Point2D(0.630 * root.getWidth(), 0.721 * root.getHeight()), 0.105 * root.getWidth(), 0.058 * root.getHeight(), model, 3);
    	observers.add(kingBalcony);
        root.getChildren().add(kingBalcony);
    	int i = 0;
    	for(RegionDTO r : model.getRegions()) {
    		BalconyPane balcony = new BalconyPane(new Point2D(balconiesOrigins.get(i).getX() * root.getWidth(), balconiesOrigins.get(i).getY() * root.getHeight()), 0.105 * root.getWidth(), 0.058 * root.getHeight(), model, i);
    		root.getChildren().add(balcony);
            observers.add(balcony);
    		i++;
    	}
    }

    /**
     * Builds and displays the position of the players on the Nobility track
     */
    private void buildNobilityTrack() {
        Point2D origin = new Point2D(0.0475 * root.getWidth(), 0.81 * root.getHeight());
        NobilityTrackPane track = new NobilityTrackPane(origin, 0.69 * root.getWidth(), 0.05 * root.getHeight(), model);
        observers.add(track);
        root.getChildren().add(track);
    }

    /**
     * Builds and displays the position of the players on the Coin track
     */
    private void buildCoinsTrack() {
        Point2D origin = new Point2D(0.035 * root.getWidth(), 0.875 * root.getHeight());
        CoinsTrackPane track = new CoinsTrackPane(origin, 0.72 * root.getWidth(), 0.05 * root.getHeight(), model);
        observers.add(track);
        root.getChildren().add(track);
    }

    /**
     * Builds and displays the position of the players on the Victory track
     */
    private void buildVictoryTrack() {
        VictoryTrackPane track = new VictoryTrackPane(root.getWidth(), root.getHeight(), 0.035 * root.getWidth(), model);
        observers.add(track);
        root.getChildren().add(track);
    }

    /**
     * Build and displays the colorBonuses tiles
     */
    private void buildColorBonuses() {
        List<Point2D> colorBonusesOrigins = Arrays.asList(new Point2D(0.751, 0.876), new Point2D(0.8, 0.87), new Point2D(0.849, 0.863), new Point2D(0.897, 0.858));

        for(Map.Entry<CityColorDTO, BonusDTO> t : model.getColorBonuses().entrySet()) {
    		String resource = null;
    		RewardTilePane rewardTile = new RewardTilePane(0.058 * root.getWidth(), 0.035 * root.getHeight(), t.getValue());
    		rewardTile.setRotate(45);
    		switch (t.getKey().getColor()) {
			case "iron":
				resource ="ironCityTile.png";
				AnchorPane.setLeftAnchor(rewardTile, colorBonusesOrigins.get(0).getX() * root.getWidth());
				AnchorPane.setTopAnchor(rewardTile, colorBonusesOrigins.get(0).getY() * root.getHeight());
				break;
			case "bronze":
				resource = "bronzeCityTile.png";
				AnchorPane.setLeftAnchor(rewardTile, colorBonusesOrigins.get(1).getX() * root.getWidth());
				AnchorPane.setTopAnchor(rewardTile, colorBonusesOrigins.get(1).getY() * root.getHeight());
				break;
			case "silver":
				resource = "silverCityTile.png";
				AnchorPane.setLeftAnchor(rewardTile, colorBonusesOrigins.get(2).getX() * root.getWidth());
				AnchorPane.setTopAnchor(rewardTile, colorBonusesOrigins.get(2).getY() * root.getHeight());
				break;
			case "gold":
				resource = "goldCityTile.png";
				AnchorPane.setLeftAnchor(rewardTile, colorBonusesOrigins.get(3).getX() * root.getWidth());
				AnchorPane.setTopAnchor(rewardTile, colorBonusesOrigins.get(3).getY() * root.getHeight());
				break;
			default:
				break;
			}
    		rewardTile.setStyle("-fx-background-image: url(" + getClass().getResource("/img/rewardTiles/" + resource) + ");" +
                    "-fx-background-position: center;" +
                    "-fx-background-size: 100% 100%;");
    		tilesPanes.add(rewardTile);
    	}
    }
    
    /**
     * Build and displays the king tiles
     */
    private void buildKingRewardTile() {
    	int index = KING_DECK_SIZE - model.getKingDeck().getTiles().size() + 1;
    	RewardTilePane rewardTile = new RewardTilePane(0.058 * root.getWidth(), 0.037 * root.getHeight(), model.getKingDeck().getTiles().get(0).getBonuses());
    	rewardTile.setRotate(45);
    	AnchorPane.setLeftAnchor(rewardTile, 0.885 * root.getWidth());
    	AnchorPane.setTopAnchor(rewardTile, 0.773 * root.getHeight());
    	rewardTile.setStyle("-fx-background-image: url(" + getClass().getResource("/img/rewardTiles/kingTile.png") + ");" +
                "-fx-background-position: center;" +
                "-fx-background-size: 100% 100%;");
		Label indexLabel = new Label();
        indexLabel.setTextFill(Color.WHITE);
        indexLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25.0));
        indexLabel.setText(Integer.toString(index) + "°");
        AnchorPane.setLeftAnchor(indexLabel, 0.2 * rewardTile.getPrefWidth());
        AnchorPane.setBottomAnchor(indexLabel, 0.2 * rewardTile.getPrefHeight());
        rewardTile.getChildren().add(indexLabel);
        tilesPanes.add(rewardTile);
    }
    
    private void buildRegionTileBonuses() {
    	for(RegionDTO r : model.getRegions()) {
    		String resource = null;
    		double offset = 0;
    		RewardTilePane rewardTile = new RewardTilePane(0.058 * root.getWidth(), 0.037 * root.getHeight(), r.getBonus());
    		if(r.getBonus().toString().isEmpty())
    			rewardTile.setVisible(false);
    		switch (r.getName()) {
			case "coast":
				resource = "coast";
				offset = 0;
				break;
			case "hills":
				resource = "hills";
				offset = 0.3;
				break;
			case "mountains":
				resource = "mountains";
				offset = 0.63;
				break;
			default:
				break;
			}
    		AnchorPane.setLeftAnchor(rewardTile, (0.250 + offset) * root.getWidth());
    		AnchorPane.setTopAnchor(rewardTile, 0.53 * root.getHeight());
    		rewardTile.setStyle("-fx-background-image: url(" + getClass().getResource("/img/rewardTiles/"+ resource +"RewardTile.png") + ");" +
                    "-fx-background-position: center;" +
                    "-fx-background-size: 100% 100%;");
    		tilesPanes.add(rewardTile);
    	}
    }
    
    private void buildTiles() {
    	root.getChildren().removeAll(tilesPanes);
    	tilesPanes.clear();
    	buildColorBonuses();
        buildKingRewardTile();
        buildRegionTileBonuses();
    	root.getChildren().addAll(tilesPanes);
    }
    
    private void buildMarket() {
    	ScrollPane market = new ScrollPane();
    	MarketPane m = new MarketPane(0.91 * root.getWidth(), 0.68 * root.getHeight(), model, outView);
    	AnchorPane.setBottomAnchor(market, 50.0);
        AnchorPane.setLeftAnchor(market, 50.0);
        market.setVisible(false);
        
        Pane showHidePane = new Pane();
        AnchorPane.setBottomAnchor(showHidePane, 50.0);
        AnchorPane.setLeftAnchor(showHidePane, 50.0);
        showHidePane.setPrefSize(20.0, 20.0);
        showHidePane.setStyle("-fx-background-color: azure");
        showHidePane.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> market.setVisible(true));
        market.setContent(m);
        market.addEventHandler(MouseEvent.MOUSE_EXITED, e -> market.setVisible(false));
        market.setStyle("-fx-background-color: transparent");
        root.getChildren().add(showHidePane);
        root.getChildren().add(market);
        observers.add(m);
    }

    /**
     * Builds and displays the panel where the user can perform actions
     */
    private void buildActionsPane() {
        ActionsPane pane = new ActionsPane();
        AnchorPane.setTopAnchor(pane, 50.0);
        AnchorPane.setRightAnchor(pane, 50.0);

        DropShadow shadow = new DropShadow();
        shadow.setOffsetY(3.0f);
        shadow.setRadius(50.0);
        shadow.setColor(Color.WHITE);

        Pane showHidePane = new Pane();
        AnchorPane.setTopAnchor(showHidePane, 50.0);
        AnchorPane.setRightAnchor(showHidePane, 50.0);
        showHidePane.setEffect(shadow);
        showHidePane.setPrefSize(75.0, 75.0);
        showHidePane.setStyle("-fx-background-image: url(" + getClass().getResource("/img/action.png") + ");-fx-background-position: center;-fx-background-size: 100%;");
        showHidePane.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> pane.setVisible(true));

        pane.getAcquire().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> acquire());
        pane.getBuildKing().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> buildKing());
        pane.getElectAsMainAction().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> electAsMainAction());
        pane.getBuild().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> build());
        pane.getEngageAssistant().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> engageAssistant());
        pane.getChangeBPT().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> changeBPT());
        pane.getElectAsQuickAction().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> electAsQuickAction());
        pane.getAdditionalMainAction().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> additionalMainAction());
        
        root.getChildren().add(showHidePane);
        root.getChildren().add(pane);
    }

    /**
     * Builds and displays the panel where the user can see the player's state
     */
    private void buildStatePane() {

        PlayersPane statePane = new PlayersPane(400.0, root.getHeight() - 100.0, model);
        observers.add(statePane);

        Pane showHidePane = new Pane();
        AnchorPane.setTopAnchor(showHidePane, 50.0);
        AnchorPane.setLeftAnchor(showHidePane, 50.0);
        showHidePane.setPrefSize(30.0, 30.0);
        showHidePane.setStyle("-fx-background-image: url(" + getClass().getResource("/img/user.png") + ");-fx-background-position: center;-fx-background-size: 100%;");
        showHidePane.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> statePane.setVisible(true));

        root.getChildren().add(showHidePane);
        root.getChildren().add(statePane);
    }

    /**
     * Builds and displays the panel where the user chat
     */
    private void buildChatPane() {
        ChatPane chatPane = new ChatPane(400.0, root.getHeight() / 2.0, model, outView, root);
        observers.add(chatPane);

        Pane showHidePane = new Pane();
        AnchorPane.setBottomAnchor(showHidePane, 35.0);
        AnchorPane.setRightAnchor(showHidePane, 50.0);
        showHidePane.setPrefSize(50.0, 50.0);
        showHidePane.setStyle("-fx-background-image: url(" + getClass().getResource("/img/chat.png") + ");-fx-background-position: center;-fx-background-size: 100%;");
        showHidePane.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> chatPane.setVisible(true));

        root.getChildren().add(showHidePane);
        root.getChildren().add(chatPane);
    }

    private void electAsMainAction() {
        Dialog<Command> d = new ElectDialog(true, model);
        Optional<Command> result = d.showAndWait();
        if (result.isPresent())
            outView.writeObject(result.get());
        System.out.println("electAsMainAction");
    }

    private void acquire() {
        Dialog<AcquireCommand> d = new AcquireDialog(root, model);
        Optional<AcquireCommand> result = d.showAndWait();
        if (result.isPresent())
            outView.writeObject(result.get());
        System.out.println("acquire");
    }

    private void build() {
    	Dialog<BuildCommand> d = new BuildDialog(model);
    	Optional<BuildCommand> result = d.showAndWait();
    	if(result.isPresent())
    		outView.writeObject(result.get());
        System.out.println("build");
    }

    private void buildKing() {
    	Dialog<BuildKingCommand> d = new BuildKingDialog(model);
    	Optional<BuildKingCommand> result = d.showAndWait();
    	if(result.isPresent())
    		outView.writeObject(result.get());
        System.out.println("buildKing");
    }

    private void engageAssistant() {
    	Dialog<EngageAssistantCommand> d = new Dialog<>();
    	d.setContentText("Are you sure you want to engage an assistant using 3 coins?");
    	ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        d.getDialogPane().getButtonTypes().add(buttonTypeOk);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        d.getDialogPane().getButtonTypes().add(buttonTypeCancel);
        d.setResultConverter(b -> {
        	if (b == buttonTypeOk)
                return new EngageAssistantCommand();
            return null;
        });
        Optional<EngageAssistantCommand> result = d.showAndWait();
        if (result.isPresent())
            outView.writeObject(result.get());
        System.out.println("engageAssistant");
    }

    private void changeBPT() {
    	Dialog<ChangeBPTCommand> d = new ChangeBPTDialog(model);
    	Optional<ChangeBPTCommand> result = d.showAndWait();
    	if (result.isPresent())
            outView.writeObject(result.get());
        System.out.println("changeBPT");
    }

    private void electAsQuickAction() {
        Dialog<Command> d = new ElectDialog(false, model);
        Optional<Command> result = d.showAndWait();
        if (result.isPresent())
            outView.writeObject(result.get());
        System.out.println("electAsQuickAction");
    }

    private void additionalMainAction() {
    	Dialog<AdditionalMainActionCommand> d = new Dialog<>();
    	d.setContentText("Are you sure you want to use 3 assistant to have one more main action?");
    	ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        d.getDialogPane().getButtonTypes().add(buttonTypeOk);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        d.getDialogPane().getButtonTypes().add(buttonTypeCancel);
        d.setResultConverter(b -> {
        	if (b == buttonTypeOk)
                return new AdditionalMainActionCommand();
            return null;
        });
        Optional<AdditionalMainActionCommand> result = d.showAndWait();
        if (result.isPresent())
            outView.writeObject(result.get());
        System.out.println("additionalMainAction");
    }

    private void foldQuickAction() {
        System.out.println("foldQuickAction");
    }
    
    public static void main(String[] args) {
    	launch(args);
    }

    @Override
    public void update(Update o) {

    }

    /**
     * Inner class used for the configuration dialog
     */
    class Conf {
        boolean gui;
        String name;
        boolean socket;
        String ip;

        public Conf(boolean gui, String name, boolean socket, String ip) {
            this.gui = gui;
            this.name = name;
            this.socket = socket;
            this.ip = ip;
        }
    }
}
