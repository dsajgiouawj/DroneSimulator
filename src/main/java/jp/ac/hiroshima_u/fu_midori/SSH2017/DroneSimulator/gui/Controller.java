package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.DroneGUIInterface;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.simulator.Simulator;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.simulator.SimulatorGUIInterface;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placing.VictimGUIInterface;

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
    public VBox vBoxForPlacingVictims;
    public VBox vBoxForTactics;
    public Button btn_start;
    public Label label_rescuee;
    public Label label_time;
*/
    @FXML
    private HBox hBox_main;
    @FXML
    private Canvas canvas;
    @FXML
    private VBox vBoxForMain;
    @FXML
    private VBox vBoxForTactics;
    @FXML
    private VBox vBoxForPlacingVictims;

    private Simulator simulator;

    private double zoom = 1. / 16;

    /**
     * javafxのinitialize
     *
     * @param location  location
     * @param resources resources
     */
    public void initialize(URL location, ResourceBundle resources) {
        SimulatorGUIInterface.makeGUI(vBoxForMain, vBoxForTactics, vBoxForPlacingVictims);
        initCanvas();
    }

    private void initCanvas() {
        canvas.heightProperty().bind(hBox_main.heightProperty());
        canvas.widthProperty().bind(hBox_main.heightProperty());//正方形
        Timeline timeline = new Timeline(new KeyFrame(new Duration(1000 / 60), event -> draw()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private List<DroneGUIInterface> drones;
    private List<VictimGUIInterface> victims;
    private static final double radiusOfVictim = 2;//被災者を表示するときの半径
    private static final Color colorOfVictim = Color.BLUE;//被災者を表示するときの色

    public void onClick(ActionEvent actionEvent) {
        simulator = SimulatorGUIInterface.getSimulator();
        drones = simulator.getDrones();
        victims = simulator.getVictims();
    }

    private void draw() {
        if (simulator == null) return;
        if (drones == null || victims == null) return;
        clearCanvas();
        simulator.nextTurn();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (VictimGUIInterface v : victims) {
            if (v.isFound()) continue;
            double x = (v.getX() - radiusOfVictim) * zoom + canvas.getWidth() / 2;
            double y = (v.getY() - radiusOfVictim) * zoom + canvas.getHeight() / 2;
            gc.setFill(colorOfVictim);
            gc.fillOval(x, y, radiusOfVictim * 2, radiusOfVictim * 2);
        }
        for (DroneGUIInterface d : drones) {
            double x = (d.getX() - d.getViewRangeRadius()) * zoom + canvas.getWidth() / 2;
            double y = (d.getY() - d.getViewRangeRadius()) * zoom + canvas.getWidth() / 2;
            gc.setFill(d.getColor());
            gc.fillOval(x, y, d.getViewRangeRadius() * 2 * zoom, d.getViewRangeRadius() * 2 * zoom);
        }
    }

    private void clearCanvas() {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}
