package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.placingVictims.circles;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placing.PlacingVictims;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placing.PlacingVictimsCUIInterface;

import java.util.Scanner;

/**
 * PlacingCirclesをCUIから利用するためのインタフェース
 *
 * @author 遠藤拓斗 on 2017/05/29.
 */
public class PlacingCirclesCUIInterface implements PlacingVictimsCUIInterface {
    private double centerXOfRange;
    private double centerYOfRange;
    private double radiusOfRange;
    private double radiusOfCircles;
    private int numOfCircles;

    @Override
    public String explain() {
        return "----円の範囲----" +
                "中心点のx座標[m](double)\n" +
                "中心点のy座標[m](double)\n" +
                "半径[m](double)" +
                "----円----" +
                "半径[m](double)" +
                "個数";
    }

    @Override
    public void readInfo() {
        Scanner scanner = new Scanner(System.in, "UTF-8");
        centerXOfRange = scanner.nextDouble();
        centerYOfRange = scanner.nextDouble();
        radiusOfRange = scanner.nextDouble();
        radiusOfCircles = scanner.nextDouble();
        numOfCircles = scanner.nextInt();
    }

    @Override
    public PlacingVictims getPlacingVictims() {
        return new PlacingCircles(new Point2D(centerXOfRange, centerYOfRange), radiusOfRange, radiusOfCircles, numOfCircles);
    }
}
