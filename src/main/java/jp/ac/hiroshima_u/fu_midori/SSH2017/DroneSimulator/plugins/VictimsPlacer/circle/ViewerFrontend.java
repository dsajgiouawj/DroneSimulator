package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.VictimsPlacer.circle;

import javafx.geometry.Point2D;
import javafx.scene.layout.VBox;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.DoubleTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.FormattedTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placer.VictimsPlacer;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placer.VictimsPlacerViewerFrontend;

/**
 * CirclePlacerをviewerから利用
 *
 * @author 遠藤拓斗 on 2017/05/17.
 */
public class ViewerFrontend implements VictimsPlacerViewerFrontend {
    private static FormattedTextFieldWithLabel<Double> centerXField = new DoubleTextFieldWithLabel("中心のx座標", 0);
    private static FormattedTextFieldWithLabel<Double> centerYField = new DoubleTextFieldWithLabel("中心のy座標", 0);
    private static FormattedTextFieldWithLabel<Double> radiusField = new DoubleTextFieldWithLabel("半径[m]", 1000);

    @Override
    public void makeGUI(VBox vBox) {
        vBox.getChildren().addAll(centerXField, centerYField, radiusField);
    }

    @Override
    public VictimsPlacer getVictimsPlacer() {
        return new Placer(new Point2D(centerXField.getValue(), centerYField.getValue()), radiusField.getValue());
    }

    @Override
    public String explain() {
        return "円形に配置します";
    }
}
