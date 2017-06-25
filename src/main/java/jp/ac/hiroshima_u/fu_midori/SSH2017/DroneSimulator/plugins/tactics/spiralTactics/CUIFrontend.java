package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.spiralTactics;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.ITactics;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.ITacticsCUIFrontend;

import java.util.List;
import java.util.Scanner;

/**
 * SpiralTacticsをCUIから利用するためのインタフェース
 *
 * @author 遠藤拓斗 on 2017/05/29.
 */
public class CUIFrontend implements ITacticsCUIFrontend {
    private double searchRatio;

    @Override
    public void readInfo(Scanner scanner) {
        System.err.println("探索割合(0より大きく1以下)");
        searchRatio = scanner.nextDouble();
    }

    @Override
    public ITactics getTactics(int numDrone, double viewRangeRadius, int limitTime, List<Drone> drones) {
        return new Tactics(searchRatio, numDrone, viewRangeRadius, drones);
    }
}
