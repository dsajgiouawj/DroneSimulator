package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.simulator;

import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.DoubleTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.FormattedTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.IntTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.importPlugin.ImportPlugins;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.ITacticsViewerFrontend;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.TacticsUIFrontend;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placer.VictimsPlacerUIFrontend;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placer.VictimsPlacerViewerFrontend;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Simulatorをviewerから使うためのクラス。
 * Created by 遠藤拓斗 on 2017/05/12.
 */
public class SimulatorViewerFrontend {
    private static FormattedTextFieldWithLabel<Integer> numDroneField = new IntTextFieldWithLabel("ドローンの数", 50);
    private static FormattedTextFieldWithLabel<Integer> populationField = new IntTextFieldWithLabel("人口", 1000);
    private static FormattedTextFieldWithLabel<Integer> limitTimeField = new IntTextFieldWithLabel("ドローンの稼働時間[s]", 1500);
    private static FormattedTextFieldWithLabel<Double> viewRangeRadiusField = new DoubleTextFieldWithLabel("カメラの視野の半径[m]", 30);

    private static ChoiceBox tacticsChoicer = new ChoiceBox();
    private static ChoiceBox victimsPlacerChoicer = new ChoiceBox();

    private static List<ITacticsViewerFrontend> tacticsPlugins = ImportPlugins.getTacticsPlugins();
    private static List<VictimsPlacerViewerFrontend> victimsPlacerPlugins = ImportPlugins.getPlacerPlugins();

    private static VBox vBoxForTactics;
    private static VBox vBoxForVictimsPlacer;

    public static void makeGUI(VBox vBoxForSimulator, VBox vBoxForTacticsPlugin, VBox vBoxForVictimsPlacerPlugin) {
        vBoxForTactics = vBoxForTacticsPlugin;
        vBoxForVictimsPlacer = vBoxForVictimsPlacerPlugin;

        tacticsChoicer.getItems().addAll(tacticsPlugins.stream().map(t -> t.explain()).collect(Collectors.toList()));
        tacticsChoicer.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue instanceof Integer) {
                makeTacticsGUI((int) newValue);
            }
        });
        tacticsChoicer.getSelectionModel().select(0);

        victimsPlacerChoicer.getItems().addAll(victimsPlacerPlugins.stream().map(p -> p.explain()).collect(Collectors.toList()));
        victimsPlacerChoicer.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue instanceof Integer) {
                makePlacerGUI((int) newValue);
            }
        });
        victimsPlacerChoicer.getSelectionModel().select(0);

        vBoxForSimulator.getChildren().addAll(tacticsChoicer, victimsPlacerChoicer, numDroneField, populationField, limitTimeField, viewRangeRadiusField);
    }

    private static void makeTacticsGUI(int index) {
        vBoxForTactics.getChildren().clear();
        tacticsPlugins.get(index).makeGUI(vBoxForTactics);
    }

    private static void makePlacerGUI(int index) {
        vBoxForVictimsPlacer.getChildren().clear();
        victimsPlacerPlugins.get(index).makeGUI(vBoxForVictimsPlacer);
    }

    public static Simulator getSimulator() {
        int numDrone = numDroneField.getValue();
        int population = populationField.getValue();
        int limitTime = limitTimeField.getValue();
        double viewRangeRadius = viewRangeRadiusField.getValue();
        VictimsPlacerUIFrontend victimsPlacerUIFrontend = victimsPlacerPlugins.get(victimsPlacerChoicer.getSelectionModel().getSelectedIndex());
        TacticsUIFrontend tacticsUIFrontend = tacticsPlugins.get(tacticsChoicer.getSelectionModel().getSelectedIndex());
        //getTactics(numDrone, viewRangeRadius, limitTime, drones1);
        return new Simulator(tacticsUIFrontend, limitTime, numDrone, viewRangeRadius, population, victimsPlacerUIFrontend);
    }
}
