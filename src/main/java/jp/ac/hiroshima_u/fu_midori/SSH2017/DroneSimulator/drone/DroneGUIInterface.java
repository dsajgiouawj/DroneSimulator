package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone;

import javafx.scene.paint.Color;

/**
 * GUI時のドローン表示用
 *
 * @author 遠藤拓斗 on 2017/05/27.
 */
public interface DroneGUIInterface {
    /**
     * ドローンのx座標を取得
     * @return ドローンのx座標
     */
    double getX();

    /**
     * ドローンのy座標を取得
     * @return ドローンのy座標
     */
    double getY();

    /**
     * ドローンの視野の半径を取得
     * @return ドローンの視野の半径
     */
    double getViewRangeRadius();

    /**
     * 表示色を取得
     * @return ドローンの表示色
     */
    Color getColor();
}
