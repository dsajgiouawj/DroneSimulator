package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim;

import javafx.geometry.Point2D;


/**
 * 被災者を表すクラスです。
 *
 * @author 遠藤拓斗 on 2017/05/11.
 */
class Victim implements ViewableVictim {
    private Point2D point;
    /**
     * 既に発見されているか
     */
    private boolean found = false;

    Victim(Point2D point) {
        this.point = point;
    }

    public Point2D getPoint() {
        return point;
    }

    /**
     * 被災者を発見したことにする。
     */
    void setFound() {
        found = true;
    }

    /**
     * 既に発見されているかどうか。
     *
     * @return 発見されているならtrue 発見されていなければfalse
     */
    public boolean isFound() {
        return found;
    }

    @Override
    public double getX() {
        return point.getX();
    }

    @Override
    public double getY() {
        return point.getY();
    }
}
