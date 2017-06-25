package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.filter;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.CallingFilter;

import java.util.List;
import java.util.stream.IntStream;

/**
 * 最近呼び出しを行ったドローンを除外します
 *
 * @author 遠藤拓斗 on 2017/05/30.
 */
public class RemoveRecentlyCallDrones implements CallingFilter {
    private int time = 0;
    private int[] lastCall = new int[0];//avoid findbugs warning
    private int thresholdTime;

    /**
     * コンストラクタ
     *
     * @param thresholdTime この時間以上経過していれば除外しない
     * @param drones        ドローン
     */
    public RemoveRecentlyCallDrones(int thresholdTime, List<Drone> drones) {
        this.thresholdTime = thresholdTime;
        this.lastCall = new int[drones.size()];
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
    public void informFinding(int id, int num) {
    }

    @Override
    public void informCalling(int id) {
        lastCall[id] = time;
    }

    @Override
    public void informBeingCalled(int id) {

    }

    @Override
    public IntStream filter(int id, IntStream stream) {
        return stream.filter(i -> time - lastCall[i] >= thresholdTime);
    }

    @Override
    public String toString() {
        return "最後に呼び出しを行ってから" + thresholdTime + "秒以上経過していないドローンを除外";
    }
}
