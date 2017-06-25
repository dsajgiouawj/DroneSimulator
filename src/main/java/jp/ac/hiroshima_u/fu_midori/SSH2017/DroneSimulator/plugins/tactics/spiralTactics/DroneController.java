package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.spiralTactics;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;

/**
 * SpiralTacticsに使用するドローン
 *
 * @author 遠藤拓斗 on 2017/05/15.
 */
public class DroneController {
    private Drone drone;
    private int numDrone;
    private int id;

    private double targetR;
    private double targetTheta;
    private Point2D target;
    private static final double dTheta = 2 * Math.PI / 1000;//1000分割
    private double a;//r=aθ
    private final Point2D center;

    /**
     * コンストラクタ
     *
     * @param drone           操作するドローン
     * @param numDrone        ドローンの数
     * @param id              ドローンのid
     * @param viewRangeRadius ドローンの視野の半径
     * @param searchRatio     探索割合
     * @param center          中心
     */
    public DroneController(Drone drone, int numDrone, int id, double viewRangeRadius, double searchRatio, Point2D center) {
        this.drone = drone;
        this.numDrone = numDrone;
        this.id = id;
        targetTheta = 2 * Math.PI / numDrone * id;
        a = 2 * numDrone * viewRangeRadius / (2 * Math.PI) * (1 / searchRatio);
        this.center = center;
        setNextTarget();
    }

    public DroneController(Drone drone, int numDrone, int id, double viewRangeRadius, double searchRatio) {
        this(drone, numDrone, id, viewRangeRadius, searchRatio, Point2D.ZERO);
    }


    private void setNextTarget() {
        targetTheta += dTheta;
        targetR = a * (targetTheta - (2 * Math.PI / numDrone * id));
        target = new Point2D(Math.cos(targetTheta) * targetR, Math.sin(targetTheta) * targetR);
        target = target.add(center);
    }

    public void executeTurn() {
        drone.goToPoint(target);
        if (drone.canMove()) {
            setNextTarget();
            executeTurn();
        }
    }
}