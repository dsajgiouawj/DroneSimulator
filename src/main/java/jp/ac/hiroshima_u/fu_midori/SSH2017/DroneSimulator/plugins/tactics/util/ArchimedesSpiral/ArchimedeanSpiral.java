package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.ArchimedesSpiral;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.DroneImpl;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.Victims;

import java.util.ArrayList;
import java.util.List;

/**
 * 現在地を中心としてアルキメデスの螺線状に動く
 *
 * @author 遠藤拓斗 on 2017/08/25.
 */
public class ArchimedeanSpiral {
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
     */
    public ArchimedeanSpiral(Drone drone, int numDrone, int id, double viewRangeRadius, double searchRatio) {
        this.drone = drone;
        this.numDrone = numDrone;
        this.id = id;
        targetTheta = 2 * Math.PI / numDrone * id;
        a = 2 * numDrone * viewRangeRadius / (2 * Math.PI) * (1 / searchRatio);
        this.center = drone.getPoint();
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

    /**
     * 螺線探索を実行するとどこに行くのか調べる
     *
     * @param time 何秒後の位置を知りたいか
     * @return (time)秒後の位置
     */
    public static Point2D simulate(int numDrone, int id, double viewRangeRadius, double searchRatio, Point2D center, int time) {
        DroneImpl drone = new DroneImpl(viewRangeRadius, new Victims(new ArrayList<>()), center);
        ArchimedeanSpiral simulator = new ArchimedeanSpiral(drone, numDrone, id, viewRangeRadius, searchRatio);
        for (int i = 0; i < time; i++) {
            drone.nextTurn();
            simulator.executeTurn();
        }
        return drone.getPoint();
    }

    /**
     * 螺線探索を行った時の毎秒の地点を調べる
     *
     * @param time 何秒分調べるか
     * @return 通過地点のリスト(0番目の要素は中心点)
     */
    public static List<Point2D> simulatePassingPoints(int numDrone, int id, double viewRangeRadius, double searchRatio, Point2D center, int time) {
        List<Point2D> res = new ArrayList<>();
        res.add(center);
        DroneImpl drone = new DroneImpl(viewRangeRadius, new Victims(new ArrayList<>()), center);
        ArchimedeanSpiral simulator = new ArchimedeanSpiral(drone, numDrone, id, viewRangeRadius, searchRatio);
        for (int i = 0; i < time; i++) {
            drone.nextTurn();
            simulator.executeTurn();
            res.add(drone.getPoint());
        }
        return res;
    }
}
