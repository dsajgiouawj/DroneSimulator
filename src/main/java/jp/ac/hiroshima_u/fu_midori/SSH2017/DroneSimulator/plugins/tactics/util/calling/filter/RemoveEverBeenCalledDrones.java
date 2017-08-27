package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling.filter;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling.CallingFilter;

import java.util.List;
import java.util.stream.IntStream;

/**
 * 呼び出されたことのあるドローンを除外します
 *
 * @author 遠藤拓斗 on 2017/05/31.
 */
public class RemoveEverBeenCalledDrones implements CallingFilter {
    private boolean[] hasEverBeenCalled;

    public RemoveEverBeenCalledDrones(List<Drone> drones) {
        this.hasEverBeenCalled = new boolean[drones.size()];
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

    }

    @Override
    public void informBeingCalled(int id) {
        hasEverBeenCalled[id] = true;
    }

    @Override
    public IntStream filter(int id, IntStream stream) {
        return stream.filter(i -> !hasEverBeenCalled[i]);
    }

    @Override
    public String toString() {
        return "呼び出されたことのあるドローンを除外";
    }
}
