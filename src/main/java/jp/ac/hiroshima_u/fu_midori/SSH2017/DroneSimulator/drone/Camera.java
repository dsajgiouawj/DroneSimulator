package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.Victim;

import java.util.List;

/**
 * ドローンのカメラ。
 * 被災者の発見判定を行います。
 * 視野は円形です
 *
 * @author 遠藤拓斗 on 2017/05/12.
 */
class Camera {
    private double viewRangeRadius;
    private List<Victim> victims;

    /**
     * コンストラクタ
     *
     * @param viewRangeRadius 視野の半径[m]
     * @param victims         被災者のリスト(ソートされている必要があります)
     */
    Camera(double viewRangeRadius, List<Victim> victims) {
        this.viewRangeRadius = viewRangeRadius;
        this.victims = victims;
    }

    /**
     * point0からpoint1まで動いた時、その線分から一定距離以内の被災者を発見します
     *
     * @param point0 始点
     * @param point1 終点
     */
    void findPeople(Point2D point0, Point2D point1) {
        for (Victim victim : victims) {
            if (distance(point0, point1, victim.getPoint()) <= viewRangeRadius) {
                victim.setFound();
            }
        }
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
}
