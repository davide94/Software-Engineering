package it.polimi.ingsw.cg26.client;

import it.polimi.ingsw.cg26.client.controller.Controller;
import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.client.view.OutView;
import it.polimi.ingsw.cg26.client.view.rmi.ClientRMIInView;
import it.polimi.ingsw.cg26.client.view.rmi.ClientRMIOutView;
import it.polimi.ingsw.cg26.client.view.socket.ClientSocketInView;
import it.polimi.ingsw.cg26.client.view.socket.ClientSocketOutView;
import it.polimi.ingsw.cg26.common.dto.CityColorDTO;
import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.rmi.ServerRMIViewInterface;
import it.polimi.ingsw.cg26.common.rmi.ServerRMIWelcomeViewInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class GUIClient extends Application {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final static double RATIO = 7.0d / 6.0d;

    private final static String DEFAULT_IP = "127.0.0.1";

    private final static int DEFAULT_SOCKET_PORT = 29999;

    private final static int DEFAULT_RMI_PORT = 52365;

    private final static String INTERFACE_NAME = "WELCOME_VIEW";

    private OutView outView;

    private ExecutorService executor;

    private Model model;

    private Controller controller;

    private Map<String, Pane> citiesPanes = new LinkedHashMap<>();

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

        Scene scene = new Scene(root, maxWidth, maxHeight);

        // TODO: maintain fixed ratio on resize
        // TODO: set window min size

        constructCities(root);
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

        createCity(root, 0.050, 0.060, cities.get(0));
        createCity(root, 0.035, 0.240, cities.get(1));
        createCity(root, 0.210, 0.110, cities.get(2));
        createCity(root, 0.200, 0.270, cities.get(3));
        createCity(root, 0.100, 0.380, cities.get(4));
        createCity(root, 0.350, 0.060, cities.get(5));
        createCity(root, 0.335, 0.240, cities.get(6));
        createCity(root, 0.400, 0.380, cities.get(7));
        createCity(root, 0.510, 0.110, cities.get(8));
        createCity(root, 0.500, 0.270, cities.get(9));
        createCity(root, 0.700, 0.060, cities.get(10));
        createCity(root, 0.680, 0.240, cities.get(11));
        createCity(root, 0.680, 0.400, cities.get(12));
        createCity(root, 0.810, 0.150, cities.get(13));
        createCity(root, 0.800, 0.350, cities.get(14));
    }

    private void createCity(Pane root, double x, double y, CityDTO city) {
        AnchorPane pane = new AnchorPane();
        AnchorPane.setLeftAnchor(pane, x * root.getWidth());
        AnchorPane.setTopAnchor(pane, y * root.getHeight());
        pane.setPrefSize(0.15 * root.getHeight(), 0.15 * root.getHeight());

        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.BLACK);

        Font goudyMedieval = Font.loadFont(getClass().getResource("/fonts/goudy_medieval/Goudy_Mediaeval_DemiBold.ttf").toExternalForm(), 0.025 * root.getHeight());
        Label nameLabel = new Label(city.getName().substring(0, 1).toUpperCase() + city.getName().substring(1));
        //nameLabel.setEffect(ds);
        nameLabel.setFont(goudyMedieval);
        nameLabel.setTextFill(Color.rgb(137, 135, 143));
        nameLabel.setRotate(45.0);
        AnchorPane.setRightAnchor(nameLabel, 10.0);
        AnchorPane.setTopAnchor(nameLabel, 10.0);
        pane.getChildren().add(nameLabel);

        String style = "-fx-background-image: url(" + getClass().getResource("/img/cities/violet.png") + ");";
        if (city.getColor().equals(new CityColorDTO("iron"))) {
            style = "-fx-background-image: url(" + getClass().getResource("/img/cities/iron.png") + ");";
            nameLabel.setTextFill(Color.rgb(62, 171, 182));
        }
        if (city.getColor().equals(new CityColorDTO("bronze"))) {
            style = "-fx-background-image: url(" + getClass().getResource("/img/cities/bronze.png") + ");";
            nameLabel.setTextFill(Color.rgb(196, 112, 81));
        }
        if (city.getColor().equals(new CityColorDTO("silver"))) {
            style = "-fx-background-image: url(" + getClass().getResource("/img/cities/silver.png") + ");";
            nameLabel.setTextFill(Color.rgb(150, 155, 159));
        }
        if (city.getColor().equals(new CityColorDTO("gold"))) {
            style = "-fx-background-image: url(" + getClass().getResource("/img/cities/gold.png") + ");";
            nameLabel.setTextFill(Color.rgb(186, 148, 38));
        }

        style += "-fx-background-position: center;-fx-background-size: 100% 100%;";
        pane.setStyle(style);

        root.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {
            AnchorPane.setLeftAnchor(pane, x * newSceneWidth.doubleValue());
            pane.setPrefSize(0.15 * root.getHeight(), 0.15 * root.getHeight());
            nameLabel.setFont(Font.loadFont(getClass().getResource("/fonts/goudy_medieval/Goudy_Mediaeval_DemiBold.ttf").toExternalForm(), 0.025 * root.getHeight()));
        });
        root.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> {
            AnchorPane.setTopAnchor(pane, y * newSceneHeight.doubleValue());
            pane.setPrefSize(0.15 * root.getHeight(), 0.15 * root.getHeight());
            nameLabel.setFont(Font.loadFont(getClass().getResource("/fonts/goudy_medieval/Goudy_Mediaeval_DemiBold.ttf").toExternalForm(), 0.025 * root.getHeight()));
        });

        citiesPanes.put(city.getName(), pane);
        root.getChildren().add(pane);
    }

    private void constructActionsPane(Pane root) {
        AnchorPane actionsPane = new AnchorPane();
        root.getChildren().add(actionsPane);
        AnchorPane.setTopAnchor(actionsPane, 50.0);
        AnchorPane.setRightAnchor(actionsPane, 50.0);
        actionsPane.setPrefWidth(400.0);
        actionsPane.setPrefHeight(310.0);
        actionsPane.setVisible(false);
        actionsPane.setStyle("-fx-background-image: url(" + getClass().getResource("/img/actions.jpg") + ");" +
                "-fx-background-position: center;" +
                "-fx-background-size: 100% 100%;");
        actionsPane.addEventHandler(MouseEvent.MOUSE_EXITED, e -> actionsPane.setVisible(false));

        Pane showHidePane = new Pane();
        root.getChildren().add(showHidePane);
        AnchorPane.setTopAnchor(showHidePane, 5.0);
        AnchorPane.setRightAnchor(showHidePane, 5.0);
        showHidePane.setPrefSize(20.0, 20.0);
        showHidePane.setStyle("-fx-background-color: azure");
        showHidePane.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> actionsPane.setVisible(true));

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

        actionsPane.getChildren().addAll(p1, p2, p3, p4, s1, s2, s3, s4);
    }

    private void constructStatePane(Pane root) {
        // TODO: implement
    }

    private void constructChatPane(Pane root) {
        // TODO: implement
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
}
