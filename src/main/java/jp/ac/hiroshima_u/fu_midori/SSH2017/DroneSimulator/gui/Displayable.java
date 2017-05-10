package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui;

import javafx.scene.paint.Color;

import java.awt.geom.Point2D;

/**
 * ドローンや人など点(丸)で表示するもの
 * @author 遠藤拓斗 on 2017/05/07.
 */
public interface Displayable {
    /**
     * 表示する座標を取得します。
     * @return 座標
     */
    Point2D getPoint();

    /**
     * 表示する色を取得します。
     * @return 表示する色。
     */
    Color getColor();

    /**
     * 表示する円の半径を取得します。
     * @return 円の半径
     */
    double getRadius();
}
