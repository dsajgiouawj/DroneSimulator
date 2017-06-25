package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.VictimsPlacer.circles;

import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.DoubleTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.FormattedTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.IntTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placer.VictimsPlacer;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placer.VictimsPlacerViewerFrontend;

/**
 * CirclesPlacerをviewerから利用
 *
 * @author 遠藤拓斗 on 2017/05/17.
 */
public class ViewerFrontend implements VictimsPlacerViewerFrontend {
    private static Label rangeOfCirclesLabel = new Label("円の範囲");
    private static FormattedTextFieldWithLabel<Double> centerXOfRangeField = new DoubleTextFieldWithLabel("中心のx座標", 0);
    private static FormattedTextFieldWithLabel<Double> centerYOfRangeField = new DoubleTextFieldWithLabel("中心のy座標", 0);
    private static FormattedTextFieldWithLabel<Double> radiusOfRangeField = new DoubleTextFieldWithLabel("半径[m]", 2000);
    private static Label circlesLabel = new Label("円");
    private static FormattedTextFieldWithLabel<Double> radiusOfCirclesField = new DoubleTextFieldWithLabel("半径[m]", 200);
    private static FormattedTextFieldWithLabel<Integer> numbOfCirclesField = new IntTextFieldWithLabel("個数", 20);

    @Override
    public void makeGUI(VBox vBox) {
        vBox.getChildren().addAll(rangeOfCirclesLabel, centerXOfRangeField, centerYOfRangeField, radiusOfRangeField, circlesLabel, radiusOfCirclesField, numbOfCirclesField);
    }

    @Override
    public VictimsPlacer getVictimsPlacer() {
        return new Placer(new Point2D(centerXOfRangeField.getValue(), centerYOfRangeField.getValue()), radiusOfRangeField.getValue(), radiusOfCirclesField.getValue(), numbOfCirclesField.getValue());
    }

    @Override
    public String explain() {
        return "複数の円上に配置します";
    }
}
