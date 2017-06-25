package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.callNeighborsAndSpiralTactics;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.SelectCalleeMediator;

import static jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.callNeighborsAndSpiralTactics.DroneState.*;

/**
 * RandomAndCallNeighborsAndThenSpiralTactics用のドローン
 *
 * @author 遠藤拓斗 on 2017/06/07.
 */
public class DroneController {
    private final Drone drone;
    private final int numDrone;
    private final int id;
    private final double viewRangeRadius;
    private final int turnInterval;
    private final double limitOfTurningAngle;
    private final SelectCalleeMediator selectCalleeMediator;
    private DroneState state;
    private int time = 0;
    private Point2D target;
    private jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.spiralTactics.DroneController spiralTacticsDrone;
    private final double searchRatio2;
    private final int timeToContinueSpiral2SinceLastFind;

    private int nextId;
    private int nextNumDrone;

    public DroneController(Drone drone, int numDrone, int id, double viewRangeRadius, int turnInterval, double limitOfTurningAngle, SelectCalleeMediator selectCalleeMediator, boolean useSpiralAtFirst, double searchRatio, double searchRatio2, int timeToContinueSpiral2SinceLastFind) {
        this.drone = drone;
        this.numDrone = numDrone;
        this.id = id;
        this.viewRangeRadius = viewRangeRadius;
        this.turnInterval = turnInterval;
        this.limitOfTurningAngle = limitOfTurningAngle;
        this.selectCalleeMediator = selectCalleeMediator;
        this.state = randomWalking;
        this.timeToContinueSpiral2SinceLastFind = timeToContinueSpiral2SinceLastFind;
        if (useSpiralAtFirst) this.state = spiral;
        spiralTacticsDrone = new jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.spiralTactics.DroneController(drone, numDrone, id, viewRangeRadius, searchRatio);
        this.searchRatio2 = searchRatio2;
        drone.setTheta(2 * Math.PI / numDrone * id);//均等な方向に向く
    }

    public void executeTurn() {
        if (state == randomWalking) {
            randomWalk();
        } else if (state == beingCalled) {
            goTarget();
        } else if (state == spiral) {
            spiralTacticsDrone.executeTurn();
            if (drone.getNumOfFoundVictimsWhileThisTurn() > 0)
                selectCalleeMediator.inform(id, drone.getNumOfFoundVictimsWhileThisTurn());
        } else if (state == spiral2) {
            spiral2();
        } else {
            throw new RuntimeException("未実装");
        }
        time++;
    }

    private void randomWalk() {
        if (time % turnInterval == 0) {
            double deltaTheta = Math.random() * limitOfTurningAngle * 2 - limitOfTurningAngle;//[-limit,limit)
            drone.turnClockWise(deltaTheta);
        }
        drone.goStraight();
        if (drone.getNumOfFoundVictimsWhileLastMovement() > 0) {
            selectCalleeMediator.inform(id, drone.getNumOfFoundVictimsWhileLastMovement());
        }
    }

    private void goTarget() {
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

    private int spiral2elapsedTimeSinceLastFind;

    private void spiral2() {
        spiralTacticsDrone.executeTurn();
        if (drone.getNumOfFoundVictimsWhileThisTurn() == 0) {
            spiral2elapsedTimeSinceLastFind++;
        } else {
            spiral2elapsedTimeSinceLastFind = 0;
        }
        if (spiral2elapsedTimeSinceLastFind > timeToContinueSpiral2SinceLastFind) {
            spiralTacticsDrone = null;
            setState(randomWalking);
        }
    }

    public void setState(DroneState state) {
        if (state == randomWalking) drone.setColor(Color.WHITE);
        else if (state == beingCalled) drone.setColor(Color.RED);
        else if (state == spiral2) {
            drone.setColor(Color.YELLOW);
            spiralTacticsDrone = new jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.spiralTactics.DroneController(drone, nextNumDrone, nextId, viewRangeRadius, searchRatio2, drone.getPoint());
            spiral2elapsedTimeSinceLastFind = 0;
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
