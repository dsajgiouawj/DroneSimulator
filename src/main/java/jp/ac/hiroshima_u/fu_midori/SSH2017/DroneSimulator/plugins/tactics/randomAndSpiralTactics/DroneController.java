package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndSpiralTactics;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.ArchimedesSpiral.ArchimedeanSpiral;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling.SelectCalleeMediator;

import static jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndSpiralTactics.DroneState.*;

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
    private ArchimedeanSpiral spiralDrone;
    private final double searchRatio2;

    private int nextId;
    private int nextNumDrone;

    private int lastFindTime = 0;
    private final int timeToContinueSpiral;

    /**
     * @param drone
     * @param numDrone
     * @param id
     * @param viewRangeRadius
     * @param turnInterval
     * @param limitOfTurningAngle
     * @param selectCalleeMediator
     * @param useSpiralAtFirst     最初螺線探索をするか
     * @param searchRatio          最初の螺線探索の探索割合
     * @param searchRatio2         発見後の螺線探索の探索割合
     * @param timeToContinueSpiral 最後に被災者を発見してから螺線探索を続ける時間
     */
    public DroneController(Drone drone, int numDrone, int id, double viewRangeRadius, int turnInterval, double limitOfTurningAngle, SelectCalleeMediator selectCalleeMediator, boolean useSpiralAtFirst, double searchRatio, double searchRatio2, int timeToContinueSpiral) {
        this.drone = drone;
        this.numDrone = numDrone;
        this.id = id;
        this.viewRangeRadius = viewRangeRadius;
        this.turnInterval = turnInterval;
        this.limitOfTurningAngle = limitOfTurningAngle;
        this.selectCalleeMediator = selectCalleeMediator;
        this.state = randomWalking;
        if (useSpiralAtFirst) this.state = spiral;
        spiralDrone = new ArchimedeanSpiral(drone, numDrone, id, viewRangeRadius, searchRatio);
        this.searchRatio2 = searchRatio2;
        drone.setTheta(2 * Math.PI / numDrone * id);//均等な方向に向く
        this.timeToContinueSpiral = timeToContinueSpiral;
    }

    public void executeTurn() {
        if (state == randomWalking) {
            randomWalk();
        } else if (state == beingCalled) {
            goTarget();
        } else if (state == spiral) {
            spiralDrone.executeTurn();
            if (drone.getNumOfFoundVictimsWhileThisTurn() > 0) {
                selectCalleeMediator.inform(id, drone.getNumOfFoundVictimsWhileThisTurn());
            }
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

    private void spiral2() {
        spiralDrone.executeTurn();
        if (drone.getNumOfFoundVictimsWhileThisTurn() > 0) {
            lastFindTime = time;
        } else if (time >= lastFindTime + timeToContinueSpiral) {
            setState(randomWalking);
        }
    }

    public void setState(DroneState state) {
        if (state == randomWalking) drone.setColor(Color.WHITE);
        else if (state == beingCalled) drone.setColor(Color.RED);
        else if (state == spiral2) {
            drone.setColor(Color.YELLOW);
            spiralDrone = new ArchimedeanSpiral(drone, nextNumDrone, nextId, viewRangeRadius, searchRatio2);
            lastFindTime = time;//見つけたことにしておいて直後に螺線探索が終了するのを防ぐ
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
