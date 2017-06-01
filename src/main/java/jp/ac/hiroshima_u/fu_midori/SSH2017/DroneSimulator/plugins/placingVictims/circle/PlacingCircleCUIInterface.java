package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.placingVictims.circle;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placing.PlacingVictims;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placing.PlacingVictimsCUIInterface;

import java.util.Scanner;

/**
 * PlacingCircleをCUIから利用するためのインタフェース
 *
 * @author 遠藤拓斗 on 2017/05/29.
 */
public class PlacingCircleCUIInterface implements PlacingVictimsCUIInterface {
    private double centerX;
    private double centerY;
    private double radius;

    @Override
    public String explain() {
        return "中心点のx座標[m](double)\n" +
                "中心点のy座標[m](double)\n" +
                "半径[m](double)";
    }

    @Override
    public void readInfo() {
        Scanner scanner = new Scanner(System.in, "UTF-8");
        centerX = scanner.nextDouble();
        centerY = scanner.nextDouble();
        radius = scanner.nextDouble();
    }

    @Override
    public PlacingVictims getPlacingVictims() {
        return new PlacingCircle(new Point2D(centerX, centerY), radius);
    }
}
