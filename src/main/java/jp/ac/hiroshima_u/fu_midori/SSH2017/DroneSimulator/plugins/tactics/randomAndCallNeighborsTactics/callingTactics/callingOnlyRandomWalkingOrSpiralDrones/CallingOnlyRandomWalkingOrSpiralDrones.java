package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.callingOnlyRandomWalkingOrSpiralDrones;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.RandomDrone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.CallingSubTactics;

import java.util.List;
import java.util.stream.Stream;

import static jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.DroneState.beingCalled;

/**
 * ランダムウォーク中のドローンのみ呼び出す
 *
 * @author 遠藤拓斗 on 2017/05/31.
 */
public class CallingOnlyRandomWalkingOrSpiralDrones implements CallingSubTactics {
    @Override
    public void setDrones(List<RandomDrone> drones) {

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

    }

    @Override
    public Stream<RandomDrone> filter(int id, Stream<RandomDrone> stream) {
        return stream.filter(d -> d.getState() != beingCalled);
    }
}
