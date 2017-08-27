package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.callNeighborsAndSpiralTactics;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling.CallingFilter;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 重なるなら探索しない
 *
 * @author 遠藤拓斗 on 2017/08/10.
 */
public class NotCallIfOverlap implements CallingFilter {
    private List<Drone> drones;
    private final int limitTime;
    private List<Pair<Point2D, Integer>> callingPoints = new ArrayList<>();//(point,time)
    private int time = 0;
    private final double areaPerSecond;//[m^2/s]

    NotCallIfOverlap(List<Drone> drones, int limitTime, int numOfDronesToCall) {
        this.drones = drones;
        this.limitTime = limitTime;
        double v = drones.get(0).viewRangeRadius();
        double s = drones.get(0).speed();
        areaPerSecond = 2 * v * s * numOfDronesToCall;
    }

    @Override
    public void before() {

    }

    @Override
    public void after() {
        time++;
    }

    @Override
    public void informFinding(int id, int num) {

    }

    @Override
    public void informCalling(int id) {
        callingPoints.add(new Pair<>(drones.get(id).getPoint(), time));
    }

    @Override
    public void informBeingCalled(int id) {

    }

    @Override
    public IntStream filter(int id, IntStream stream) {
        Point2D currentCallingPoint = drones.get(id).getPoint();
        for (Pair<Point2D, Integer> point : callingPoints) {
            double d1 = Math.sqrt((limitTime - point.second) * areaPerSecond / Math.PI);
            double d2 = Math.sqrt((limitTime - time) * areaPerSecond / Math.PI);
            if (currentCallingPoint.distance(point.first) < d1 + d2) return stream.filter(i -> false);
        }
        return stream;
    }
}


