package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.CallNeighborsAndSpiralTactics;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.FilterManagementCUIInterface;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.Tactics;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.TacticsCUIInterface;

import java.util.List;
import java.util.Scanner;

/**
 * RandomAndCallNeighborsAndThenSpiralTacticsをCUIから利用するためのインタフェース
 *
 * @author 遠藤拓斗 on 2017/06/08.
 */
public class CUIInterface implements TacticsCUIInterface {
    private int turnInterval = 10;
    private double limitOfTurningAngle = 0.5;

    private int certainNumber;
    private int thresholdTime;
    private boolean useSpiral;
    private double searchRatio;
    private double searchRatio2;
    private FilterManagementCUIInterface fmci = new FilterManagementCUIInterface();

    @Override
    public String explain() {
        return "一度に呼び出すドローン数(int)\n" +
                "一定時間(最近の定義)(int)\n" +
                "最初螺線探索をする(boolean)\n" +
                "探索割合(最初の螺線)(double)" +
                "探索割合(呼び出された後の螺線)" +
                fmci.explain();
    }

    @Override
    public void readInfo() {
        Scanner scanner = new Scanner(System.in, "UTF-8");
        certainNumber = scanner.nextInt();
        thresholdTime = scanner.nextInt();
        useSpiral = scanner.nextBoolean();
        searchRatio = scanner.nextDouble();
        searchRatio2 = scanner.nextDouble();
        fmci.readInfo();
    }

    @Override
    public Tactics getTactics(int numDrone, double viewRangeRadius, int limitTime, List<Drone> drones) {

        return new CallNeighborsAndSpiralTactics(numDrone, viewRangeRadius, turnInterval, limitOfTurningAngle,
                useSpiral, searchRatio, searchRatio2, drones, fmci.getFilterManagement(thresholdTime, drones), certainNumber);
    }
}
