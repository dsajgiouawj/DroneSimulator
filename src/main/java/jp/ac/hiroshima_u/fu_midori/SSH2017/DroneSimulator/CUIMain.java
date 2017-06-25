package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.simulator.Simulator;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.simulator.SimulatorCUIFrontend;

import java.util.Scanner;

/**
 * CUI時のMainクラス
 *
 * @author 遠藤拓斗 on 2017/05/29.
 */
public class CUIMain {
    public static void main(String[] args) {
        System.err.println("実行回数");
        Scanner scanner = new Scanner(System.in, "UTF-8");
        int times = scanner.nextInt();
        SimulatorCUIFrontend simulatorCUIFrontend = new SimulatorCUIFrontend();
        simulatorCUIFrontend.readInfo(scanner);
        {
            Simulator simulatorInfo = simulatorCUIFrontend.getSimulator();
            System.out.print(times + ",");
            System.out.print("\"" + simulatorInfo + "\",");
            System.out.print("\"" + simulatorInfo.getPlacer() + "\",");
            System.out.print("\"" + simulatorInfo.getTactics() + "\"");
        }
        System.err.println("出力形式:");
        System.err.println("回数,simulator情報,placer情報,tactics情報,data...");
        System.err.println("実行中");
        for (int i = 0; i < times; i++) {
            Simulator simulator = simulatorCUIFrontend.getSimulator();
            simulator.runThrough();
            int numOfFoundVictims = simulator.getVictims().foundPopulation();
            System.out.print("," + numOfFoundVictims);
        }
    }
}
