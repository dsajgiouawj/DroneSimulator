package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.simulator;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.TacticsCUIInterface;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placing.PlacingVictimsCUIInterface;

import java.util.Scanner;

/**
 * SimulatorをCUIから使うためのクラス
 *
 * @author 遠藤拓斗 on 2017/05/29.
 */
public class SimulatorCUIInterface {
    private int numDrone;
    private int population;
    private int limitTime;
    private double viewRangeRadius;
    private TacticsCUIInterface tacticsUI;
    private PlacingVictimsCUIInterface placingVictimsUI;
    private Scanner scanner = new Scanner(System.in, "UTF-8");

    public void readInfo() {
        System.err.println("ドローンの台数");
        System.err.println("被災者の数");
        System.err.println("ドローンの稼働時間[s]");
        System.err.println("ドローンの視野の半径[m]");
        numDrone = scanner.nextInt();
        population = scanner.nextInt();
        limitTime = scanner.nextInt();
        viewRangeRadius = scanner.nextDouble();
        readTacticsInfo();
        readPlacingInfo();
    }

    private void readTacticsInfo() {
        System.err.println("TacticsCUIInterfaceのFQCN");
        String tacticsInterfaceClassName = scanner.next();
        Class tacticsInterfaceClass;
        try {
            tacticsInterfaceClass = Class.forName(tacticsInterfaceClassName);
        } catch (ClassNotFoundException e) {
            System.err.println("Tacticsクラスが見つかりません");
            throw new RuntimeException();
        }
        try {
            tacticsUI = (TacticsCUIInterface) tacticsInterfaceClass.newInstance();
        } catch (InstantiationException e) {
            System.err.println("Tacticsクラスのインスタンスを取得できません");
            throw new RuntimeException();
        } catch (IllegalAccessException e) {
            System.err.println("Tacticsクラスにアクセスできません");
            throw new RuntimeException();
        }
        System.err.println(tacticsUI.explain());
        tacticsUI.readInfo();
    }

    private void readPlacingInfo() {
        System.err.println("PlacingVictimsCUIInterfaceのFQCN");
        String placingInterfaceClassName = scanner.next();
        Class placingInterfaceClass;
        try {
            placingInterfaceClass = Class.forName(placingInterfaceClassName);
        } catch (ClassNotFoundException e) {
            System.err.println("PlacingVictimsクラスが見つかりません");
            throw new RuntimeException();
        }
        try {
            placingVictimsUI = (PlacingVictimsCUIInterface) placingInterfaceClass.newInstance();
        } catch (InstantiationException e) {
            System.err.println("PlacingVictimsクラスのインスタンスを取得できません");
            throw new RuntimeException();
        } catch (IllegalAccessException e) {
            System.err.println("PlacingVictimsクラスにアクセスできません");
            throw new RuntimeException();
        }
        System.err.println(placingVictimsUI.explain());
        placingVictimsUI.readInfo();
    }

    public Simulator getSimulator() {
        return new Simulator(tacticsUI, limitTime, numDrone, viewRangeRadius, population, placingVictimsUI);
    }
}
