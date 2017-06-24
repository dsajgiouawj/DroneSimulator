package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.Victims;

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
    private Victims victims;
    private double viewRangeRadius;
    private int numOfFoundVictimsWhileLastMovement = 0;
    private int numOfFoundVictimsWhileThisTurn = 0;

    public DroneImpl(double viewRangeRadius, Victims victims) {
        this.viewRangeRadius = viewRangeRadius;
        this.victims = victims;
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

    public int getNumOfFoundVictimsWhileLastMovement() {
        return numOfFoundVictimsWhileLastMovement;
    }

    public int getNumOfFoundVictimsWhileThisTurn() {
        return numOfFoundVictimsWhileThisTurn;
    }

    private Color color = Color.WHITE;

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public double speed() {
        return SPEED;
    }

    public Color getColor() {
        return this.color;
    }

    public Point2D getPoint0() {
        return point0;
    }

    private void findPeople() {
        numOfFoundVictimsWhileLastMovement = 0;
        numOfFoundVictimsWhileLastMovement = victims.findPeople(point0, point, viewRangeRadius);
        numOfFoundVictimsWhileThisTurn += numOfFoundVictimsWhileLastMovement;
    }

    public void nextTurn() {
        residualDistance = SPEED;
        numOfFoundVictimsWhileThisTurn = 0;
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
