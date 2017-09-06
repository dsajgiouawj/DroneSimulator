package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndSpiralTactics;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling.FilterManagementCUIFrontend;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.ITactics;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.ITacticsCUIFrontend;

import java.util.List;
import java.util.Scanner;

/**
 * RandomAndCallNeighborsAndThenSpiralTacticsをCUIから利用するためのインタフェース
 *
 * @author 遠藤拓斗 on 2017/06/08.
 */
public class CUIFrontend implements ITacticsCUIFrontend {
    private int turnInterval = 10;
    private double limitOfTurningAngle = 0.5;

    private int numOfDronesToCall;
    private boolean useSpiral;
    private double searchRatio;
    private double searchRatio2;
    private int timeToContinueSpiral;
    private FilterManagementCUIFrontend fmci = new FilterManagementCUIFrontend();

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
        System.err.println("探索割合(呼び出された後の螺線)(double)");
        searchRatio2 = scanner.nextDouble();
        System.err.println("最後に被災者を発見してから螺旋探索を続ける時間[s](int)");
        timeToContinueSpiral = scanner.nextInt();
        fmci.readInfo(scanner);
    }

    @Override
    public ITactics getTactics(int numDrone, double viewRangeRadius, int limitTime, List<Drone> drones) {
        return new Tactics(numDrone, viewRangeRadius, turnInterval, limitOfTurningAngle,
                useSpiral, searchRatio, searchRatio2, drones, fmci.getFilterManagement(drones), numOfDronesToCall, limitTime, timeToContinueSpiral);
    }
}
