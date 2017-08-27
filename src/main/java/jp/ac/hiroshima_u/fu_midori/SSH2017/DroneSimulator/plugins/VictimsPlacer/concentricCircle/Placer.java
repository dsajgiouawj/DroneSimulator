package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.VictimsPlacer.concentricCircle;

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
        double x = Math.random() * outerRadius * 2 - outerRadius;
        double y = Math.random() * outerRadius * 2 - outerRadius;
        if (x * x + y * y <= outerRadius * outerRadius) {
            if (x * x + y * y >= innerRadius * innerRadius) {
                return new Point2D(x + center.getX(), y + center.getY());
            } else
                return generatePoint();
        } else
            return generatePoint();
    }

    @Override
    public String toString() {
        return "同心円状に配置 中心:(" + center.getX() + "," + center.getY() + ")" + "内半径:" + innerRadius + "外半径:" + outerRadius;
    }
}
