package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.spiralTactics;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.Tactics;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.TacticsCUIInterface;

import java.util.Scanner;

/**
 * SpiralTacticsをCUIから利用するためのインタフェース
 *
 * @author 遠藤拓斗 on 2017/05/29.
 */
public class SpiralTacticsCUIInterface implements TacticsCUIInterface {
    private double searchRatio;

    @Override
    public String explain() {
        return "探索割合(0より大きく1以下)";
    }

    @Override
    public void readInfo() {
        Scanner scanner = new Scanner(System.in, "UTF-8");
        searchRatio = scanner.nextDouble();
    }

    @Override
    public Tactics getTactics(int numDrone, double viewRangeRadius, int limitTime) {
        return new SpiralTactics(searchRatio, numDrone, viewRangeRadius);
    }
}
