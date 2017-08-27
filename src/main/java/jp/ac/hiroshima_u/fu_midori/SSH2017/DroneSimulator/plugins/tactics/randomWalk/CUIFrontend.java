package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomWalk;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.ITactics;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.ITacticsCUIFrontend;

import java.util.List;
import java.util.Scanner;

/**
 * @author 遠藤拓斗 on 2017/8/27.
 */
public class CUIFrontend implements ITacticsCUIFrontend {
    private double limitOfTurningAngle;
    private int turnInterval;
    private int thresholdTime;
    private double probability;

    @Override
    public void readInfo(Scanner scanner) {
        System.err.println("旋回制限角度[±rad](double)");
        limitOfTurningAngle = scanner.nextDouble();
        System.err.println("旋回間隔[s](int)");
        turnInterval = scanner.nextInt();
        System.err.println("最後に発見してから呼び出されるまでの時間[s](int)");
        thresholdTime = scanner.nextInt();
        System.err.println("呼び出される確率(0以上1以下)");
        probability = scanner.nextDouble();
    }

    @Override
    public ITactics getTactics(int numDrone, double viewRangeRadius, int limitTime, List<Drone> drones) {
        return new Tactics(numDrone, viewRangeRadius, limitTime, drones, limitOfTurningAngle, turnInterval, thresholdTime, probability);
    }
}
