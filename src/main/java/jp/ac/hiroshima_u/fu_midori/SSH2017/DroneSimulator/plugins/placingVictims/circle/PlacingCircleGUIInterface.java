package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.placingVictims.circle;

import javafx.geometry.Point2D;
import javafx.scene.layout.VBox;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.DoubleTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placing.PlacingVictims;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placing.PlacingVictimsGUIInterface;

/**
 * placingCircleをGUIから利用するためのインタフェース
 *
 * @author 遠藤拓斗 on 2017/05/17.
 */
public class PlacingCircleGUIInterface implements PlacingVictimsGUIInterface {
    private static DoubleTextFieldWithLabel centerXField = new DoubleTextFieldWithLabel("中心のx座標", 0);
    private static DoubleTextFieldWithLabel centerYField = new DoubleTextFieldWithLabel("中心のy座標", 0);
    private static DoubleTextFieldWithLabel radiusField = new DoubleTextFieldWithLabel("半径[m]", 1000);

    @Override
    public void makeGUI(VBox vBox) {
        vBox.getChildren().addAll(centerXField, centerYField, radiusField);
    }

    @Override
    public PlacingVictims getPlacingVictims() {
        return new PlacingCircle(new Point2D(centerXField.getValue(), centerYField.getValue()), radiusField.getValue());
    }

    @Override
    public String explain() {
        return "円形に配置します";
    }
}
