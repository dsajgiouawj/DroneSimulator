package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.spiralTactics;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.DoubleTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.FormattedTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.ITactics;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.ITacticsViewerFrontend;

import java.util.List;

/**
 * spiralTacticsをviewerから利用するためのインタフェース
 *
 * @author 遠藤拓斗 on 2017/05/15.
 */
public class ViewerFrontend implements ITacticsViewerFrontend {
    private FormattedTextFieldWithLabel<Double> searchRatioField = new DoubleTextFieldWithLabel("探索割合", 1);
    private Label warningLabel = new Label("探索割合の値は0より大きく1以下である必要があります\n不正な値を入力した場合は1とみなされます");

    @Override
    public void makeGUI(VBox vBox) {
        vBox.getChildren().addAll(searchRatioField, warningLabel);
    }

    @Override
    public ITactics getTactics(int numDrone, double viewRangeRadius, int limitTime, List<Drone> drones) {
        double searchRatio = searchRatioField.getValue();
        if (!(0 < searchRatio && searchRatio <= 1)) {
            searchRatio = 1;
        }
        return new Tactics(searchRatio, numDrone, viewRangeRadius, drones);
    }

    @Override
    public String explain() {
        return "螺旋探索";
    }
}
