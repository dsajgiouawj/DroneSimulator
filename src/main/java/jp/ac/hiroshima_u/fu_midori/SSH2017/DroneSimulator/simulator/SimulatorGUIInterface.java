package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.simulator;

import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.DoubleTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.FormattedTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.IntTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.importPlugin.ImportGUIPlugins;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.TacticsGUIInterface;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.TacticsUI;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placing.PlacingVictimsGUIInterface;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placing.PlacingVictimsUI;

import java.util.List;
import java.util.stream.Collectors;

/**
 * SimulatorをGUIから使うためのクラス。
 * Created by 遠藤拓斗 on 2017/05/12.
 */
public class SimulatorGUIInterface {
    private static FormattedTextFieldWithLabel<Integer> numDroneField = new IntTextFieldWithLabel("ドローンの数", 50);
    private static FormattedTextFieldWithLabel<Integer> populationField = new IntTextFieldWithLabel("人口", 1000);
    private static FormattedTextFieldWithLabel<Integer> limitTimeField = new IntTextFieldWithLabel("ドローンの稼働時間[s]", 1500);
    private static FormattedTextFieldWithLabel<Double> viewRangeRadiusField = new DoubleTextFieldWithLabel("カメラの視野の半径[m]", 30);

    private static ChoiceBox tacticsChoicer = new ChoiceBox();
    private static ChoiceBox placingVictimsChoicer = new ChoiceBox();

    private static List<TacticsGUIInterface> tacticsPlugins = ImportGUIPlugins.getTacticsPlugins();
    private static List<PlacingVictimsGUIInterface> placingVictimsPlugins = ImportGUIPlugins.getPlacingPlugins();

    private static VBox vBoxForTactics;
    private static VBox vBoxForPlacingVictims;

    public static void makeGUI(VBox vBoxForSimulator, VBox vBoxForTacticsPlugin, VBox vBoxForPlacingVictimsPlugin) {
        vBoxForTactics = vBoxForTacticsPlugin;
        vBoxForPlacingVictims = vBoxForPlacingVictimsPlugin;

        tacticsChoicer.getItems().addAll(tacticsPlugins.stream().map(t -> t.explain()).collect(Collectors.toList()));
        tacticsChoicer.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue instanceof Integer) {
                makeTacticsGUI((int) newValue);
            }
        });
        tacticsChoicer.getSelectionModel().select(0);

        placingVictimsChoicer.getItems().addAll(placingVictimsPlugins.stream().map(p -> p.explain()).collect(Collectors.toList()));
        placingVictimsChoicer.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue instanceof Integer) {
                makePlacingGUI((int) newValue);
            }
        });
        placingVictimsChoicer.getSelectionModel().select(0);

        vBoxForSimulator.getChildren().addAll(tacticsChoicer, placingVictimsChoicer, numDroneField, populationField, limitTimeField, viewRangeRadiusField);
    }

    private static void makeTacticsGUI(int index) {
        vBoxForTactics.getChildren().clear();
        tacticsPlugins.get(index).makeGUI(vBoxForTactics);
    }

    private static void makePlacingGUI(int index) {
        vBoxForPlacingVictims.getChildren().clear();
        placingVictimsPlugins.get(index).makeGUI(vBoxForPlacingVictims);
    }

    public static Simulator getSimulator() {
        int numDrone = numDroneField.getValue();
        int population = populationField.getValue();
        int limitTime = limitTimeField.getValue();
        double viewRangeRadius = viewRangeRadiusField.getValue();
        PlacingVictimsUI placingVictimsUI = placingVictimsPlugins.get(placingVictimsChoicer.getSelectionModel().getSelectedIndex());
        TacticsUI tacticsUI = tacticsPlugins.get(tacticsChoicer.getSelectionModel().getSelectedIndex());
        //getTactics(numDrone, viewRangeRadius, limitTime, drones1);
        return new Simulator(tacticsUI, limitTime, numDrone, viewRangeRadius, population, placingVictimsUI);
    }
}
