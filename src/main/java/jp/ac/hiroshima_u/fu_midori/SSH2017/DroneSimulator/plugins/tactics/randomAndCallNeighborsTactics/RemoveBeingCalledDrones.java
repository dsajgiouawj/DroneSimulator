package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.CallingFilter;

import java.util.List;
import java.util.stream.IntStream;

import static jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.DroneState.beingCalled;

/**
 * ランダムウォーク中または螺線探索中のドローンのみ呼び出す
 *
 * @author 遠藤拓斗 on 2017/05/31.
 */
public class RemoveBeingCalledDrones implements CallingFilter {
    private List<DroneController> drones;

    RemoveBeingCalledDrones(List<DroneController> drones) {
        this.drones = drones;
    }

    public void before() {

    }

    public void after() {

    }

    public void informFinding(int id, int num) {

    }

    public void informCalling(int id) {

    }

    public void informBeingCalled(int id) {

    }

    public IntStream filter(int id, IntStream stream) {
        return stream.filter(i -> drones.get(i).getState() != beingCalled);
    }

    @Override
    public String toString() {
        return "呼び出されている途中のドローンを除外";
    }
}
