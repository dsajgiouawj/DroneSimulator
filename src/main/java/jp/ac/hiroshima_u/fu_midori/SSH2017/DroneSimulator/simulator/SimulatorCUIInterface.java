package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.simulator;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.DroneImpl;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.Tactics;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.TacticsCUIInterface;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.Victim;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.VictimXComparator;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placing.PlacingVictims;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placing.PlacingVictimsCUIInterface;

import java.util.ArrayList;
import java.util.List;
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
    private TacticsCUIInterface tacticsInterface;
    private PlacingVictimsCUIInterface placingVictimsInterface;
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
            tacticsInterface = (TacticsCUIInterface) tacticsInterfaceClass.newInstance();
        } catch (InstantiationException e) {
            System.err.println("Tacticsクラスのインスタンスを取得できません");
            throw new RuntimeException();
        } catch (IllegalAccessException e) {
            System.err.println("Tacticsクラスにアクセスできません");
            throw new RuntimeException();
        }
        System.err.println(tacticsInterface.explain());
        tacticsInterface.readInfo();
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
            placingVictimsInterface = (PlacingVictimsCUIInterface) placingInterfaceClass.newInstance();
        } catch (InstantiationException e) {
            System.err.println("PlacingVictimsクラスのインスタンスを取得できません");
            throw new RuntimeException();
        } catch (IllegalAccessException e) {
            System.err.println("PlacingVictimsクラスにアクセスできません");
            throw new RuntimeException();
        }
        System.err.println(placingVictimsInterface.explain());
        placingVictimsInterface.readInfo();
    }

    public Simulator getSimulator() {
        PlacingVictims placingVictims = placingVictimsInterface.getPlacingVictims();
        List<Victim> victims = placingVictims.placeVictims(population);
        victims.sort(new VictimXComparator());
        List<DroneImpl> drones = new ArrayList<>();
        for (int i = 0; i < numDrone; i++) {
            drones.add(new DroneImpl(viewRangeRadius, victims));
        }
        List<Drone> drones1 = new ArrayList<>();
        drones1.addAll(drones);
        Tactics tactics = tacticsInterface.getTactics(numDrone, viewRangeRadius, limitTime, drones1);
        return new Simulator(tactics, limitTime, drones, victims);
    }
}
