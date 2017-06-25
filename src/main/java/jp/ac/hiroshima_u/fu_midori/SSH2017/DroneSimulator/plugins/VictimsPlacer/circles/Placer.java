package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.VictimsPlacer.circles;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.Victims;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placer.VictimsPlacer;

import java.util.ArrayList;
import java.util.List;

/**
 * 被災者を複数の円上に配置します。
 *
 * @author 遠藤拓斗 on 2017/05/17.
 */
public class Placer implements VictimsPlacer {
    private Point2D centerOfRange;
    private double radiusOfRange;
    private double radiusOfCircles;
    private int numOfCircles;
    private double radiusOfCenterRange;

    /**
     * コンストラクタ
     *
     * @param centerOfRange   円を配置する範囲の中心
     * @param radiusOfRange   円を配置する範囲の半径
     * @param radiusOfCircles 円の半径
     * @param numOfCircles    円の個数
     * @throws IllegalArgumentException radiusOfCircles>=radiusOfRangeの場合
     */
    Placer(Point2D centerOfRange, double radiusOfRange, double radiusOfCircles, int numOfCircles) throws IllegalArgumentException {
        if (radiusOfCircles >= radiusOfRange) throw new IllegalArgumentException("範囲の半径は円の半径より大きい必要があります");
        this.centerOfRange = centerOfRange;
        this.radiusOfRange = radiusOfRange;
        this.radiusOfCircles = radiusOfCircles;
        this.numOfCircles = numOfCircles;
        this.radiusOfCenterRange = radiusOfRange - radiusOfCircles;
    }

    @Override
    public Victims placeVictims(int population) {
        List<Point2D> points = new ArrayList<>();
        int residualPopulation = population;
        int residualCircles = numOfCircles;
        for (int i = 0; i < numOfCircles; i++) {
            points.addAll(generateCircleInRange(residualPopulation / residualCircles));
            residualPopulation -= residualPopulation / residualCircles;
            residualCircles--;
        }
        return new Victims(points);
    }

    private Point2D generatePointInCircle(Point2D center, double radius) {
        double x = Math.random() * radius * 2 - radius;
        double y = Math.random() * radius * 2 - radius;
        if (x * x + y * y <= radius * radius) return new Point2D(x + center.getX(), y + center.getY());
        else return generatePointInCircle(center, radius);
    }

    private List<Point2D> generateCircleInRange(int numOfVictims) {
        List<Point2D> points = new ArrayList<>();
        double x = Math.random() * radiusOfCenterRange * 2 - radiusOfCenterRange;
        double y = Math.random() * radiusOfCenterRange * 2 - radiusOfCenterRange;
        if (x * x + y * y <= radiusOfCenterRange * radiusOfCenterRange) {
            x += centerOfRange.getX();
            y += centerOfRange.getY();
            for (int i = 0; i < numOfVictims; i++) {
                points.add(generatePointInCircle(new Point2D(x, y), radiusOfCircles));
            }
        } else {
            return generateCircleInRange(numOfVictims);
        }
        return points;
    }

    @Override
    public String toString() {
        return "複数の円上に配置 中心:(" + centerOfRange.getX() + "," + centerOfRange.getY() + ")" + "親半径:" + radiusOfRange + "子半径:" + radiusOfCircles + "クラスタ数:" + numOfCircles;
    }
}