package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.VieableDrone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.simulator.Simulator;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.simulator.SimulatorViewerFrontend;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.ViewableVictim;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author 遠藤拓斗 on 2017/05/07.
 */
public class Controller implements Initializable {
    /*
    public VBox vBox_parent;
    public HBox hBox_main;
    public Canvas canvas;
    public VBox inputInformationPane;
    public VBox vBoxForMain;
    public VBox vBoxForVictimsPlacer;
    public VBox vBoxForTactics;
    public Button btn_start;
    public Label label_rescuee;
    public Label label_time;
*/
    @FXML
    private Label label_zoom;
    @FXML
    private Label label_speed;
    @FXML
    private HBox hBox_main;
    @FXML
    private Canvas canvas;
    @FXML
    private VBox vBoxForMain;
    @FXML
    private VBox vBoxForTactics;
    @FXML
    private VBox vBoxForVictimsPlacer;
    @FXML
    private Label label_time;
    @FXML
    private Label label_rescuee;

    private Simulator simulator;

    private static final double zoomDefault = 1. / 16;
    private double zoom = zoomDefault;
    private double speed = 1.0;

    /**
     * javafxのinitialize
     *
     * @param location  location
     * @param resources resources
     */
    public void initialize(URL location, ResourceBundle resources) {
        SimulatorViewerFrontend.makeGUI(vBoxForMain, vBoxForTactics, vBoxForVictimsPlacer);
        initCanvas();
    }

    private void initCanvas() {
        canvas.heightProperty().bind(hBox_main.heightProperty());
        canvas.widthProperty().bind(hBox_main.heightProperty());//正方形
        Timeline timeline = new Timeline(new KeyFrame(new Duration(1000 / 60), event -> draw()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private List<VieableDrone> drones;
    private List<ViewableVictim> victims;
    private static final double radiusOfVictim = 2;//被災者を表示するときの半径
    private static final Color colorOfVictim = Color.BLUE;//被災者を表示するときの色

    public void onClick(ActionEvent actionEvent) {
        simulator = SimulatorViewerFrontend.getSimulator();
        drones = simulator.getVieableDrones();
        victims = simulator.getVieableVictims();
    }

    private double display = 0.0;

    private void draw() {
        if (simulator == null) return;
        if (drones == null || victims == null) return;
        clearCanvas();
        display += speed;//表示速度によってnextTurnの回数を決める
        int num = (int) display;
        display -= num;
        for (int i = 0; i < num; i++) {
            simulator.nextTurn();
        }

        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (ViewableVictim v : victims) {
            if (v.isFound()) continue;
            double x = (v.getX() - radiusOfVictim) * zoom + canvas.getWidth() / 2;
            double y = (v.getY() - radiusOfVictim) * zoom + canvas.getHeight() / 2;
            gc.setFill(colorOfVictim);
            gc.fillOval(x, y, radiusOfVictim * 2, radiusOfVictim * 2);
        }
        for (VieableDrone d : drones) {
            double x = (d.getX() - d.getViewRangeRadius()) * zoom + canvas.getWidth() / 2;
            double y = (d.getY() - d.getViewRangeRadius()) * zoom + canvas.getWidth() / 2;
            gc.setFill(d.getColor());
            gc.fillOval(x, y, d.getViewRangeRadius() * 2 * zoom, d.getViewRangeRadius() * 2 * zoom);
        }
        int time = simulator.getTime();
        int hour = time / 3600;
        int minute = time % 3600 / 60;
        int second = time % 60;
        label_time.setText(String.format("%d:%02d:%02d", hour, minute, second));
        label_rescuee.setText(String.format("%d/%d", simulator.getVictims().foundPopulation(), simulator.getVictims().population()));
    }

    private void clearCanvas() {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private int zoomPercentage = 100;

    public void onZoomout(ActionEvent actionEvent) {
        if (zoomPercentage == 1) return;
        if (zoomPercentage <= 10) zoomPercentage--;
        else zoomPercentage -= 10;
        label_zoom.setText(" " + zoomPercentage + "% ");
        zoom = zoomDefault * zoomPercentage / 100;
    }

    public void onZoomin(ActionEvent actionEvent) {
        if (zoomPercentage < 10) zoomPercentage++;
        else zoomPercentage += 10;
        label_zoom.setText(" " + zoomPercentage + "% ");
        zoom = zoomDefault * zoomPercentage / 100;
    }

    private int speedPercentage = 100;

    public void onSpeeddown(ActionEvent actionEvent) {
        if (speedPercentage == 1) return;
        if (speedPercentage <= 10) speedPercentage--;
        else speedPercentage -= 10;
        label_speed.setText(" " + speedPercentage + "% ");
        speed = speedPercentage / 100.0;
    }

    public void onSpeedup(ActionEvent actionEvent) {
        if (speedPercentage >= 1000) return;
        if (speedPercentage < 10) speedPercentage++;
        else speedPercentage += 10;
        label_speed.setText(" " + speedPercentage + "% ");
        speed = speedPercentage / 100.0;
    }
}
