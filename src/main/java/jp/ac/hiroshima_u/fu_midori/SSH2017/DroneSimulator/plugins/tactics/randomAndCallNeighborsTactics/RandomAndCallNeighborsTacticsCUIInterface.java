package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.CallingSubTactics;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.callIfTheFindingDroneHaveNotCalledRecently.CallIfTheFindingDroneHasNotCalledRecently;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.callingACertainNumberOfDrones.CallingACertainNumberOfDrones;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.callingDronesWhichHasNeverBeenCalled.CallingDronesWhichHaveNeverBeenCalled;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.callingDronesWhichHaveNotCalledRecently.CallingDronesWhichHaveNotCalledRecently;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.callingDronesWhichHaveNotFindRecently.CallingDronesWhichHaveNotFindRecently;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.callingOnlyRandomWalkingOrSpiralDrones.CallingOnlyRandomWalkingOrSpiralDrones;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.Tactics;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.TacticsCUIInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * RandomAndCallNeighborsTacticsをCUIから利用するためのインタフェース
 *
 * @author 遠藤拓斗 on 2017/05/31.
 */
public class RandomAndCallNeighborsTacticsCUIInterface implements TacticsCUIInterface {
    private int turnInterval = 10;
    private double limitOfTurningAngle = 0.5;

    private int certainNumber;
    private int thresholdTime;
    private boolean callOnlyDronesWhichHaveNeverBeenCalled;
    private boolean callOnlyDronesWhichHaveNotCalledRecently;
    private boolean callOnlyDronesWhichHaveNotFindRecently;
    private boolean callOnlyRandomWalkingOrSpiralDrones;
    private boolean callOnlyIfTheDroneHasNotCalledRecently;
    private boolean useSpiral;
    private double searchRatio;

    @Override
    public String explain() {
        return "一度に呼び出すドローン数(int)\n" +
                "一定時間(最近の定義)(int)\n" +
                "呼び出されたことのあるドローンは呼ばない(boolean)\n" +
                "最近呼び出されたことのあるドローンは呼ばない(boolean)\n" +
                "最近発見したことのあるドローンは呼ばない(boolean)\n" +
                "現在呼び出されたところに向かっているドローンは呼ばない(boolean)\n" +
                "最近呼び出していない場合のみ呼ぶ(boolean)\n" +
                "最初螺線探索をする(boolean)\n" +
                "探索割合(螺線)(double)";
    }

    @Override
    public void readInfo() {
        Scanner scanner = new Scanner(System.in, "UTF-8");
        certainNumber = scanner.nextInt();
        thresholdTime = scanner.nextInt();
        callOnlyDronesWhichHaveNeverBeenCalled = scanner.nextBoolean();
        callOnlyDronesWhichHaveNotCalledRecently = scanner.nextBoolean();
        callOnlyDronesWhichHaveNotFindRecently = scanner.nextBoolean();
        callOnlyRandomWalkingOrSpiralDrones = scanner.nextBoolean();
        callOnlyIfTheDroneHasNotCalledRecently = scanner.nextBoolean();
        useSpiral = scanner.nextBoolean();
        searchRatio = scanner.nextDouble();
    }

    @Override
    public Tactics getTactics(int numDrone, double viewRangeRadius, int limitTime) {
        List<CallingSubTactics> conditions = new ArrayList<>();
        if (callOnlyDronesWhichHaveNeverBeenCalled)
            conditions.add(new CallingDronesWhichHaveNeverBeenCalled());
        if (callOnlyDronesWhichHaveNotCalledRecently)
            conditions.add(new CallingDronesWhichHaveNotCalledRecently(thresholdTime));
        if (callOnlyDronesWhichHaveNotFindRecently)
            conditions.add(new CallingDronesWhichHaveNotFindRecently(thresholdTime));
        if (callOnlyRandomWalkingOrSpiralDrones)
            conditions.add(new CallingOnlyRandomWalkingOrSpiralDrones());
        if (callOnlyIfTheDroneHasNotCalledRecently)
            conditions.add(new CallIfTheFindingDroneHasNotCalledRecently(thresholdTime));

        return new RandomAndCallNeighborsTactics(numDrone, viewRangeRadius, turnInterval, limitOfTurningAngle,
                new CallingACertainNumberOfDrones(certainNumber, conditions.toArray(new CallingSubTactics[0])),
                useSpiral, searchRatio);
    }
}
