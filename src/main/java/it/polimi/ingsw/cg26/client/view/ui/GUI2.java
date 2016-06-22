package it.polimi.ingsw.cg26.client.view.ui;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 */
public class GUI2 extends Application {

    private final static double RATIO = 7.0d / 6.0d;

    @Override
    public void start(Stage primaryStage) throws Exception {
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

        primaryStage.setTitle("Council of Four");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.show();
    }

    private void constructCities(Pane root) {

        double w = root.getWidth();
        double h = root.getHeight();

        Pane c1 = createCity(0.05 * w, 0.06 * h);
        Pane c2 = createCity(0.035 * w, 0.24 * h);
        Pane c3 = createCity(0.21 * w, 0.11 * h);
        Pane c4 = createCity(0.2 * w, 0.27 * h);
        Pane c5 = createCity(0.1 * w, 0.38 * h);

        Pane c6 = createCity(0.35 * w, 0.06 * h);
        Pane c7 = createCity(0.335 * w, 0.24 * h);
        Pane c8 = createCity(0.4 * w, 0.38 * h);
        Pane c9 = createCity(0.51 * w, 0.11 * h);
        Pane c10 = createCity(0.5 * w, 0.27 * h);

        Pane c11 = createCity(0.7 * w, 0.06 * h);
        Pane c12 = createCity(0.68 * w, 0.24 * h);
        Pane c13 = createCity(0.68 * w, 0.4 * h);
        Pane c14 = createCity(0.81 * w, 0.15 * h);
        Pane c15 = createCity(0.8 * w, 0.35 * h);

        root.getChildren().addAll(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15);
    }

    private Pane createCity(double x, double y) {
        Pane city = new Pane();
        AnchorPane.setTopAnchor(city, y);
        AnchorPane.setLeftAnchor(city, x);
        city.setPrefSize(150.0, 150.0);
        city.setStyle("-fx-background-image: url(" + getClass().getResource("/img/cities/gold.png") + ");" +
                "-fx-background-position: center;" +
                "-fx-background-size: 100% 100%;");

        return city;
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
        actionsPane.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        actionsPane.setVisible(false);
                    }
                });

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
