package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.simulator;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.ITacticsCUIFrontend;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placer.VictimsPlacerCUIFrontend;

import java.util.Scanner;

/**
 * SimulatorをCUIから使うためのクラス
 *
 * @author 遠藤拓斗 on 2017/05/29.
 */
public class SimulatorCUIFrontend {
    private int numDrone;
    private int population;
    private int limitTime;
    private double viewRangeRadius;
    private ITacticsCUIFrontend tacticsCUIFrontend;
    private VictimsPlacerCUIFrontend victimsPlacerCUIFrontend;

    public void readInfo(Scanner scanner) {
        System.err.println("ドローンの台数");
        System.err.println("被災者の数");
        System.err.println("ドローンの稼働時間[s]");
        System.err.println("ドローンの視野の半径[m]");
        numDrone = scanner.nextInt();
        population = scanner.nextInt();
        limitTime = scanner.nextInt();
        viewRangeRadius = scanner.nextDouble();
        readTacticsInfo(scanner);
        readPlacerInfo(scanner);
    }

    private void readTacticsInfo(Scanner scanner) {
        System.err.println("TacticsCUIFrontendのFQCN");
        String tacticsInterfaceClassName = scanner.next();
        Class tacticsInterfaceClass;
        try {
            tacticsInterfaceClass = Class.forName(tacticsInterfaceClassName);
        } catch (ClassNotFoundException e) {
            System.err.println("Tacticsクラスが見つかりません");
            throw new RuntimeException();
        }
        try {
            tacticsCUIFrontend = (ITacticsCUIFrontend) tacticsInterfaceClass.newInstance();
        } catch (InstantiationException e) {
            System.err.println("Tacticsクラスのインスタンスを取得できません");
            throw new RuntimeException();
        } catch (IllegalAccessException e) {
            System.err.println("Tacticsクラスにアクセスできません");
            throw new RuntimeException();
        }
        tacticsCUIFrontend.readInfo(scanner);
    }

    private void readPlacerInfo(Scanner scanner) {
        System.err.println("VictimsPlacerCUIFrontendの実装クラスのFQCN");
        String placerFrontendClassName = scanner.next();
        Class placerFrontendClass;
        try {
            placerFrontendClass = Class.forName(placerFrontendClassName);
        } catch (ClassNotFoundException e) {
            System.err.println("VictimsPlacerCUIFrontendの実装クラスが見つかりません");
            throw new RuntimeException();
        }
        try {
            victimsPlacerCUIFrontend = (VictimsPlacerCUIFrontend) placerFrontendClass.newInstance();
        } catch (InstantiationException e) {
            System.err.println("VictimsPlacerの実装クラスのインスタンスを取得できません");
            throw new RuntimeException();
        } catch (IllegalAccessException e) {
            System.err.println("VictimsPlacerの実装クラスにアクセスできません");
            throw new RuntimeException();
        }
        victimsPlacerCUIFrontend.readInfo(scanner);
    }

    public Simulator getSimulator() {
        return new Simulator(tacticsCUIFrontend, limitTime, numDrone, viewRangeRadius, population, victimsPlacerCUIFrontend);
    }
}
