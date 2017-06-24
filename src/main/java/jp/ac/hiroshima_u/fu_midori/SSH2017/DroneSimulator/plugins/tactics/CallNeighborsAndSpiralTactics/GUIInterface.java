package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.CallNeighborsAndSpiralTactics;

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
 * @author 遠藤拓斗 on 2017/06/08.
 */
public class GUIInterface implements TacticsGUIInterface {
    private int turnInterval = 10;
    private double limitOfTurningAngle = 0.5;
    private FormattedTextFieldWithLabel<Integer> certainNumberField = new IntTextFieldWithLabel("一度に呼び出すドローン数", 5);
    private FormattedTextFieldWithLabel<Integer> thresholdTimeField = new IntTextFieldWithLabel("一定時間(最近の定義)", 10);

    private CheckBox useSpiral = new CheckBox("最初螺線探索をする");
    private FormattedTextFieldWithLabel<Double> searchRatioField = new DoubleTextFieldWithLabel("探索割合(最初の螺線)", 0.5);
    private FormattedTextFieldWithLabel<Double> searchRatio2Field = new DoubleTextFieldWithLabel("探索割合(呼び出された後の螺線)", 1);
    private FormattedTextFieldWithLabel<Integer> timeToContinueSpiral2SinceLastFind = new IntTextFieldWithLabel("最後に被災者を発見してから\n螺旋探索(呼び出された後)を続ける時間[s]", 300);

    private VBox vBoxForFiltersManagement = new VBox();
    private FiltersManagementGUIInterface fmgi = new FiltersManagementGUIInterface();

    @Override
    public void makeGUI(VBox vBox) {
        vBoxForFiltersManagement.getChildren().clear();
        fmgi.makeGUI(vBoxForFiltersManagement);
        vBox.getChildren().addAll(certainNumberField,
                thresholdTimeField,
                useSpiral,
                searchRatioField,
                searchRatio2Field,
                timeToContinueSpiral2SinceLastFind,
                vBoxForFiltersManagement);
    }

    @Override
    public Tactics getTactics(int numDrone, double viewRangeRadius, int limitTime, List<Drone> drones) {
        int thresholdTime = thresholdTimeField.getValue();
        FiltersManagement fm = fmgi.getFiltersManagement(thresholdTime, drones);

        return new CallNeighborsAndSpiralTactics(numDrone, viewRangeRadius, turnInterval, limitOfTurningAngle,
                useSpiral.isSelected(), searchRatioField.getValue(), searchRatio2Field.getValue(), drones, fm, certainNumberField.getValue(), timeToContinueSpiral2SinceLastFind.getValue());
    }

    @Override
    public String explain() {
        return "呼び出し後螺線探索";
    }
}
