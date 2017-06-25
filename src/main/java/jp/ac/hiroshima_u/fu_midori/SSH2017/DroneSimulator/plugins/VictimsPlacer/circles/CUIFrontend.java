package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.VictimsPlacer.circles;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placer.VictimsPlacer;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placer.VictimsPlacerCUIFrontend;

import java.util.Scanner;

/**
 * CirclesPlacerをCUIから利用
 *
 * @author 遠藤拓斗 on 2017/05/29.
 */
public class CUIFrontend implements VictimsPlacerCUIFrontend {
    private double centerXOfRange;
    private double centerYOfRange;
    private double radiusOfRange;
    private double radiusOfCircles;
    private int numOfCircles;

    @Override
    public void readInfo(Scanner scanner) {
        System.err.println("----円の範囲----");
        System.err.println("中心点のx座標[m](double)");
        centerXOfRange = scanner.nextDouble();
        System.err.println("中心点のy座標[m](double)");
        centerYOfRange = scanner.nextDouble();
        System.err.println("半径[m](double)");
        radiusOfRange = scanner.nextDouble();
        System.err.println("----円----");
        System.err.println("半径[m](double)");
        radiusOfCircles = scanner.nextDouble();
        System.err.println("個数");
        numOfCircles = scanner.nextInt();
    }

    @Override
    public VictimsPlacer getVictimsPlacer() {
        return new Placer(new Point2D(centerXOfRange, centerYOfRange), radiusOfRange, radiusOfCircles, numOfCircles);
    }
}
