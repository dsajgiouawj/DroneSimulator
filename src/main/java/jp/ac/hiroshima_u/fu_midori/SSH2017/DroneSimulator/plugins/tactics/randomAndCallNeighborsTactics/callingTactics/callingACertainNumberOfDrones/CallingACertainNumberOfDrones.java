package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.callingACertainNumberOfDrones;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.RandomDrone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.CallingSubTactics;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.CallingTactics;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.DistanceCompare;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.DroneState.beingCalled;
import static jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.DroneState.randomWalking;

/**
 * ドローンが被災者を発見したとき近くにいる一定台数のドローンを呼び寄せます。
 * 呼び寄せられたドローンが途中で被災者を発見した場合ランダムウォークに戻します
 *
 * @author 遠藤拓斗 on 2017/05/30.
 */
public class CallingACertainNumberOfDrones implements CallingTactics {
    private List<RandomDrone> drones;
    private int certainNumber;
    private List<CallingSubTactics> conditionChain = new ArrayList<>();

    public CallingACertainNumberOfDrones(int certainNumber, CallingSubTactics... conditions) {
        this.certainNumber = certainNumber;
        conditionChain.addAll(Arrays.asList(conditions));
    }

    @Override
    public void setDrones(List<RandomDrone> drones) {
        this.drones = drones;
        for (CallingSubTactics condition : conditionChain) {
            condition.setDrones(drones);
        }
    }

    @Override
    public void before() {
        for (CallingSubTactics condition : conditionChain) {
            condition.before();
        }
    }

    @Override
    public void after() {
        for (CallingSubTactics condition : conditionChain) {
            condition.after();
        }
    }

    @Override
    public void inform(int id, int num) {
        if (drones == null) throw new NullPointerException();
        for (CallingSubTactics condition : conditionChain) {
            condition.inform(id, num);
        }
        Stream<RandomDrone> stream = drones.stream();
        for (CallingSubTactics condition : conditionChain) {
            stream = condition.filter(id, stream);
        }
        call(id, stream.collect(Collectors.toList()));
        drones.get(id).setState(randomWalking);
    }

    private void call(int id, List<RandomDrone> qualifiedDrones) {
        if (drones == null) throw new NullPointerException();
        PriorityQueue<DistanceCompare> que = new PriorityQueue<>(Collections.reverseOrder());
        Point2D target = drones.get(id).getPoint();
        for (RandomDrone drone : qualifiedDrones) {
            if (drone.getId() == id) continue;
            double dist = drone.getPoint().distance(target);
            que.add(new DistanceCompare(dist, drone));
            while (que.size() > certainNumber) {
                que.poll();
            }
        }
        if (que.size() != 0) {
            for (CallingSubTactics condition : conditionChain) {
                condition.call(id);
            }
        }
        while (!que.isEmpty()) {
            RandomDrone d = que.poll().drone;
            d.setState(beingCalled);
            d.setTarget(target);
            Point2D target2 = target.subtract(d.getPoint());
            double theta = Math.atan2(target2.getY(), target2.getX());
            d.setTheta(theta);
            for (CallingSubTactics condition : conditionChain) {
                condition.beCalled(d.getId());
            }
        }
    }
}
