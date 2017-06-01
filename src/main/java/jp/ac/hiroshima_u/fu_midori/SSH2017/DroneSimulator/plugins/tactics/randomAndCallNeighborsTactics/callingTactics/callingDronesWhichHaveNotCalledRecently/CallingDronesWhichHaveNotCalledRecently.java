package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.callingDronesWhichHaveNotCalledRecently;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.RandomDrone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.CallingSubTactics;

import java.util.List;
import java.util.stream.Stream;

/**
 * 最近呼び出しを行っていないドローンを呼び出します
 *
 * @author 遠藤拓斗 on 2017/05/30.
 */
public class CallingDronesWhichHaveNotCalledRecently implements CallingSubTactics {
    private int time = 0;
    private int[] lastCall = new int[0];//avoid findbugs warning
    private int thresholdTime;

    public CallingDronesWhichHaveNotCalledRecently(int thresholdTime) {
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
        //do nothing
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
        return stream.filter(d -> time - lastCall[d.getId()] >= thresholdTime);
    }
}
