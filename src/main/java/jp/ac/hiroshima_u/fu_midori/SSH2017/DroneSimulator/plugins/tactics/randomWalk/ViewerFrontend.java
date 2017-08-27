package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomWalk;

import javafx.scene.layout.VBox;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.DoubleTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.FormattedTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.ITactics;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.ITacticsViewerFrontend;

import java.util.List;

/**
 * @author 遠藤拓斗 on 2017/8/27
 */
public class ViewerFrontend implements ITacticsViewerFrontend {
    FormattedTextFieldWithLabel<Double> probabilityField = new DoubleTextFieldWithLabel("呼び出される確率(0以上1以下)", 0.05);

    @Override
    public void makeGUI(VBox vBox) {
        vBox.getChildren().add(probabilityField);
    }

    @Override
    public String explain() {
        return "ランダムウォーク";
    }

    @Override
    public ITactics getTactics(int numDrone, double viewRangeRadius, int limitTime, List<Drone> drones) {
        return new Tactics(numDrone, viewRangeRadius, limitTime, drones, 0.5, 10, 60, probabilityField.getValue());
    }
}
