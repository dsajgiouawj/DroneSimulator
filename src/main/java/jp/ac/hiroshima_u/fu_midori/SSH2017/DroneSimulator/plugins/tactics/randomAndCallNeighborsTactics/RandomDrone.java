package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.CallingTactics;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.spiralTactics.SpiralTacticsDrone;

import static jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.DroneState.*;

/**
 * RandomAndCallNeighborsTacticsに使用するドローン
 *
 * @author 遠藤拓斗 on 2017/05/28.
 */
public class RandomDrone {
    private Drone drone;
    private int numDrone;
    private int id;
    private int turnInterval;
    private double limitOfTurningAngle;
    private CallingTactics caller;
    private DroneState state;
    private int time = 0;
    private Point2D target;
    private SpiralTacticsDrone spiralTacticsDrone;

    public RandomDrone(Drone drone, int numDrone, int id, double viewRangeRadius, int turnInterval, double limitOfTurningAngle, CallingTactics caller, boolean useSpiralAtFirst, double searchRatio) {
        this.drone = drone;
        this.numDrone = numDrone;
        this.id = id;
        this.turnInterval = turnInterval;
        this.limitOfTurningAngle = limitOfTurningAngle;
        this.caller = caller;
        this.state = randomWalking;
        if (useSpiralAtFirst) this.state = spiral;
        spiralTacticsDrone = new SpiralTacticsDrone(drone, numDrone, id, viewRangeRadius, searchRatio);
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
                caller.inform(id, drone.getNumOfFoundVictimsWhileLastMovement());
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
            caller.inform(id, drone.getNumOfFoundVictimsWhileLastMovement());
        }
    }

    protected void goTarget() {
        drone.goToPoint(target);
        if (drone.getNumOfFoundVictimsWhileLastMovement() > 0) {
            caller.inform(id, drone.getNumOfFoundVictimsWhileLastMovement());
        }
        if (drone.canMove()) {
            target = null;
            setState(randomWalking);
            randomWalk();
        }
    }

    public void setState(DroneState state) {
        if (state == randomWalking) drone.setColor(Color.WHITE);
        else if (state == beingCalled) drone.setColor(Color.RED);
        this.state = state;
    }

    public DroneState getState() {
        return state;
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
