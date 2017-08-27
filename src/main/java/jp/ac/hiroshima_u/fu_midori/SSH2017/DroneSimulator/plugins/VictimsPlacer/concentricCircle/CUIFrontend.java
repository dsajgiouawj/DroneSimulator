package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.VictimsPlacer.concentricCircle;

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
    private double innerRadius;
    private double outerRadius;

    @Override
    public void readInfo(Scanner scanner) {
        System.err.println("中心点のx座標[m](double)");
        centerX = scanner.nextDouble();
        System.err.println("中心点のy座標[m](double)");
        centerY = scanner.nextDouble();
        System.err.println("内半径[m](double)");
        innerRadius = scanner.nextDouble();
        System.err.println("外半径[m](double");
        outerRadius = scanner.nextDouble();
    }

    @Override
    public VictimsPlacer getVictimsPlacer() {
        return new Placer(new Point2D(centerX, centerY), innerRadius, outerRadius);
    }
}
