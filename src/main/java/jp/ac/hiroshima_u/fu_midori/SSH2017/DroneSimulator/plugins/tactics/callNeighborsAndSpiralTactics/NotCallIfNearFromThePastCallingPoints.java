package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.callNeighborsAndSpiralTactics;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling.CallingFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 以前呼び出したことがある地点から近いところからは呼び出さない
 *
 * @author 遠藤拓斗 on 2017/06/08.
 */
class NotCallIfNearFromThePastCallingPoints implements CallingFilter {
    private List<Drone> drones;
    private List<Point2D> callingPoints = new ArrayList<>();
    private final double thresholdDistance;

    /**
     * コンストラクタ
     *
     * @param drones            ドローン
     * @param thresholdDistance この距離以上なら呼び出す
     */
    NotCallIfNearFromThePastCallingPoints(List<Drone> drones, double thresholdDistance) {
        this.drones = drones;
        this.thresholdDistance = thresholdDistance;
    }

    @Override
    public void before() {

    }

    @Override
    public void after() {

    }

    @Override
    public void informFinding(int id, int num) {

    }

    @Override
    public void informCalling(int id) {
        callingPoints.add(drones.get(id).getPoint());
    }

    @Override
    public void informBeingCalled(int id) {

    }

    @Override
    public IntStream filter(int id, IntStream stream) {
        Point2D currentCallingPoint = drones.get(id).getPoint();
        for (Point2D point : callingPoints) {
            if (currentCallingPoint.distance(point) < thresholdDistance) return stream.filter(i -> false);
        }
        return stream;
    }

    @Override
    public String toString() {
        return "以前呼び出したことがある地点から" + thresholdDistance + "m以上離れていないと呼び出しを行わない";
    }
}
