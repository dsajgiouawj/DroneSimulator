package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim;

import javafx.geometry.Point2D;

/**
 * GUI時の被災者表示用
 *
 * @author 遠藤拓斗 on 2017/05/27.
 */
public interface ViewableVictim {
    /**
     * 被災者のx座標取得
     *
     * @return x座標
     */
    double getX();

    /**
     * 被災者のy座標取得
     *
     * @return y座標
     */
    double getY();

    /**
     * 被災者の座標取得
     * @return 座標
     */
    Point2D getPoint();

    /**
     * 発見されているかどうか
     *
     * @return 発見されていればtrue
     */
    boolean isFound();
}
