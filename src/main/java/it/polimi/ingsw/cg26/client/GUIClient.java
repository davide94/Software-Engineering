package it.polimi.ingsw.cg26.client;

import it.polimi.ingsw.cg26.client.controller.Controller;
import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.client.view.OutView;
import it.polimi.ingsw.cg26.client.view.rmi.ClientRMIInView;
import it.polimi.ingsw.cg26.client.view.rmi.ClientRMIOutView;
import it.polimi.ingsw.cg26.client.view.socket.ClientSocketInView;
import it.polimi.ingsw.cg26.client.view.socket.ClientSocketOutView;
import it.polimi.ingsw.cg26.client.view.ui.CityPane;
import it.polimi.ingsw.cg26.common.dto.*;
import it.polimi.ingsw.cg26.common.dto.bonusdto.BonusDTO;
import it.polimi.ingsw.cg26.common.rmi.ServerRMIViewInterface;
import it.polimi.ingsw.cg26.common.rmi.ServerRMIWelcomeViewInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
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
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.KeyStore.Entry;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class GUIClient extends Application {

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

    private static final List<Point2D> bptOrigins = Arrays.asList(new Point2D(0.140, 0.587), new Point2D(0.215, 0.587), new Point2D(0.439, 0.587), new Point2D(0.513, 0.587), new Point2D(0.773, 0.587), new Point2D(0.847, 0.587));
    
    private static final List<Point2D> balconiesOrigins = Arrays.asList(new Point2D(0.140, 0.676), new Point2D(0.439, 0.676), new Point2D(0.773, 0.676));
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        initialConfiguration();

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

        constructCities(root);

        int i = 0;
        for (RegionDTO r: model.getRegions()) {
            for (BusinessPermissionTileDTO t: r.getDeck().getOpenCards()) {
                Pane bpt = constructBPT(0.065 * root.getWidth(), 0.075 * root.getWidth(), t);
                AnchorPane.setLeftAnchor(bpt, bptOrigins.get(i).getX() * root.getWidth());
                AnchorPane.setTopAnchor(bpt, bptOrigins.get(i).getY() * root.getHeight());
                root.getChildren().add(bpt);
                i++;
            }
        }
        
        constructCoveredBPT(root);
        
        constructBalconies(root);
        
        moveKing(model.getKing());

        constructActionsPane(root);
        constructStatePane(root);
        constructChatPane(root);

        primaryStage.setTitle("Council of Four");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initialConfiguration() {
        class Conf {
            String name;
            boolean socket;
            String ip;

            public Conf(String name, boolean socket, String ip) {
                this.name = name;
                this.socket = socket;
                this.ip = ip;
            }
        }

        Dialog<Conf> dialog = new Dialog<>();
        dialog.setTitle("Welcome to Council of Four");
        dialog.setHeaderText("Select game configuration");

        Label label1 = new Label("Name: ");
        Label label2 = new Label("ip: ");
        Label label3 = new Label("Connection type: ");

        TextField text1 = new TextField();
        TextField text2 = new TextField();
        text2.setText(DEFAULT_IP);

        ToggleGroup group = new ToggleGroup();
        RadioButton rb1 = new RadioButton("Socket");
        rb1.setToggleGroup(group);
        rb1.setSelected(true);
        RadioButton rb2 = new RadioButton("RMI");
        rb2.setToggleGroup(group);

        GridPane grid = new GridPane();
        grid.setHgap(25.0);
        grid.setVgap(10.0);
        grid.add(label1, 1, 1);
        grid.add(text1, 2, 1, 2, 1);
        grid.add(label2, 1, 2);
        grid.add(text2, 2, 2, 2, 1);
        grid.add(label3, 1, 3);
        grid.add(rb1, 2, 3);
        grid.add(rb2, 3, 3);
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(b -> {
            if (b == buttonTypeOk)
                return new Conf(text1.getText(), rb1.isSelected(), text2.getText());
            return null;
        });

        Optional<Conf> result = dialog.showAndWait();

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
    }

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

    private void constructCities(Pane root) {
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
            createCity(root, citiesOrigins.get(cities.indexOf(city)), cities.get(cities.indexOf(city)));
        }

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
                    double offset = 0.075 * root.getHeight();
                    final Shape[] line = {createRoute(p1.getX() * root.getWidth() + offset, p1.getY() * root.getHeight() + offset, p2.getX() * root.getWidth() + offset, p2.getY() * root.getHeight() + offset)};
                    root.getChildren().add(line[0]);
                    /*root.widthProperty().addListener(new ChangeListener<Number>() {
                        @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                            root.getChildren().remove(line[0]);
                            line[0] = createRoute(p1.getX() * root.getWidth() + offset, p1.getY() * root.getHeight() + offset, p2.getX() * root.getWidth() + offset, p2.getY() * root.getHeight() + offset);
                            root.getChildren().add(line[0]);
                        }
                    });
                    root.heightProperty().addListener(new ChangeListener<Number>() {
                        @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                            root.getChildren().remove(line[0]);
                            line[0] = createRoute(p1.getX() * root.getWidth() + offset, p1.getY() * root.getHeight() + offset, p2.getX() * root.getWidth() + offset, p2.getY() * root.getHeight() + offset);
                            root.getChildren().add(line[0]);
                        }
                    });*/
                }
            }
            alreadyVisited.add(city.getName());
        }

        for (CityDTO city: citiesPanes.keySet()) {
            root.getChildren().add(citiesPanes.get(city));
        }
    }

    public Shape createRoute(double x1, double y1, double x2, double y2) {
        Point2D startPoint = new Point2D(x1, y1);
        Point2D endPoint = new Point2D(x2, y2);

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

    private void createCity(Pane root, Point2D origin, CityDTO city) {
    	CityPane pane = new CityPane(root, city);
        AnchorPane.setLeftAnchor(pane, origin.getX() * root.getWidth());
        AnchorPane.setTopAnchor(pane, origin.getY() * root.getHeight());
        pane.setPrefSize(0.15 * root.getHeight(), 0.15 * root.getHeight());
        // create city bonus
        if (!city.getBonuses().toString().isEmpty()) {
            Pane bonusPane = constructBonus(0.04 * root.getHeight(), 0.04 * root.getHeight(), city.getBonuses());
            AnchorPane.setLeftAnchor(bonusPane, 0.030 * root.getHeight());
            AnchorPane.setTopAnchor(bonusPane, 0.020 * root.getHeight());
            //bonusPane.setRotate((new Random().nextDouble() - 0.5) * 60.0);
            pane.getChildren().add(bonusPane);
        }
        citiesPanes.put(city, pane);
        //root.getChildren().add(pane);
    }

    private Pane constructBonus(double width, double height, BonusDTO bonus) {
        GridPane pane = new GridPane();
        pane.setPrefWidth(width);
        pane.setPrefHeight(height);
        List<String> bonusesStrings = Arrays.asList(bonus.toString().split(","));
        int i = 0;
        for (String bonusString: bonusesStrings) {
            AnchorPane bonusPane = new AnchorPane();
            double bonusSize = bonusesStrings.size() == 1 ? width * 0.75 : width /(double) bonusesStrings.size();
            bonusPane.setPrefSize(bonusSize, bonusSize);
            String styleString = "";
            if (bonusString.contains("Assistants"))
                styleString += "-fx-background-image: url(" + getClass().getResource("/img/bonuses/assistants.png") + ");";
            if (bonusString.contains("Cards"))
                styleString += "-fx-background-image: url(" + getClass().getResource("/img/bonuses/cards.png") + ");";
            if (bonusString.contains("Coins"))
                styleString += "-fx-background-image: url(" + getClass().getResource("/img/bonuses/coins.png") + ");";
            if (bonusString.contains("Main"))
                styleString += "-fx-background-image: url(" + getClass().getResource("/img/bonuses/main.png") + ");";
            if (bonusString.contains("Nobility"))
                styleString += "-fx-background-image: url(" + getClass().getResource("/img/bonuses/nobility.png") + ");";
            if (bonusString.contains("Take BPT"))
                styleString += "-fx-background-image: url(" + getClass().getResource("/img/bonuses/TakeTileBonus.png") + ");";
            if (bonusString.contains("Take Player"))
                styleString += "-fx-background-image: url(" + getClass().getResource("/img/bonuses/TakeCityBonus.png") + ");";
            if (bonusString.contains("Take Your"))
                styleString += "-fx-background-image: url(" + getClass().getResource("/img/bonuses/takeYour.png") + ");";
            if (bonusString.contains("Victory"))
                styleString += "-fx-background-image: url(" + getClass().getResource("/img/bonuses/victory.png") + ");";
            styleString += "-fx-background-position: center;-fx-background-size: 100%; -fx-background-repeat: no-repeat;";
            //System.out.println(bonusString);
            int j = 0;
            while (true) {
                if (bonusString.charAt(j) > 47 && bonusString.charAt(j) < 58)
                    break;
                j++;
            }
            bonusPane.setStyle(styleString);
            Label multiplicityLabel = new Label();
            AnchorPane.setLeftAnchor(multiplicityLabel, bonusSize / 3.0);
            AnchorPane.setTopAnchor(multiplicityLabel, bonusSize / 4.0);
            multiplicityLabel.setTextFill(Color.WHITE);
            multiplicityLabel.setText(new String(new char[]{bonusString.charAt(j)}));
            bonusPane.getChildren().add(multiplicityLabel);
            pane.add(bonusPane, i, 1);
            i++;
        }
        return pane;
    }

    private Pane constructBPT(double width, double height, BusinessPermissionTileDTO tile) {
        AnchorPane pane = new AnchorPane();
        //pane.setRotate((new Random().nextDouble() - 0.5) * 15.0);
        DropShadow shadow = new DropShadow();
        shadow.setRadius(4.0);
        shadow.setColor(Color.BLACK);
        pane.setEffect(shadow);

        pane.setPrefSize(width, height);
        pane.setMaxSize(width, height);
        pane.setStyle("-fx-background-image: url(" + getClass().getResource("/img/cards/bpt.png") + ");" +
                      "-fx-background-position: center;" +
                      "-fx-background-size: 100% 100%;");
        //System.out.println(tile.toString());
        String cities = "";
        for(String city: tile.getCities()) {
            cities += new String(new char[]{city.charAt(0)}).toUpperCase() + "/";
        }

        if (cities.length() > 0) {
            cities = cities.substring(0, cities.length()-1);
        }

        Label label = new Label(cities);
        Font goudyMedieval = Font.loadFont(getClass().getResource("/fonts/goudy_medieval/Goudy_Mediaeval_DemiBold.ttf").toExternalForm(), 0.25 * width);
        label.setFont(goudyMedieval);
        AnchorPane.setLeftAnchor(label, width / 2.0 - tile.getCities().size() * width / 8.0);
        AnchorPane.setTopAnchor(label, height / 6.0);
        pane.getChildren().add(label);

        Pane bonusPane = constructBonus(width / 2.0, width / 2, tile.getBonuses());
        AnchorPane.setLeftAnchor(bonusPane, width / 4.0);
        AnchorPane.setBottomAnchor(bonusPane, 0.0);
        pane.getChildren().add(bonusPane);

        return pane;
    }
    
    private void constructCoveredBPT(Pane root) {
    	List<Point2D> coveredBPTOrigins = Arrays.asList(new Point2D(0.065, 0.587), new Point2D(0.364, 0.587), new Point2D(0.698, 0.587));
    	List<String> bptUrls = Arrays.asList("BPTCoast.png", "BPTHills.png", "BPTMountain.png");
    	for(int i = 0; i<coveredBPTOrigins.size(); i++) {
    		Pane coveredBPT = new Pane();
    		DropShadow shadow = new DropShadow();
    		shadow.setRadius(4.0);
    		shadow.setColor(Color.BLACK);
    		coveredBPT.setEffect(shadow);
    		coveredBPT.setPrefSize(0.065 * root.getWidth(), 0.075 * root.getWidth());
    		coveredBPT.setMaxSize(0.065 * root.getWidth(), 0.075 * root.getWidth());
    		coveredBPT.setStyle("-fx-background-image: url(" + getClass().getResource("/img/coveredBPT/" + bptUrls.get(i)) + ");" +
    				"-fx-background-position: center;" +
    				"-fx-background-size: 100% 100%;");
    		AnchorPane.setLeftAnchor(coveredBPT, coveredBPTOrigins.get(i).getX() * root.getWidth());
    		AnchorPane.setTopAnchor(coveredBPT, coveredBPTOrigins.get(i).getY() * root.getHeight());
    		root.getChildren().add(coveredBPT);
    	}
    }
    
    private void constructBalconies(Pane root) {
    	HBox kingBalcony = createSingleBalcony(model.getKingBalcony(), 0.105 * root.getWidth(), 0.058 * root.getHeight());
    	AnchorPane.setLeftAnchor(kingBalcony, 0.630 * root.getWidth());
    	AnchorPane.setTopAnchor(kingBalcony, 0.721 * root.getHeight());
    	root.getChildren().add(kingBalcony);
    	int i = 0;
    	for(RegionDTO r : model.getRegions()) {
    		HBox balcony = createSingleBalcony(r.getBalcony(), 0.105 * root.getWidth(), 0.058 * root.getHeight());
    		AnchorPane.setLeftAnchor(balcony, balconiesOrigins.get(i).getX() * root.getWidth());
    		AnchorPane.setTopAnchor(balcony, balconiesOrigins.get(i).getY() * root.getHeight());
    		root.getChildren().add(balcony);
    		i++;
    	}
    }

    private HBox createSingleBalcony(BalconyDTO balcony, double width, double height) {
    	HBox balconyBox = new HBox();
    	balconyBox.setPrefSize(width, height);
		balconyBox.setMaxSize(width, height);
    	for(CouncillorDTO c : balcony.getCouncillors()) {
    		Pane councillor = new Pane();
    		councillor.setPrefSize(width/4, height);
    		String resource = null;
    		switch (c.getColor().getColor()) {
    		case "white": resource = "White_Councillor.png";
    			break;
    		case "violet": resource = "Violet_Councillor.png";
    			break;
    		case "blue": resource = "Blue_Councillor.png";
    			break;
    		case "orange": resource = "Orange_Councillor.png";
    			break;
    		case "black": resource = "Black_Councillor.png";
    			break;
    		case "pink": resource = "Pink_Councillor.png";
    			break;
    		default:
    			break;
    		}
    		councillor.setStyle("-fx-background-image: url(" + getClass().getResource("/img/councillors/" + resource) + ");" +
    				"-fx-background-position: center;" +
    				"-fx-background-size: 100% 100%;");
    		balconyBox.getChildren().add(councillor);
    	}
    	
    	return balconyBox;
    }
    
    private void moveKing(KingDTO king) {
    	for(Map.Entry<CityDTO, CityPane> c : citiesPanes.entrySet()){
    		if(c.getKey().getName().equals(king.getCurrentCity()))
    			c.getValue().getKing().setVisible(true);
    		else c.getValue().getKing().setVisible(false);
    	}
    	king.getCurrentCity();
    }
    
    private void constructActionsPane(Pane root) {
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

    private void constructStatePane(Pane root) {
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
        pane.add(createPlayerPane(scrollPane, model.getLocalPlayer()), 1, 1);
        pane.add(new Label("Other Players: "), 0, 2);

        scrollPane.setContent(pane);
        root.getChildren().add(showHidePane);
        root.getChildren().add(scrollPane);
    }

    private Pane createPlayerPane(Node root, PlayerDTO player) {
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

    private void constructChatPane(Pane root) {
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
}
