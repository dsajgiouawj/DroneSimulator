package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.VictimsPlacer.concentricCircle;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.Victims;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placer.VictimsPlacer;

import java.util.ArrayList;
import java.util.List;

/**
 * 被災者を同心円上に配置します。
 *
 * @author 遠藤拓斗 on 2017/08/27.
 */
public class Placer implements VictimsPlacer {
    private Point2D center;
    private double innerRadius;
    private double outerRadius;

    Placer(Point2D center, double innerRadius, double outerRadius) {
        this.center = center;
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
    }

    @Override
    public Victims placeVictims(int population) {
        List<Point2D> points = new ArrayList<>();
        for (int i = 0; i < population; ++i) {
            points.add(generatePoint());
        }
        return new Victims(points);
    }

    private Point2D generatePoint() {
        double ratio = innerRadius / outerRadius;
        double u = Math.random() * (1 - ratio * ratio) + ratio * ratio;//[ratio^2,1)
        double r = Math.sqrt(u) * outerRadius;//[ratio,1)*outerRadius->[inner,outer)
        double theta = Math.random() * 2 * Math.PI;//[0,2π)
        return new Point2D(center.getX() + r * Math.cos(theta), center.getY() + r * Math.sin(theta));
    }

    @Override
    public String toString() {
        return "同心円状に配置 中心:(" + center.getX() + "," + center.getY() + ")" + "内半径:" + innerRadius + "外半径:" + outerRadius;
    }
}
