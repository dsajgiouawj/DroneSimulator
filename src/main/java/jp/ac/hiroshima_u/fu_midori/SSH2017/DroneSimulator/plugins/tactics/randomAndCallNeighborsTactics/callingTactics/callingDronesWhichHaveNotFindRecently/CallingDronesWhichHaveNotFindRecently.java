package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.callingDronesWhichHaveNotFindRecently;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.RandomDrone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.CallingSubTactics;

import java.util.List;
import java.util.stream.Stream;

/**
 * 最近被災者を発見していないドローンを呼び出します。
 *
 * @author 遠藤拓斗 on 2017/05/30.
 */
public class CallingDronesWhichHaveNotFindRecently implements CallingSubTactics {
    private int time = 0;
    private int[] lastFind = new int[0];//avoid findbugs warning
    private int thresholdTime;

    public CallingDronesWhichHaveNotFindRecently(int thresholdTime) {
        this.thresholdTime = thresholdTime;
    }

    @Override
    public void setDrones(List<RandomDrone> drones) {
        lastFind = new int[drones.size()];
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
        if (lastFind == null) throw new NullPointerException();
        lastFind[id] = time;
    }

    @Override
    public void call(int id) {

    }

    @Override
    public void beCalled(int id) {

    }

    @Override
    public Stream<RandomDrone> filter(int id, Stream<RandomDrone> stream) {
        if (lastFind == null) throw new NullPointerException();
        return stream.filter(d -> time - lastFind[d.getId()] >= thresholdTime);
    }
}
