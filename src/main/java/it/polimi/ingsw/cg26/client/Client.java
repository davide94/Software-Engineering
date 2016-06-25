package it.polimi.ingsw.cg26.client;

import it.polimi.ingsw.cg26.client.controller.Controller;
import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.client.ui.*;
import it.polimi.ingsw.cg26.client.view.OutView;
import it.polimi.ingsw.cg26.client.view.rmi.ClientRMIInView;
import it.polimi.ingsw.cg26.client.view.rmi.ClientRMIOutView;
import it.polimi.ingsw.cg26.client.view.socket.ClientSocketInView;
import it.polimi.ingsw.cg26.client.view.socket.ClientSocketOutView;
import it.polimi.ingsw.cg26.common.dto.*;
import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import it.polimi.ingsw.cg26.common.rmi.ServerRMIViewInterface;
import it.polimi.ingsw.cg26.common.rmi.ServerRMIWelcomeViewInterface;
import it.polimi.ingsw.cg26.common.update.Update;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
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

    private static final List<Point2D> citiesOrigins = Arrays.asList(new Point2D(0.050, 0.060), new Point2D(0.035, 0.240), new Point2D(0.210, 0.110), new Point2D(0.200, 0.270), new Point2D(0.100, 0.380), new Point2D(0.350, 0.060), new Point2D(0.335, 0.240), new Point2D(0.400, 0.380), new Point2D(0.510, 0.110), new Point2D(0.500, 0.270), new Point2D(0.700, 0.060), new Point2D(0.680, 0.240), new Point2D(0.680, 0.400), new Point2D(0.810, 0.150), new Point2D(0.800, 0.350));

    private static final List<Point2D> bptOrigins = Arrays.asList(new Point2D(0.065, 0.587), new Point2D(0.140, 0.587), new Point2D(0.215, 0.587), new Point2D(0.364, 0.587), new Point2D(0.439, 0.587), new Point2D(0.513, 0.587), new Point2D(0.698, 0.587), new Point2D(0.773, 0.587), new Point2D(0.847, 0.587));
    
    private static final List<Point2D> balconiesOrigins = Arrays.asList(new Point2D(0.140, 0.676), new Point2D(0.439, 0.676), new Point2D(0.773, 0.676));

    private static final List<Point2D> colorBonusesOrigins = Arrays.asList(new Point2D(0.749, 0.85), new Point2D(0.798, 0.842), new Point2D(0.847, 0.836), new Point2D(0.895, 0.83));
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

        //Optional<Conf> result = buildDialog().showAndWait();
        Optional<Conf> result = Optional.of(new Conf(true, "", true, "127.0.0.1"));

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
        AnchorPane root = new AnchorPane();
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

        buildCities(root);
        buildBPT(root);
        buildBalconies(root);
        buildNobilityTrack(root);
        buildColorBonuses(root);
        buildCoinsTrack(root);

        buildActionsPane(root);
        buildStatePane(root);
        buildChatPane(root);

        primaryStage.setTitle("Council of Four");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Builds the cities
     * @param root is the root Pane
     */
    private void buildCities(Pane root) {
        while (model.getRegions() == null)
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        List<CityDTO> cities = new LinkedList<>();
        cities.addAll(model.getRegions().get(0).getCities());
        cities.addAll(model.getRegions().get(1).getCities());
        cities.addAll(model.getRegions().get(2).getCities());

        for (CityDTO city: cities) {
            Point2D o = citiesOrigins.get(cities.indexOf(city));
            Point2D origin = new Point2D(o.getX() * root.getWidth(), o.getY() * root.getHeight());
            citiesPanes.put(city, new CityPane(origin, 0.15 * root.getHeight(), city));
        }

        moveKing(model.getKing());

        linkCities(root, cities);

        for (CityDTO city: citiesPanes.keySet()) {
            root.getChildren().add(citiesPanes.get(city));
        }
    }

    /**
     * Draws the routes between linked cities
     * @param root is the root Pane
     * @param cities is a list of CityDTO
     */
    private void linkCities(Pane root, List<CityDTO> cities) {
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
     * @param root is the root Pane
     */
    private void buildBPT(Pane root) {
        List<String> bptUrls = Arrays.asList("BPTCoast.png", "BPTHills.png", "BPTMountain.png");

        int i = 0;
        for (RegionDTO r: model.getRegions()) {
            Pane coveredBPT = new Pane();
            DropShadow shadow = new DropShadow();
            shadow.setRadius(4.0);
            shadow.setColor(Color.BLACK);
            coveredBPT.setEffect(shadow);
            coveredBPT.setPrefSize(0.065 * root.getWidth(), 0.075 * root.getWidth());
            coveredBPT.setMaxSize(0.065 * root.getWidth(), 0.075 * root.getWidth());
            coveredBPT.setStyle("-fx-background-image: url(" + getClass().getResource("/img/coveredBPT/" + bptUrls.get(i / 3)) + ");" +
                    "-fx-background-position: center;" +
                    "-fx-background-size: 100% 100%;");
            AnchorPane.setLeftAnchor(coveredBPT, bptOrigins.get(i).getX() * root.getWidth());
            AnchorPane.setTopAnchor(coveredBPT, bptOrigins.get(i).getY() * root.getHeight());
            root.getChildren().add(coveredBPT);
            i++;
            for (BusinessPermissionTileDTO t: r.getDeck().getOpenCards()) {
                Pane bpt = new BPTPane(0.065 * root.getWidth(), 0.075 * root.getWidth(), t);
                AnchorPane.setLeftAnchor(bpt, bptOrigins.get(i).getX() * root.getWidth());
                AnchorPane.setTopAnchor(bpt, bptOrigins.get(i).getY() * root.getHeight());
                root.getChildren().add(bpt);
                i++;
            }
        }
    }

    /**
     * Builds and displays the Balconies
     * @param root is the root Pane
     */
    private void buildBalconies(Pane root) {
    	HBox kingBalcony = new BalconyPane(new Point2D(0.630 * root.getWidth(), 0.721 * root.getHeight()), 0.105 * root.getWidth(), 0.058 * root.getHeight(), model.getKingBalcony());
    	root.getChildren().add(kingBalcony);
    	int i = 0;
    	for(RegionDTO r : model.getRegions()) {
    		HBox balcony = new BalconyPane(new Point2D(balconiesOrigins.get(i).getX() * root.getWidth(), balconiesOrigins.get(i).getY() * root.getHeight()), 0.105 * root.getWidth(), 0.058 * root.getHeight(), r.getBalcony());
    		root.getChildren().add(balcony);
    		i++;
    	}
    }

    private void buildNobilityTrack(Pane root) {
        Point2D origin = new Point2D(0.0475 * root.getWidth(), 0.81 * root.getHeight());
        Pane track = new NobilityTrackPane(origin, 0.69 * root.getWidth(), 0.05 * root.getHeight(), model.getNobilityTrack(), model.getPlayers());
        root.getChildren().add(track);
    }

    private void buildCoinsTrack(Pane root) {
        Point2D origin = new Point2D(0.035 * root.getWidth(), 0.875 * root.getHeight());
        Pane track = new CoinsTrack(origin, 0.72 * root.getWidth(), 0.05 * root.getHeight(), model.getPlayers());
        root.getChildren().add(track);
    }

    private void buildColorBonuses(Pane root) {
    	for(Map.Entry<CityColorDTO, BonusDTO> t : model.getColorBonuses().entrySet()) {
    		AnchorPane rewardTile = new AnchorPane();
    		rewardTile.setPrefSize(0.058 * root.getWidth(), 0.035 * root.getHeight());
			rewardTile.setRotate(45);
    		switch (t.getKey().getColor()) {
			case "iron":
				rewardTile.setStyle("-fx-background-image: url(" + getClass().getResource("/img/rewardTiles/ironCityTile.png") + ");" +
	                    "-fx-background-position: center;" +
	                    "-fx-background-size: 100% 100%;");
				AnchorPane.setLeftAnchor(rewardTile, colorBonusesOrigins.get(0).getX() * root.getWidth());
				AnchorPane.setTopAnchor(rewardTile, colorBonusesOrigins.get(0).getY() * root.getHeight());
				break;
			case "silver":
				rewardTile.setStyle("-fx-background-image: url(" + getClass().getResource("/img/rewardTiles/bronzeCityTile.png") + ");" +
	                    "-fx-background-position: center;" +
	                    "-fx-background-size: 100% 100%;");
				AnchorPane.setLeftAnchor(rewardTile, colorBonusesOrigins.get(1).getX() * root.getWidth());
				AnchorPane.setTopAnchor(rewardTile, colorBonusesOrigins.get(1).getY() * root.getHeight());
				break;
			case "bronze":
				rewardTile.setStyle("-fx-background-image: url(" + getClass().getResource("/img/rewardTiles/silverCityTile.png") + ");" +
	                    "-fx-background-position: center;" +
	                    "-fx-background-size: 100% 100%;");
				AnchorPane.setLeftAnchor(rewardTile, colorBonusesOrigins.get(2).getX() * root.getWidth());
				AnchorPane.setTopAnchor(rewardTile, colorBonusesOrigins.get(2).getY() * root.getHeight());
				break;
			case "gold":
				rewardTile.setStyle("-fx-background-image: url(" + getClass().getResource("/img/rewardTiles/goldCityTile.png") + ");" +
	                    "-fx-background-position: center;" +
	                    "-fx-background-size: 100% 100%;");
				AnchorPane.setLeftAnchor(rewardTile, colorBonusesOrigins.get(3).getX() * root.getWidth());
				AnchorPane.setTopAnchor(rewardTile, colorBonusesOrigins.get(3).getY() * root.getHeight());
				break;
			default:
				break;
			}
    		root.getChildren().add(rewardTile);
    	}
    }

    /**
     * Updates the position of the king's visual representation to the actual king's position
     * @param king is a KingDTO
     */
    private void moveKing(KingDTO king) {
    	for(Map.Entry<CityDTO, CityPane> c : citiesPanes.entrySet()){
    		if(c.getKey().getName().equals(king.getCurrentCity()))
    			c.getValue().getKing().setVisible(true);
    		else c.getValue().getKing().setVisible(false);
    	}
    	king.getCurrentCity();
    }

    /**
     * Builds and displays the panel where the user can perform actions
     * @param root is the root Pane
     */
    private void buildActionsPane(Pane root) {
        DropShadow shadow = new DropShadow();
        shadow.setOffsetY(3.0f);
        shadow.setRadius(50.0);
        shadow.setColor(Color.BLACK);

        AnchorPane pane = new AnchorPane();
        pane.setEffect(shadow);
        AnchorPane.setTopAnchor(pane, 50.0);
        AnchorPane.setRightAnchor(pane, 50.0);
        pane.setPrefWidth(400.0);
        pane.setPrefHeight(310.0);
        pane.setVisible(false);
        pane.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actions.jpg") + ");" +
                      "-fx-background-position: center;" +
                      "-fx-background-size: 100% 100%;");
        pane.addEventHandler(MouseEvent.MOUSE_EXITED, e -> pane.setVisible(false));

        Pane showHidePane = new Pane();
        AnchorPane.setTopAnchor(showHidePane, 50.0);
        AnchorPane.setRightAnchor(showHidePane, 50.0);
        showHidePane.setPrefSize(20.0, 20.0);
        showHidePane.setStyle("-fx-background-color: azure");
        showHidePane.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> pane.setVisible(true));

        Button p1 = new Button();
        p1.setPrefWidth(200);
        p1.setPrefHeight(60);
        p1.setStyle("-fx-background-color: transparent");
        p1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> acquire());
        Button p2 = new Button();
        p2.setPrefWidth(200);
        p2.setPrefHeight(60);
        p2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> buildKing());
        p2.setStyle("-fx-background-color: transparent");
        Button p3 = new Button();
        p3.setPrefWidth(200);
        p3.setPrefHeight(60);
        p3.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> electAsMainAction());
        p3.setStyle("-fx-background-color: transparent");
        Button p4 = new Button();
        p4.setPrefWidth(200);
        p4.setPrefHeight(60);
        p4.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> build());
        p4.setStyle("-fx-background-color: transparent");
        Button s1 = new Button();
        s1.setPrefWidth(200);
        s1.setPrefHeight(60);
        s1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> engageAssistant());
        s1.setStyle("-fx-background-color: transparent");
        Button s2 = new Button();
        s2.setPrefWidth(200);
        s2.setPrefHeight(60);
        s2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> changeBPT());
        s2.setStyle("-fx-background-color: transparent");
        Button s3 = new Button();
        s3.setPrefWidth(200);
        s3.setPrefHeight(60);
        s3.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> electAsQuickAction());
        s3.setStyle("-fx-background-color: transparent");
        Button s4 = new Button();
        s4.setPrefWidth(200);
        s4.setPrefHeight(60);
        s4.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> additionalMainAction());
        s4.setStyle("-fx-background-color: transparent");

        AnchorPane.setLeftAnchor(p4, 0.0);
        AnchorPane.setBottomAnchor(p4, 0.0);
        AnchorPane.setLeftAnchor(p3, 0.0);
        AnchorPane.setBottomAnchor(p3, 60.0);
        AnchorPane.setLeftAnchor(p2, 0.0);
        AnchorPane.setBottomAnchor(p2, 120.0);
        AnchorPane.setLeftAnchor(p1, 0.0);
        AnchorPane.setBottomAnchor(p1, 180.0);
        AnchorPane.setRightAnchor(s4, 0.0);
        AnchorPane.setBottomAnchor(s4, 0.0);
        AnchorPane.setRightAnchor(s3, 0.0);
        AnchorPane.setBottomAnchor(s3, 60.0);
        AnchorPane.setRightAnchor(s2, 0.0);
        AnchorPane.setBottomAnchor(s2, 120.0);
        AnchorPane.setRightAnchor(s1, 0.0);
        AnchorPane.setBottomAnchor(s1, 180.0);

        pane.getChildren().addAll(p1, p2, p3, p4, s1, s2, s3, s4);
        root.getChildren().add(showHidePane);
        root.getChildren().add(pane);
    }

    /**
     * Builds and displays the panel where the user can see the player's state
     * @param root is the root Pane
     */
    private void buildStatePane(Pane root) {
        DropShadow shadow = new DropShadow();
        shadow.setOffsetY(3.0f);
        shadow.setRadius(50.0);
        shadow.setColor(Color.BLACK);

        ScrollPane scrollPane = new ScrollPane();
        GridPane pane = new GridPane();
        AnchorPane.setTopAnchor(scrollPane, 50.0);
        AnchorPane.setLeftAnchor(scrollPane, 50.0);
        scrollPane.setPrefWidth(400.0);
        scrollPane.setPrefHeight(root.getHeight() - 100.0);
        scrollPane.setVisible(false);
        scrollPane.setEffect(shadow);
        scrollPane.setStyle("-fx-background-color: azure");
        scrollPane.addEventHandler(MouseEvent.MOUSE_EXITED, e -> scrollPane.setVisible(false));

        Pane showHidePane = new Pane();
        AnchorPane.setTopAnchor(showHidePane, 50.0);
        AnchorPane.setLeftAnchor(showHidePane, 50.0);
        showHidePane.setPrefSize(20.0, 20.0);
        showHidePane.setStyle("-fx-background-color: azure");
        showHidePane.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> scrollPane.setVisible(true));

        pane.add(new Label("You: "), 0, 0);
        pane.add(buildPlayerPane(model.getLocalPlayer()), 1, 1);
        pane.add(new Label("Other Players: "), 0, 2);

        scrollPane.setContent(pane);
        root.getChildren().add(showHidePane);
        root.getChildren().add(scrollPane);
    }

    /**
     * Builds and returns a pane displaying the Player's state
     * @param player is a PlayerDTO
     * @return the pane
     */
    private Pane buildPlayerPane(PlayerDTO player) {
        GridPane playerPane = new GridPane();
        playerPane.add(new Label("Name: "), 1, 1);
        playerPane.add(new Label(player.getName()), 2, 1);
        playerPane.add(new Label("State: "), 1, 2);
        playerPane.add(new Label(player.isOnline()? "online" : "offline"), 2, 2);
        playerPane.add(new Label("Victory Points number: "), 1, 3);
        playerPane.add(new Label(Integer.toString(player.getVictoryPoints())), 2, 3);
        playerPane.add(new Label("Coins number: "), 1, 4);
        playerPane.add(new Label(Integer.toString(player.getCoins())), 2, 4);
        playerPane.add(new Label("Position in Nobility Track: "), 1, 5);
        playerPane.add(new Label(Integer.toString(player.getNobilityCell())), 2, 5);
        playerPane.add(new Label("Assistants number: "), 1, 6);
        playerPane.add(new Label(Integer.toString(player.getAssistantsNumber())), 2, 6);
        playerPane.add(new Label("Remaining Main Actions: "), 1, 7);
        playerPane.add(new Label(Integer.toString(player.getRemainingMainActions())), 2, 7);
        playerPane.add(new Label("Remaining Quick Actions: "), 1, 8);
        playerPane.add(new Label(Integer.toString(player.getRemainingQuickActions())), 2, 8);
        // TODO: remaining things
        return playerPane;
    }

    /**
     * Builds and displays the panel where the user chat
     * @param root is the root Pane
     */
    private void buildChatPane(Pane root) {
        DropShadow shadow = new DropShadow();
        shadow.setOffsetY(3.0f);
        shadow.setRadius(50.0);
        shadow.setColor(Color.BLACK);

        AnchorPane pane = new AnchorPane();
        AnchorPane.setBottomAnchor(pane, 50.0);
        AnchorPane.setRightAnchor(pane, 50.0);
        pane.setPrefWidth(400.0);
        pane.setPrefHeight(root.getHeight() / 2.0);
        pane.setVisible(false);
        pane.setEffect(shadow);
        pane.setStyle("-fx-background-color: azure");
        pane.addEventHandler(MouseEvent.MOUSE_EXITED, e -> pane.setVisible(false));

        Pane showHidePane = new Pane();
        AnchorPane.setBottomAnchor(showHidePane, 50.0);
        AnchorPane.setRightAnchor(showHidePane, 50.0);
        showHidePane.setPrefSize(20.0, 20.0);
        showHidePane.setStyle("-fx-background-color: azure");
        showHidePane.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> pane.setVisible(true));

        root.getChildren().add(showHidePane);
        root.getChildren().add(pane);
    }

    private void electAsMainAction() {
        System.out.println("electAsMainAction");
    }

    private void acquire() {
        System.out.println("acquire");
    }

    private void build() {
        System.out.println("build");
    }

    private void buildKing() {
        System.out.println("buildKing");
    }

    private void engageAssistant() {
        System.out.println("engageAssistant");
    }

    private void changeBPT() {
        System.out.println("changeBPT");
    }

    private void electAsQuickAction() {
        System.out.println("electAsQuickAction");
    }

    private void additionalMainAction() {
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
