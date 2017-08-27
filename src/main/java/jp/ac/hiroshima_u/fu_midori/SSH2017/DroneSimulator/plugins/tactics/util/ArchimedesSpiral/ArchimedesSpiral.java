package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.ArchimedesSpiral;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;

/**
 * アルキメデスの螺線状に動く
 * @author 遠藤拓斗 on 2017/08/25.
 */
public class ArchimedesSpiral {
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
    public ArchimedesSpiral(Drone drone, int numDrone, int id, double viewRangeRadius, double searchRatio, Point2D center) {
        this.drone = drone;
        this.numDrone = numDrone;
        this.id = id;
        targetTheta = 2 * Math.PI / numDrone * id;
        a = 2 * numDrone * viewRangeRadius / (2 * Math.PI) * (1 / searchRatio);
        this.center = center;
        setNextTarget();
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
