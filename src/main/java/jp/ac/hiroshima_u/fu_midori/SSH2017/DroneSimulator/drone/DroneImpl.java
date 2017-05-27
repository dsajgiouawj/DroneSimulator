package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.Victim;

import java.util.List;

/**
 * ドローンの実装です。
 *
 * @author 遠藤拓斗 on 2017/05/07.
 */
public class DroneImpl implements Drone, DroneGUIInterface {
    private static final double SPEED = 16;//16[m/s]
    private double residualDistance = 0;//このターンの残り距離
    private double theta = 0;//向いている方向[rad]
    private Point2D point = new Point2D(0, 0);//現在位置
    private Point2D point0;//前回位置
    private Camera camera;
    private double viewRangeRadius;

    public DroneImpl(double viewRangeRadius, List<Victim> victims) {
        this.viewRangeRadius = viewRangeRadius;
        this.camera = new Camera(viewRangeRadius, victims);
    }

    public void goStraight() {
        goStraight(residualDistance);
    }

    public void goStraight(double distance) {
        distance = Math.min(distance, residualDistance);
        residualDistance -= distance;
        point0 = point;
        point = point.add(Math.cos(theta) * distance, Math.sin(theta) * distance);
        findPeople();
    }

    public void goTheDirection(Point2D direction) {
        goTheDirection(direction, residualDistance);
    }

    public void goTheDirection(Point2D direction, double distance) {
        distance = Math.min(distance, residualDistance);
        direction = direction.normalize();
        if (direction.equals(Point2D.ZERO)) return;
        point0 = point;
        point = point.add(direction.multiply(distance));
        residualDistance -= distance;
        findPeople();
    }

    public void goToPoint(Point2D target) {
        double distance = Math.min(point.distance(target), residualDistance);
        goTheDirection(target.subtract(point), distance);
    }

    public void turnClockWise(double deltaTheta) {
        theta += deltaTheta;
    }

    public void turnCounterClockWise(double deltaTheta) {
        theta -= deltaTheta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public double getTheta() {
        if (theta < 0) {
            theta = 2 * Math.PI + theta % (2 * Math.PI);
        }
        return theta % (2 * Math.PI);
    }

    public Point2D getPoint() {
        return point;
    }

    public double getResidualDistance() {
        return residualDistance;
    }

    public boolean canMove() {
        return residualDistance > 0;
    }


    public int numOfFoundPeopleWhileLastMove() {
        return 0;
    }

    private Color color = Color.WHITE;

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public Point2D getPoint0() {
        return point0;
    }

    private void findPeople() {
        camera.findPeople(point0, point);
    }

    public void nextTurn() {
        residualDistance = SPEED;
    }

    @Override
    public double getX() {
        return point.getX();
    }

    @Override
    public double getY() {
        return point.getY();
    }

    @Override
    public double getViewRangeRadius() {
        return viewRangeRadius;
    }
}
