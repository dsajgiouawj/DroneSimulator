package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomWalk;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling.SelectCalleeMediator;

import static jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomWalk.DroneState.beingCalled;
import static jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomWalk.DroneState.randomWalking;

/**
 * @author 遠藤拓斗 on 2017/8/27.
 */
public class DroneController {
    private final Drone drone;
    //private final int numDrone;
    private final int id;
    //private final double viewRangeRadius;
    private final int turnInterval;
    private final double limitOfTurningAngle;
    private final SelectCalleeMediator selectCalleeMediator;
    private DroneState state;
    private int time = 0;
    private Point2D target;

    /**
     * コンストラクタ
     *
     * @param drone                操作するドローン
     * @param numDrone             ドローンの台数
     * @param id                   ドローンのインデックス
     * @param viewRangeRadius      カメラの視野半径
     * @param turnInterval         旋回間隔[s]
     * @param limitOfTurningAngle  旋回制限角度[±rad]
     * @param selectCalleeMediator 呼べ寄せの仲介
     */
    public DroneController(Drone drone, int numDrone, int id, double viewRangeRadius, int turnInterval, double limitOfTurningAngle, SelectCalleeMediator selectCalleeMediator) {
        this.drone = drone;
        //this.numDrone = numDrone;
        this.id = id;
        //this.viewRangeRadius = viewRangeRadius;
        this.turnInterval = turnInterval;
        this.limitOfTurningAngle = limitOfTurningAngle;
        this.selectCalleeMediator = selectCalleeMediator;
        setState(randomWalking);
        drone.setTheta(2 * Math.PI / numDrone * id);//均等な方向を向く
    }

    public void executeTurn() {
        time++;
        if (state == randomWalking) {
            randomWalk();
        } else if (state == beingCalled) {
            goTarget();
        } else {
            throw new RuntimeException("未実装");
        }
    }

    private void randomWalk() {
        if (time % turnInterval == 0) {
            drone.turnClockWise(Math.random() * limitOfTurningAngle * 2 - limitOfTurningAngle);//旋回
        }
        drone.goStraight();
        if (drone.getNumOfFoundVictimsWhileLastMovement() > 0) {
            selectCalleeMediator.inform(id, drone.getNumOfFoundVictimsWhileLastMovement());
        }
    }

    private void goTarget() {
        drone.goToPoint(target);
        if (drone.canMove()) {
            setState(randomWalking);
            randomWalk();
        }
    }

    private void setState(DroneState state) {
        this.state = state;
        if (state == randomWalking) drone.setColor(Color.WHITE);
        else drone.setColor(Color.RED);
    }

    public void called(Point2D target) {
        setState(beingCalled);
        this.target = target;
        drone.setTheta(Math.atan2(target.getY() - drone.getPoint().getY(), target.getX() - drone.getPoint().getX()));//向かう方向を向く
    }

    public Point2D getPoint() {
        return drone.getPoint();
    }
}
