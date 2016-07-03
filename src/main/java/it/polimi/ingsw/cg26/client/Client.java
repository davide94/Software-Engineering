package it.polimi.ingsw.cg26.client;

import it.polimi.ingsw.cg26.client.controller.Controller;
import it.polimi.ingsw.cg26.client.model.Model;
import it.polimi.ingsw.cg26.client.ui.*;
import it.polimi.ingsw.cg26.client.ui.actions.*;
import it.polimi.ingsw.cg26.client.view.OutView;
import it.polimi.ingsw.cg26.client.view.rmi.ClientRMIInView;
import it.polimi.ingsw.cg26.client.view.rmi.ClientRMIOutView;
import it.polimi.ingsw.cg26.client.view.socket.ClientSocketInView;
import it.polimi.ingsw.cg26.client.view.socket.ClientSocketOutView;
import it.polimi.ingsw.cg26.common.commands.*;
import it.polimi.ingsw.cg26.common.dto.BusinessPermissionTileDTO;
import it.polimi.ingsw.cg26.common.dto.CityDTO;
import it.polimi.ingsw.cg26.common.dto.RegionDTO;
import it.polimi.ingsw.cg26.common.rmi.ServerRMIViewInterface;
import it.polimi.ingsw.cg26.common.rmi.ServerRMIWelcomeViewInterface;
import it.polimi.ingsw.cg26.common.update.Update;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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

    private static final double RATIO = 2.0 / 1.7;

    private static final String DEFAULT_IP = "localhost";

    private static final int DEFAULT_SOCKET_PORT = 29999;

    private static final int DEFAULT_RMI_PORT = 52365;

    private static final String INTERFACE_NAME = "WELCOME_VIEW";

    private Socket socket;

    private OutView outView;
    
    private MusicPlayer musicPlayer;

    private ExecutorService executor;

    private Model model;

    private Controller controller;

    private Collection<Observer> observers;

    private Pane root;

    private boolean refresh;
    
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
        this.socket = new Socket(ip, port);
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
                "-fx-background-size: 100% 100%");

        primaryStage.setOnCloseRequest(e -> {
            if (socket != null)
                try {
                    socket.close();
                } catch (IOException e1) {
                    log.error("An error occurred closing Socket.", e1);
                }
            Platform.exit();
            System.exit(0);
        });

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

        MapPane map = new MapPane(maxWidth, maxHeight, model);
        root.getChildren().add(map);
        observers.add(map);

        musicPlayer = new MusicPlayer();

        buildVictoryTrack();
        buildBPT();
        buildBalconies();
        buildNobilityTrack();
        buildCoinsTrack();
        buildTiles();
        buildMusicPane();

        Collection<Node> panes = new LinkedList<>();
        panes.add(buildActionsPane());
        panes.add(buildChatPane());
        panes.add(buildMarket());
        panes.add(buildStatePane());

        root.getChildren().addAll(panes);

        primaryStage.setTitle("Council of Four");
        primaryStage.setScene(scene);
        primaryStage.show();

        final Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, event -> Client.this.refreshScene()),
                new KeyFrame(Duration.millis(500))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
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
     * Builds and displays the tiles
     */
    private void buildTiles() {
    	AllTilesPane pane = new AllTilesPane(model, root);
    	root.getChildren().add(pane);
    	observers.add(pane);
    }
    
    
    /**
     * Build and displays the market
     */
    private Node buildMarket() {
    	ScrollPane market = new ScrollPane();
    	MarketPane m = new MarketPane(0.91 * root.getWidth(), 0.68 * root.getHeight(), model, outView);
    	AnchorPane.setBottomAnchor(market, 50.0);
        AnchorPane.setLeftAnchor(market, 50.0);
        market.setVisible(false);

        Pane showHidePane = new Pane();
        AnchorPane.setBottomAnchor(showHidePane, 0.23 * root.getHeight());
        AnchorPane.setLeftAnchor(showHidePane, 0.04 * root.getWidth());
        showHidePane.setPrefSize(50.0, 50.0);
        showHidePane.setStyle("-fx-background-image: url(" + getClass().getResource("/img/marketIcon.png") + ");" +
                "-fx-background-position: center;" +
                "-fx-background-size: 100% 100%;");
        showHidePane.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> market.setVisible(true));
        showHidePane.setEffect(new DropShadow(20.0, Color.rgb(0, 0, 0, 0.95)));
        
        market.setContent(m);
        market.addEventHandler(MouseEvent.MOUSE_EXITED, e -> market.setVisible(false));
        market.setStyle("-fx-background-color: transparent");
        root.getChildren().add(showHidePane);
        observers.add(m);

        return market;
    }

    /**
     * Builds and displays the panel where the user can perform actions
     */
    private Node buildActionsPane() {
        ActionsPane pane = new ActionsPane(model);
        AnchorPane.setTopAnchor(pane, 0.05 * root.getHeight());
        AnchorPane.setRightAnchor(pane, 0.05 * root.getWidth());

        Pane showHidePane = new Pane();
        AnchorPane.setTopAnchor(showHidePane, 0.05 * root.getHeight());
        AnchorPane.setRightAnchor(showHidePane, 0.05 * root.getWidth());
        showHidePane.setEffect(new DropShadow(20.0, Color.rgb(0, 0, 0, 0.95)));
        showHidePane.setPrefSize(75.0, 75.0);
        showHidePane.setStyle("-fx-background-image: url(" + getClass().getResource("/img/action.png") + ");-fx-background-position: center;-fx-background-size: 100%;");
        showHidePane.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            if (model.getState().isYourTurn())
                pane.enable();
            else
                pane.disable();
            pane.setVisible(true);
        });

        pane.getAcquire().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> acquire());
        pane.getBuildKing().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> buildKing());
        pane.getElectAsMainAction().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> electAsMainAction());
        pane.getBuild().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> build());
        pane.getEngageAssistant().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> engageAssistant());
        pane.getChangeBPT().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> changeBPT());
        pane.getElectAsQuickAction().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> electAsQuickAction());
        pane.getAdditionalMainAction().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> additionalMainAction());
        pane.getFoldQuickAction().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> foldQuickAction());
        root.getChildren().add(showHidePane);
        observers.add(pane);

        return pane;
    }
    
    /**
     * Builds and displays the panel where the user can play and stop the music
     */
    private void buildMusicPane() {
    	Button playPauseButton = new Button();
        playPauseButton.setPrefSize(40.0, 40.0);
        playPauseButton.setEffect(new DropShadow(20.0, Color.rgb(0, 0, 0, 0.95)));
        AnchorPane.setBottomAnchor(playPauseButton, 0.15 * root.getWidth());
        AnchorPane.setRightAnchor(playPauseButton, 0.2 * root.getHeight());
        root.getChildren().add(playPauseButton);
        playPauseButton.setStyle("-fx-background-image: url(" + getClass().getResource("/img/musicButtons/volume.png") + ");" +
                "-fx-background-position: center;-fx-background-size: contain;-fx-background-color: transparent;");
        playPauseButton.setOnMouseClicked(e -> {
            if (musicPlayer.toggle())
                playPauseButton.setStyle("-fx-background-image: url(" + getClass().getResource("/img/musicButtons/volume.png") + ");" +
                        "-fx-background-position: center;-fx-background-size: contain;-fx-background-color: transparent;");
            else
                playPauseButton.setStyle("-fx-background-image: url(" + getClass().getResource("/img/musicButtons/mute.png") + ");" +
                        "-fx-background-position: center;-fx-background-size: contain;-fx-background-color: transparent;");
        });

    	/*MusicPane pane = new MusicPane();
        
        DropShadow shadow = new DropShadow();
        shadow.setOffsetY(3.0f);
        shadow.setRadius(50.0);
        shadow.setColor(Color.WHITE);

        Pane showHidePane = new Pane();
        AnchorPane.setTopAnchor(showHidePane, 50.0);
        AnchorPane.setLeftAnchor(showHidePane, 250.0);
        showHidePane.setPrefSize(30.0, 30.0);
        showHidePane.setStyle("-fx-background-image: url(" + getClass().getResource("/img/user.png") + ");-fx-background-position: center;-fx-background-size: 100%;");
        showHidePane.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> pane.setVisible(true));


        pane.getPlay().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> this.musicPlayer.play());
        pane.getStop().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> this.musicPlayer.stop());

        root.getChildren().add(showHidePane);
        root.getChildren().add(pane);*/
    }

    /**
     * Builds and displays the panel where the user can see the player's state
     */
    private Node buildStatePane() {
        PlayersPane statePane = new PlayersPane(400.0, root.getHeight() - 100.0, model);
        observers.add(statePane);

        Pane showHidePane = new Pane();
        AnchorPane.setTopAnchor(showHidePane, 0.525 * root.getHeight());
        AnchorPane.setLeftAnchor(showHidePane, 0.05 * root.getWidth());
        showHidePane.setEffect(new DropShadow(20.0, Color.rgb(0, 0, 0, 0.95)));
        showHidePane.setPrefSize(30.0, 30.0);
        showHidePane.setStyle("-fx-background-image: url(" + getClass().getResource("/img/user.png") + ");-fx-background-position: center;-fx-background-size: 100%;");
        showHidePane.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> statePane.setVisible(true));
        root.getChildren().add(showHidePane);

        return statePane;
    }

    /**
     * Builds and displays the panel where the user chat
     */
    private Node buildChatPane() {
        ChatPane chatPane = new ChatPane(400.0, root.getHeight() / 2.0, model, outView, root);
        observers.add(chatPane);

        Pane showHidePane = new Pane();
        AnchorPane.setBottomAnchor(showHidePane, 35.0);
        AnchorPane.setRightAnchor(showHidePane, 50.0);
        showHidePane.setEffect(new DropShadow(20.0, Color.rgb(0, 0, 0, 0.95)));
        showHidePane.setPrefSize(50.0, 50.0);
        showHidePane.setStyle("-fx-background-image: url(" + getClass().getResource("/img/chat.png") + ");-fx-background-position: center;-fx-background-size: 100%;");
        showHidePane.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            chatPane.update(null, null);
            chatPane.setVisible(true);
            chatPane.update(null, null);
        });
        root.getChildren().add(showHidePane);

        return chatPane;
    }

    /**
     * Show the result of the elect as main action
     */
    private void electAsMainAction() {
        Dialog<Command> d = new ElectDialog(true, model);
        Optional<Command> result = d.showAndWait();
        if (result.isPresent())
            outView.writeObject(result.get());
        System.out.println("electAsMainAction");
    }

    
    /**
     * Show the result of the acquire action
     */
    private void acquire() {
        Dialog<AcquireCommand> d = new AcquireDialog(root, model);
        Optional<AcquireCommand> result = d.showAndWait();
        if (result.isPresent())
            outView.writeObject(result.get());
        System.out.println("acquire");
    }

    /**
     * Show the result of the build action
     */
    private void build() {
    	Dialog<BuildCommand> d = new BuildDialog(model);
    	Optional<BuildCommand> result = d.showAndWait();
    	if(result.isPresent())
    		outView.writeObject(result.get());
        System.out.println("build");
    }

    /**
     * Show the result of the build king action
     */
    private void buildKing() {
    	Dialog<BuildKingCommand> d = new BuildKingDialog(model);
    	Optional<BuildKingCommand> result = d.showAndWait();
    	if(result.isPresent())
    		outView.writeObject(result.get());
        System.out.println("buildKing");
    }

    
    /**
     * Show the result of the engage assistant action
     */
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

    
    /**
     * Show the result of the changeBPT action
     */
    private void changeBPT() {
    	Dialog<ChangeBPTCommand> d = new ChangeBPTDialog(model);
    	Optional<ChangeBPTCommand> result = d.showAndWait();
    	if (result.isPresent())
            outView.writeObject(result.get());
        System.out.println("changeBPT");
    }

    
    /**
     * Show the result of the elect as quick action
     */
    private void electAsQuickAction() {
        Dialog<Command> d = new ElectDialog(false, model);
        Optional<Command> result = d.showAndWait();
        if (result.isPresent())
            outView.writeObject(result.get());
        System.out.println("electAsQuickAction");
    }

    
    /**
     * Show the result of the additional main action
     */
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

    
    /**
     * Show the result of the fold quick action
     */
    private void foldQuickAction() {
    	Dialog<FoldQuickActionCommand> d = new Dialog<>();
    	d.setContentText("Are you sure you don't want to use your quick action?");
    	ButtonType buttonTypeOk = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        d.getDialogPane().getButtonTypes().add(buttonTypeOk);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        d.getDialogPane().getButtonTypes().add(buttonTypeCancel);
        d.setResultConverter(b -> {
        	if (b == buttonTypeOk)
                return new FoldQuickActionCommand();
            return null;
        });
        Optional<FoldQuickActionCommand> result = d.showAndWait();
        if (result.isPresent())
            outView.writeObject(result.get());
        System.out.println("foldQuickAction");
    }

    
    /**
     * Build and Displays Dialogs for pending requests
     */
    private void buildDialogs() {
        Optional<List<BusinessPermissionTileDTO>> pendingTiles = model.getState().getPendingBPTBonusRequest();
        if (pendingTiles.isPresent()) {
            if (pendingTiles.get().isEmpty()) {
                model.getState().pendingRequestPerformed();
                outView.writeObject(new ChooseBPTCommand(null, 0));
            } else {
                buildBPTBonusRequestDialog(pendingTiles.get());
            }
        }
        Optional<List<CityDTO>> pendingCities = model.getState().getPendingCityBonusRequest();
        if (pendingCities.isPresent()) {
            if (pendingCities.get().isEmpty()) {
                model.getState().pendingRequestPerformed();
                outView.writeObject(new ChooseCityCommand(new LinkedList<>()));
            } else {
                buildCityBonusRequestDialog(pendingCities.get());
            }
        }
        Optional<List<BusinessPermissionTileDTO>> pendingPlayer = model.getState().getPendingPlayerBonusRequest();
        if (pendingPlayer.isPresent()) {
            if (pendingPlayer.get().isEmpty()) {
                model.getState().pendingRequestPerformed();
                outView.writeObject(new ChoosePlayerBPTCommand(new LinkedList<>()));
            } else {
                buildPlayerRequestDialog(pendingPlayer.get());
            }
        }
        if (model.getState().isMatchEnded())
            new FinalDialog(model).show();
    }

    
    /**
     * Build and displays the dialog for the BPT to choose
     * @param pendingTiles is the list of BPT that the players can choose
     */
    private void buildBPTBonusRequestDialog(List<BusinessPermissionTileDTO> pendingTiles) {
        Dialog<ChooseBPTCommand> d = new ChooseBPTDialog(pendingTiles);
        d.setOnHidden(e -> {
            model.getState().pendingRequestPerformed();
            if (d.getResult() != null)
                outView.writeObject(d.getResult());
            else
                outView.writeObject(new ChooseBPTCommand(null, 0));
        });
        d.show();
    }

    
    /**
     * Build and displays the dialog for the bonus of a city to choose
     * @param pendingCities is the list of cities in which a player has an emporium
     */
    private void buildCityBonusRequestDialog(List<CityDTO> pendingCities) {
        Dialog<ChooseCityCommand> d = new ChooseCityBonusDialog(pendingCities);
        d.setOnHidden(e -> {
            model.getState().pendingRequestPerformed();
            if (d.getResult() != null)
                outView.writeObject(d.getResult());
            else
                outView.writeObject(new ChooseCityCommand(new LinkedList<>()));
        });
        d.show();
    }

    
    /**
     * Build and displays the dialog for the bonus of one player's BPT to choose
     * @param pendingPlayer is the list of BPT owned by a player
     */
    private void buildPlayerRequestDialog(List<BusinessPermissionTileDTO> pendingPlayer) {
        Dialog<ChoosePlayerBPTCommand> d = new ChoosePlayerBonusDialog(pendingPlayer);
        d.setOnHidden(e -> {
            model.getState().pendingRequestPerformed();
            if (d.getResult() != null)
                outView.writeObject(d.getResult());
            else
                outView.writeObject(new ChoosePlayerBPTCommand(new LinkedList<>()));
        });
        d.show();
    }

    /**
     * Refresh the scene
     */
    private synchronized void refreshScene() {
        if (refresh) {
            buildDialogs();
            observers.forEach(o -> o.update(null, null));
        }
        refresh = false;
    }

    /**
     * When a new update arrives, in this way the scene can be refreshed
     * @param o is the update
     */
    @Override
    public void update(Update o) {
        refresh = true;
    }

    
    public static void main(String[] args) {
        launch(args);
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
