package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.CallNeighborsAndSpiralTactics;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.SelectCalleeMediator;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.spiralTactics.SpiralTacticsDrone;

import static jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.CallNeighborsAndSpiralTactics.DroneState.*;

/**
 * RandomAndCallNeighborsAndThenSpiralTactics用のドローン
 *
 * @author 遠藤拓斗 on 2017/06/07.
 */
public class DroneController {
    private Drone drone;
    private int numDrone;
    private int id;
    private double viewRangeRadius;
    private int turnInterval;
    private double limitOfTurningAngle;
    private SelectCalleeMediator selectCalleeMediator;
    private DroneState state;
    private int time = 0;
    private Point2D target;
    private SpiralTacticsDrone spiralTacticsDrone;
    private double searchRatio2;

    private int nextId;
    private int nextNumDrone;

    public DroneController(Drone drone, int numDrone, int id, double viewRangeRadius, int turnInterval, double limitOfTurningAngle, SelectCalleeMediator selectCalleeMediator, boolean useSpiralAtFirst, double searchRatio, double searchRatio2) {
        this.drone = drone;
        this.numDrone = numDrone;
        this.id = id;
        this.viewRangeRadius = viewRangeRadius;
        this.turnInterval = turnInterval;
        this.limitOfTurningAngle = limitOfTurningAngle;
        this.selectCalleeMediator = selectCalleeMediator;
        this.state = randomWalking;
        if (useSpiralAtFirst) this.state = spiral;
        spiralTacticsDrone = new SpiralTacticsDrone(drone, numDrone, id, viewRangeRadius, searchRatio);
        this.searchRatio2 = searchRatio2;
        drone.setTheta(2 * Math.PI / numDrone * id);//均等な方向に向く
    }

    protected void executeTurn() {
        if (state == randomWalking) {
            randomWalk();
        } else if (state == beingCalled) {
            goTarget();
        } else if (state == spiral) {
            spiralTacticsDrone.executeTurn();
            if (drone.getNumOfFoundVictimsWhileLastMovement() > 0)
                selectCalleeMediator.inform(id, drone.getNumOfFoundVictimsWhileLastMovement());
        } else if (state == spiral2) {
            spiral2();
        } else {
            throw new RuntimeException("未実装");
        }
        time++;
    }

    protected void randomWalk() {
        if (time % turnInterval == 0) {
            double deltaTheta = Math.random() * limitOfTurningAngle * 2 - limitOfTurningAngle;//[-limit,limit)
            drone.turnClockWise(deltaTheta);
        }
        drone.goStraight();
        if (drone.getNumOfFoundVictimsWhileLastMovement() > 0) {
            selectCalleeMediator.inform(id, drone.getNumOfFoundVictimsWhileLastMovement());
        }
    }

    protected void goTarget() {
        drone.goToPoint(target);
        if (drone.getNumOfFoundVictimsWhileLastMovement() > 0) {
            selectCalleeMediator.inform(id, drone.getNumOfFoundVictimsWhileLastMovement());
        }
        if (drone.canMove()) {
            target = null;
            setState(spiral2);
            spiral2();
        }
    }

    protected void spiral2() {
        spiralTacticsDrone.executeTurn();
    }

    public void setState(DroneState state) {
        if (state == randomWalking) drone.setColor(Color.WHITE);
        else if (state == beingCalled) drone.setColor(Color.RED);
        else if (state == spiral2) {
            drone.setColor(Color.YELLOW);
            spiralTacticsDrone = new SpiralTacticsDrone(drone, nextNumDrone, nextId, viewRangeRadius, searchRatio2, drone.getPoint());
        }
        this.state = state;
    }

    public DroneState getState() {
        return state;
    }

    /**
     * 次の螺旋探索の設定
     * setStateより先に呼び出す
     *
     * @param id       螺旋探索に使用するid
     * @param numDrone 螺旋探索に集まるドローン数
     */
    public void setNextSettings(int id, int numDrone) {
        this.nextId = id;
        this.nextNumDrone = numDrone;
    }

    public Point2D getPoint() {
        return drone.getPoint();
    }

    public void setTarget(Point2D target) {
        this.target = target;
    }

    public Point2D getTarget() {
        return this.target;
    }

    public int getNumDrone() {
        return numDrone;
    }

    public int getId() {
        return id;
    }

    public void setTheta(double theta) {
        drone.setTheta(theta);
    }
}
