package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.FilterManagementCUIFrontend;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.ITactics;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.ITacticsCUIFrontend;

import java.util.List;
import java.util.Scanner;

/**
 * RandomAndCallNeighborsTacticsをCUIから利用するためのインタフェース
 *
 * @author 遠藤拓斗 on 2017/05/31.
 */
public class CUIFrontend implements ITacticsCUIFrontend {
    private int turnInterval = 10;
    private double limitOfTurningAngle = 0.5;

    private int numOfDronesToCall;
    private boolean useSpiral;
    private double searchRatio;
    private FilterManagementCUIFrontend fmci = new FilterManagementCUIFrontend();
    private boolean callOnlyRandomWalkingOrSpiral;

    @Override
    public void readInfo(Scanner scanner) {
        System.err.println("一度に呼び出すドローン数(int)");
        numOfDronesToCall = scanner.nextInt();
        System.err.println("最初螺線探索をする(boolean)");
        useSpiral = scanner.nextBoolean();
        if (useSpiral) {
            System.err.println("探索割合(double)");
            searchRatio = scanner.nextDouble();
        }
        fmci.readInfo(scanner);
        System.err.println("現在呼び出し元に向かっているドローンは呼び出さない(boolean)");
        callOnlyRandomWalkingOrSpiral = scanner.nextBoolean();
    }

    @Override
    public ITactics getTactics(int numDrone, double viewRangeRadius, int limitTime, List<Drone> drones) {
        return new Tactics(numDrone, viewRangeRadius, turnInterval, limitOfTurningAngle,
                useSpiral, searchRatio, drones, fmci.getFilterManagement(drones), numOfDronesToCall, callOnlyRandomWalkingOrSpiral);
    }
}
