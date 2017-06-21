package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 被災者を管理するクラス
 *
 * @author 遠藤拓斗 on 2017/06/21.
 */
public class Victims {
    private List<Victim> victims = new ArrayList<>();
    private final int population;
    private int foundPopulation;

    /**
     * コンストラクタ
     *
     * @param points 被災者の位置のリスト
     */
    public Victims(List<Point2D> points) {
        for (Point2D point : points) {
            victims.add(new Victim(point));
        }
        victims.sort(Comparator.comparingDouble(v -> v.getPoint().getX()));
        population = victims.size();
        foundPopulation = 0;
    }

    /**
     * 被災者全体の人数
     *
     * @return 被災者全体の人数
     */
    public int population() {
        return population;
    }

    /**
     * 発見された被災者の人数
     *
     * @return 発見された被災者の人数
     */
    public int foundPopulation() {
        return foundPopulation;
    }

    /**
     * point0からpoint1まで動いた時、その線分から一定距離以内の被災者を発見します
     *
     * @param point0          始点
     * @param point1          終点
     * @param viewRangeRadius 視野の半径
     * @return 発見人数
     */
    public int findPeople(Point2D point0, Point2D point1, double viewRangeRadius) {
        int numOfFoundVictims = 0;
        double minX, maxX;
        minX = Math.min(point0.getX(), point1.getX()) - viewRangeRadius;
        maxX = Math.max(point0.getX(), point1.getX()) + viewRangeRadius;
        int leftIndex = Collections.binarySearch(victims, new Victim(new Point2D(minX - 0.000001, 0)), Comparator.comparingDouble(v -> v.getPoint().getX()));
        if (leftIndex < 0) {
            leftIndex = -leftIndex - 1;
        }
        int rightIndex = Collections.binarySearch(victims, new Victim(new Point2D(maxX + 0.000001, 0)), Comparator.comparingDouble(v -> v.getPoint().getX()));
        if (rightIndex < 0) {
            rightIndex = -rightIndex - 1;
        }
        for (int i = leftIndex; i < rightIndex; i++) {
            Victim victim = victims.get(i);
            if (distance(point0, point1, victim.getPoint()) <= viewRangeRadius) {
                if (!victim.isFound()) {
                    victim.setFound();
                    numOfFoundVictims++;
                    foundPopulation++;
                }
            }
        }
        return numOfFoundVictims;
    }

    /**
     * 線分point0point1と点pointの距離を求めます
     *
     * @param point0 線分の始点
     * @param point1 線分の終点
     * @param point  点
     * @return 点と線分の距離
     */
    private double distance(Point2D point0, Point2D point1, Point2D point) {
        if (point1.subtract(point0).dotProduct(point.subtract(point0)) < 0) return point.subtract(point0).magnitude();
        if (point0.subtract(point1).dotProduct(point.subtract(point1)) < 0) return point.subtract(point1).magnitude();
        return point1.subtract(point0).crossProduct(point.subtract(point0)).magnitude() / point1.subtract(point0).magnitude();
    }

    public List<ViewableVictim> getViewableVictims() {
        List<ViewableVictim> res = new ArrayList<>();
        res.addAll(victims);
        return res;
    }
}
