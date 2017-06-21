package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.placingVictims.circle;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.Victims;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placing.PlacingVictims;

import java.util.ArrayList;
import java.util.List;

/**
 * 被災者を円形に配置します。
 *
 * @author 遠藤拓斗 on 2017/05/17.
 */
public class PlacingCircle implements PlacingVictims {
    private Point2D center;
    private double radius;

    PlacingCircle(Point2D center, double radius) {
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
        double x = Math.random() * radius * 2 - radius;
        double y = Math.random() * radius * 2 - radius;
        if (x * x + y * y <= radius * radius) return new Point2D(x + center.getX(), y + center.getY());
        else return generatePoint();
    }
}
