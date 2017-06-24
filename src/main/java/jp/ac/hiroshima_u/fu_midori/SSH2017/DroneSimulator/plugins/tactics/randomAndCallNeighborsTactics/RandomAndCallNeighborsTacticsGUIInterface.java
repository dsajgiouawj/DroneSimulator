package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.DoubleTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.FormattedTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.IntTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.FiltersManagement;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.FiltersManagementGUIInterface;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.Tactics;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.TacticsGUIInterface;

import java.util.List;

/**
 * RandomAndCallNeighborsTacticsをGUIから利用するためのインタフェース
 *
 * @author 遠藤拓斗 on 2017/05/30.
 */
public class RandomAndCallNeighborsTacticsGUIInterface implements TacticsGUIInterface {
    private int turnInterval = 10;
    private double limitOfTurningAngle = 0.5;
    private FormattedTextFieldWithLabel<Integer> certainNumberField = new IntTextFieldWithLabel("一度に呼び出すドローン数", 5);
    private FormattedTextFieldWithLabel<Integer> thresholdTimeField = new IntTextFieldWithLabel("一定時間(最近の定義)", 10);

    private CheckBox useSpiral = new CheckBox("最初螺線探索をする");
    private FormattedTextFieldWithLabel<Double> searchRatioField = new DoubleTextFieldWithLabel("探索割合(螺線)", 0.5);

    private VBox vBoxForFiltersManagement = new VBox();
    private CheckBox callOnlyRandomWalkingOrSpiralDrones = new CheckBox("現在呼び出されて向かっているドローンは呼び出さない");
    private FiltersManagementGUIInterface fmgi = new FiltersManagementGUIInterface();

    @Override
    public void makeGUI(VBox vBox) {
        vBoxForFiltersManagement.getChildren().clear();
        fmgi.makeGUI(vBoxForFiltersManagement);
        vBox.getChildren().addAll(certainNumberField,
                thresholdTimeField,
                useSpiral,
                searchRatioField,
                vBoxForFiltersManagement,
                callOnlyRandomWalkingOrSpiralDrones);
    }

    @Override
    public Tactics getTactics(int numDrone, double viewRangeRadius, int limitTime, List<Drone> drones) {
        int thresholdTime = thresholdTimeField.getValue();
        FiltersManagement fm = fmgi.getFiltersManagement(thresholdTime, drones);

        return new RandomAndCallNeighborsTactics(numDrone, viewRangeRadius, turnInterval, limitOfTurningAngle,
                useSpiral.isSelected(), searchRatioField.getValue(), drones,
                fm, certainNumberField.getValue(), callOnlyRandomWalkingOrSpiralDrones.isSelected());
    }

    @Override
    public String explain() {
        return "ランダムウォーク・呼び出し";
    }
}
