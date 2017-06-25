package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.DoubleTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.FormattedTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.IntTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.FiltersManagement;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.FiltersManagementViewerFrontend;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.ITactics;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.ITacticsViewerFrontend;

import java.util.List;

/**
 * RandomAndCallNeighborsTacticsをviewerから利用するためのインタフェース
 *
 * @author 遠藤拓斗 on 2017/05/30.
 */
public class ViewerFrontend implements ITacticsViewerFrontend {
    private int turnInterval = 10;
    private double limitOfTurningAngle = 0.5;
    private FormattedTextFieldWithLabel<Integer> certainNumberField = new IntTextFieldWithLabel("一度に呼び出すドローン数", 5);

    private CheckBox useSpiral = new CheckBox("最初螺線探索をする");
    private FormattedTextFieldWithLabel<Double> searchRatioField = new DoubleTextFieldWithLabel("探索割合(螺線)", 0.5);

    private VBox vBoxForFiltersManagement = new VBox();
    private CheckBox callOnlyRandomWalkingOrSpiralDrones = new CheckBox("現在呼び出されて向かっているドローンは呼び出さない");
    private FiltersManagementViewerFrontend fmgi = new FiltersManagementViewerFrontend();

    @Override
    public void makeGUI(VBox vBox) {
        vBoxForFiltersManagement.getChildren().clear();
        fmgi.makeGUI(vBoxForFiltersManagement);
        vBox.getChildren().addAll(certainNumberField,
                useSpiral,
                searchRatioField,
                vBoxForFiltersManagement,
                callOnlyRandomWalkingOrSpiralDrones);
    }

    @Override
    public ITactics getTactics(int numDrone, double viewRangeRadius, int limitTime, List<Drone> drones) {
        FiltersManagement fm = fmgi.getFiltersManagement(drones);

        return new Tactics(numDrone, viewRangeRadius, turnInterval, limitOfTurningAngle,
                useSpiral.isSelected(), searchRatioField.getValue(), drones,
                fm, certainNumberField.getValue(), callOnlyRandomWalkingOrSpiralDrones.isSelected());
    }

    @Override
    public String explain() {
        return "ランダムウォーク・呼び出し";
    }
}
