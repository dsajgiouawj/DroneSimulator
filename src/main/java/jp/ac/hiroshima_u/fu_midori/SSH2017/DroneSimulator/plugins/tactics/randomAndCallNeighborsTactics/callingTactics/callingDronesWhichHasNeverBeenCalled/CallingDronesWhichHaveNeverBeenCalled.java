package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.callingDronesWhichHasNeverBeenCalled;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.RandomDrone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.CallingSubTactics;

import java.util.List;
import java.util.stream.Stream;

/**
 * 一度も呼び出されていないドローンを呼び出します。
 *
 * @author 遠藤拓斗 on 2017/05/31.
 */
public class CallingDronesWhichHaveNeverBeenCalled implements CallingSubTactics {
    private boolean[] hasEverBeenCalled = new boolean[0];//avoid findbugs warning

    @Override
    public void setDrones(List<RandomDrone> drones) {
        hasEverBeenCalled = new boolean[drones.size()];
    }

    @Override
    public void before() {

    }

    @Override
    public void after() {

    }

    @Override
    public void inform(int id, int num) {

    }

    @Override
    public void call(int id) {

    }

    @Override
    public void beCalled(int id) {
        if (hasEverBeenCalled == null) throw new NullPointerException();
        hasEverBeenCalled[id] = true;
    }

    @Override
    public Stream<RandomDrone> filter(int id, Stream<RandomDrone> stream) {
        if (hasEverBeenCalled == null) throw new NullPointerException();
        return stream.filter(d -> !hasEverBeenCalled[d.getId()]);
    }
}
