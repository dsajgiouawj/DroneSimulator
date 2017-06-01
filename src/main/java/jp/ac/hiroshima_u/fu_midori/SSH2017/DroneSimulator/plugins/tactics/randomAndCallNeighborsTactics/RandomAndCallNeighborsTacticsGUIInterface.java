package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.DoubleTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.FormattedTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.IntTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.CallingSubTactics;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.callIfTheFindingDroneHaveNotCalledRecently.CallIfTheFindingDroneHasNotCalledRecently;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.callingACertainNumberOfDrones.CallingACertainNumberOfDrones;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.callingDronesWhichHasNeverBeenCalled.CallingDronesWhichHaveNeverBeenCalled;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.callingDronesWhichHaveNotCalledRecently.CallingDronesWhichHaveNotCalledRecently;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.callingDronesWhichHaveNotFindRecently.CallingDronesWhichHaveNotFindRecently;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.callingOnlyRandomWalkingOrSpiralDrones.CallingOnlyRandomWalkingOrSpiralDrones;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.Tactics;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.TacticsGUIInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * RandomAndCallNeighborsTacticsをGUIから利用するためのインタフェース
 *
 * @author 遠藤拓斗 on 2017/05/30.
 */
public class RandomAndCallNeighborsTacticsGUIInterface implements TacticsGUIInterface {
    private int turnInterval = 10;
    private double limitOfTurningAngle = 0.5;
    private FormattedTextFieldWithLabel<Integer> certainNumberField = new IntTextFieldWithLabel("一度に呼び出すドローン数", 4);
    private FormattedTextFieldWithLabel<Integer> thresholdTimeField = new IntTextFieldWithLabel("一定時間(最近の定義)", 10);
    private CheckBox callOnlyDronesWhichHaveNeverBeenCalled = new CheckBox("呼び出されたことのあるドローンは呼ばない");
    private CheckBox callOnlyDronesWhichHaveNotCalledRecently = new CheckBox("最近呼び出されたことのあるドローンは呼ばない");
    private CheckBox callOnlyDronesWhichHaveNotFindRecently = new CheckBox("最近発見したことのあるドローンは呼ばない");
    private CheckBox callOnlyRandomWalkingOrSpiralDrones = new CheckBox("現在呼び出されたところに向かっているドローンは呼ばない");
    private CheckBox callOnlyIfTheDroneHasNotCalledRecently = new CheckBox("最近呼び出していない場合のみ呼ぶ");

    private CheckBox useSpiral = new CheckBox("最初螺線探索をする");
    private FormattedTextFieldWithLabel<Double> searchRatioField = new DoubleTextFieldWithLabel("探索割合(螺線)", 0.5);

    @Override
    public void makeGUI(VBox vBox) {
        vBox.getChildren().addAll(certainNumberField,
                thresholdTimeField,
                callOnlyDronesWhichHaveNeverBeenCalled,
                callOnlyDronesWhichHaveNotCalledRecently,
                callOnlyDronesWhichHaveNotFindRecently,
                callOnlyRandomWalkingOrSpiralDrones,
                callOnlyIfTheDroneHasNotCalledRecently,
                useSpiral,
                searchRatioField);
    }

    @Override
    public Tactics getTactics(int numDrone, double viewRangeRadius, int limitTime) {
        List<CallingSubTactics> conditions = new ArrayList<>();
        int thresholdTime = thresholdTimeField.getValue();
        if (callOnlyDronesWhichHaveNeverBeenCalled.isSelected())
            conditions.add(new CallingDronesWhichHaveNeverBeenCalled());
        if (callOnlyDronesWhichHaveNotCalledRecently.isSelected())
            conditions.add(new CallingDronesWhichHaveNotCalledRecently(thresholdTime));
        if (callOnlyDronesWhichHaveNotFindRecently.isSelected())
            conditions.add(new CallingDronesWhichHaveNotFindRecently(thresholdTime));
        if (callOnlyRandomWalkingOrSpiralDrones.isSelected())
            conditions.add(new CallingOnlyRandomWalkingOrSpiralDrones());
        if (callOnlyIfTheDroneHasNotCalledRecently.isSelected())
            conditions.add(new CallIfTheFindingDroneHasNotCalledRecently(thresholdTime));

        return new RandomAndCallNeighborsTactics(numDrone, viewRangeRadius, turnInterval, limitOfTurningAngle,
                new CallingACertainNumberOfDrones(certainNumberField.getValue(), conditions.toArray(new CallingSubTactics[0])),
                useSpiral.isSelected(), searchRatioField.getValue());
    }

    @Override
    public String explain() {
        return "ランダムウォーク・呼び出し";
    }
}
