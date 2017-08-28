package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.VictimsPlacer.circle;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.Victims;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placer.VictimsPlacer;

import java.util.ArrayList;
import java.util.List;

/**
 * 被災者を円形に配置します。
 *
 * @author 遠藤拓斗 on 2017/05/17.
 */
public class Placer implements VictimsPlacer {
    private Point2D center;
    private double radius;

    Placer(Point2D center, double radius) {
        this.center = center;
        this.radius = radius;
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
        double r = Math.sqrt(Math.random()) * radius;
        double theta = Math.random() * 2 * Math.PI;
        return new Point2D(center.getX() + r * Math.cos(theta), center.getY() + r * Math.sin(theta));
    }

    @Override
    public String toString() {
        return "円形に配置 中心:(" + center.getX() + "," + center.getY() + ")" + "半径:" + radius;
    }
}
