package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.FilterManagementCUIInterface;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.Tactics;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.TacticsCUIInterface;

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
    private boolean useSpiral;
    private double searchRatio;
    private FilterManagementCUIInterface fmci = new FilterManagementCUIInterface();
    private boolean callOnlyRandomWalkingOrSpiral;


    @Override
    public String explain() {
        return "一度に呼び出すドローン数(int)\n" +
                "一定時間(最近の定義)(int)\n" +
                "最初螺線探索をする(boolean)\n" +
                "探索割合(螺線)(double)" +
                fmci.explain() +
                "現在呼び出し元に向かっているドローンは呼び出さない";
    }

    @Override
    public void readInfo() {
        Scanner scanner = new Scanner(System.in, "UTF-8");
        certainNumber = scanner.nextInt();
        thresholdTime = scanner.nextInt();
        useSpiral = scanner.nextBoolean();
        searchRatio = scanner.nextDouble();
        fmci.readInfo();
        callOnlyRandomWalkingOrSpiral = scanner.nextBoolean();
    }

    @Override
    public Tactics getTactics(int numDrone, double viewRangeRadius, int limitTime, List<Drone> drones) {

        return new RandomAndCallNeighborsTactics(numDrone, viewRangeRadius, turnInterval, limitOfTurningAngle,
                useSpiral, searchRatio, drones, fmci.getFilterManagement(thresholdTime, drones), certainNumber, callOnlyRandomWalkingOrSpiral);
    }
}
