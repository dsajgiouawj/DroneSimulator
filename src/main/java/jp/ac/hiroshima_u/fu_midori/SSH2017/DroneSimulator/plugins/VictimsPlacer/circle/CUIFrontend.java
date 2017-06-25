package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.VictimsPlacer.circle;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placer.VictimsPlacer;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placer.VictimsPlacerCUIFrontend;

import java.util.Scanner;

/**
 * CirclePlacerをCUIから利用
 *
 * @author 遠藤拓斗 on 2017/05/29.
 */
public class CUIFrontend implements VictimsPlacerCUIFrontend {
    private double centerX;
    private double centerY;
    private double radius;

    @Override
    public void readInfo(Scanner scanner) {
        System.err.println("中心点のx座標[m](double)");
        centerX = scanner.nextDouble();
        System.err.println("中心点のy座標[m](double)");
        centerY = scanner.nextDouble();
        System.err.println("半径[m](double)");
        radius = scanner.nextDouble();
    }

    @Override
    public VictimsPlacer getVictimsPlacer() {
        return new Placer(new Point2D(centerX, centerY), radius);
    }
}
