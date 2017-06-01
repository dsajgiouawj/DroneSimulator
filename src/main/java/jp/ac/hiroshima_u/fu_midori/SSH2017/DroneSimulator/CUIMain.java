package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.simulator.Simulator;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.simulator.SimulatorCUIInterface;

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
        SimulatorCUIInterface simulatorCUIInterface = new SimulatorCUIInterface();
        simulatorCUIInterface.readInfo();
        for (int i = 0; i < times; i++) {
            Simulator simulator = simulatorCUIInterface.getSimulator();
            simulator.runThrough();
            int numOfFoundVictims = (int) simulator.getVictims().stream().filter(v -> v.isFound()).count();
            if (i != 0) System.out.print(",");
            System.out.print(numOfFoundVictims);
        }
    }
}
