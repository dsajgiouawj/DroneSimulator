package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

/**
 * ドローンの実装です。
 *
 * @author 遠藤拓斗 on 2017/05/07.
 */
public class DroneImpl implements Drone {
    private static final double SPEED = 16;//16[m/s]
    private double residualDistance = 0;//このターンの残り距離
    private double theta = 0;//向いている方向[rad]
    private Point2D point = new Point2D(0, 0);//現在位置
    private Point2D point0;//前回位置

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

    private Color color;

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public Point2D getPoint0() {
        return point0;
    }

    private void findPeople() {

    }

    public void nextTurn() {
        residualDistance = SPEED;
    }
}
