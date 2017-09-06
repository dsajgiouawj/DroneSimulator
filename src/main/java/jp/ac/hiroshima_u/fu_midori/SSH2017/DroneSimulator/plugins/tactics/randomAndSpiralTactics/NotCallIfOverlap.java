package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndSpiralTactics;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.ArchimedesSpiral.ArchimedesSpiral;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling.CallingFilter;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 重なるなら探索しない
 *
 * @author 遠藤拓斗 on 2017/08/10.
 */
public class NotCallIfOverlap implements CallingFilter {
    private List<Drone> drones;
    private final int limitTime;
    private final int timeToContinueSpiral;
    private List<Pair<Point2D, Integer>> callingPoints = new ArrayList<>();//(point,time)
    private int time = 0;
    private final List<Double> distanceList;

    NotCallIfOverlap(List<Drone> drones, int limitTime, int timeToContinueSpiral, int numOfDronesToCall) {
        this.drones = drones;
        this.limitTime = limitTime;
        double viewRangeRadius = drones.get(0).viewRangeRadius();
        this.timeToContinueSpiral = timeToContinueSpiral;
        distanceList = ArchimedesSpiral.simulatePassingPoints(numOfDronesToCall, 0, viewRangeRadius, 1, Point2D.ZERO, timeToContinueSpiral).stream().map(Point2D.ZERO::distance).collect(Collectors.toList());
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
            double d1 = distanceList.get(Math.min(limitTime - point.second, timeToContinueSpiral));
            double d2 = distanceList.get(Math.min(limitTime - time, timeToContinueSpiral));
            if (currentCallingPoint.distance(point.first) < d1 + d2) return stream.filter(i -> false);
        }
        return stream;
    }
}


