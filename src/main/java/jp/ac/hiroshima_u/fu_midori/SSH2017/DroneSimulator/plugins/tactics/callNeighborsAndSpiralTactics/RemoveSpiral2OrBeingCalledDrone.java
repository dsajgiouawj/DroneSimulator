package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.callNeighborsAndSpiralTactics;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.CallingFilter;

import java.util.List;
import java.util.stream.IntStream;

import static jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.callNeighborsAndSpiralTactics.DroneState.beingCalled;
import static jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.callNeighborsAndSpiralTactics.DroneState.spiral2;

/**
 * 呼び出されて螺旋探索してるドローンは呼び出さない
 *
 * @author 遠藤拓斗 on 2017/06/08.
 */
class RemoveSpiral2OrBeingCalledDrone implements CallingFilter {
    private List<DroneController> drones;

    RemoveSpiral2OrBeingCalledDrone(List<DroneController> drones) {
        this.drones = drones;
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

    }

    @Override
    public IntStream filter(int id, IntStream stream) {
        return stream.filter(i -> drones.get(i).getState() != spiral2 && drones.get(i).getState() != beingCalled);
    }

    @Override
    public String toString(){
        return "呼び出されて螺旋探索中または呼び出されている途中のドローンを除外";
    }
}
