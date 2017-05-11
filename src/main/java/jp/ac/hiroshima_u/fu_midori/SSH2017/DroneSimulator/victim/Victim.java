package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.Displayable;


/**
 * 被災者を表すクラスです。
 *
 * @author 遠藤拓斗 on 2017/05/11.
 */
public class Victim implements Displayable {
    private Point2D point;

    Victim(Point2D point) {
        this.point = point;
    }

    public Point2D getPoint() {
        return point;
    }

    public Color getColor() {
        return Color.BLUE;
    }

    public double getRadius() {
        return 2;
    }
}
