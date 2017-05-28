package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.spiralTactics;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.DoubleTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.FormattedTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.Tactics;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.TacticsGUIInterface;

/**
 * spiralTacticsをGUIから利用するためのインタフェース
 *
 * @author 遠藤拓斗 on 2017/05/15.
 */
public class SpiralTacticsGUIInterface implements TacticsGUIInterface {
    private static FormattedTextFieldWithLabel<Double> searchRatioField = new DoubleTextFieldWithLabel("探索割合", 1);
    private static Label warningLabel = new Label("探索割合の値は0より大きく1以下である必要があります\n不正な値を入力した場合は1とみなされます");

    @Override
    public void makeGUI(VBox vBox) {
        vBox.getChildren().addAll(searchRatioField, warningLabel);
    }

    @Override
    public Tactics getTactics(int numDrone, double viewRangeRadius, int limitTime) {
        double searchRatio = searchRatioField.getValue();
        if (!(0 < searchRatio && searchRatio <= 1)) {
            searchRatio = 1;
        }
        return new SpiralTactics(searchRatio, numDrone, viewRangeRadius);
    }

    @Override
    public String explain() {
        return "螺旋探索(隙間なし)";
    }
}
