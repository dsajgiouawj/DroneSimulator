package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.callIfTheFindingDroneHaveNotCalledRecently;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.RandomDrone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.CallingSubTactics;

import java.util.List;
import java.util.stream.Stream;

/**
 * もし発見したドローンが最近呼び出しを行っていないなら呼び出しを行います
 *
 * @author 遠藤拓斗 on 2017/05/31.
 */
public class CallIfTheFindingDroneHasNotCalledRecently implements CallingSubTactics {
    private int time = 0;
    private int[] lastCall;
    private int thresholdTime;

    public CallIfTheFindingDroneHasNotCalledRecently(int thresholdTime) {
        this.thresholdTime = thresholdTime;
    }

    @Override
    public void setDrones(List<RandomDrone> drones) {
        lastCall = new int[drones.size()];
    }

    @Override
    public void before() {
        time++;
    }

    @Override
    public void after() {

    }

    @Override
    public void inform(int id, int num) {
    }

    @Override
    public void call(int id) {
        if (lastCall == null) throw new NullPointerException();
        lastCall[id] = time;
    }

    @Override
    public void beCalled(int id) {

    }

    @Override
    public Stream<RandomDrone> filter(int id, Stream<RandomDrone> stream) {
        if (lastCall == null) throw new NullPointerException();
        if (time - lastCall[id] < thresholdTime) {
            //呼び出さない
            return stream.filter(d -> false);
        } else {
            //呼び出す
            return stream;
        }
    }
}
