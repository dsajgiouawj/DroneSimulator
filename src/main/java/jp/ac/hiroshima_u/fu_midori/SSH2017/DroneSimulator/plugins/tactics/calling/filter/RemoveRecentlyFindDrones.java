package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.filter;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.CallingFilter;

import java.util.List;
import java.util.stream.IntStream;

/**
 * 最近被災者を発見したドローンを除外します
 *
 * @author 遠藤拓斗 on 2017/05/30.
 */
public class RemoveRecentlyFindDrones implements CallingFilter {
    private int time = 0;
    private int[] lastFind = new int[0];//avoid findbugs warning
    private int thresholdTime;

    /**
     * コンストラクタ
     *
     * @param thresholdTime この時間以上経過していれば除外しない
     * @param drones        ドローン
     */
    public RemoveRecentlyFindDrones(int thresholdTime, List<Drone> drones) {
        this.thresholdTime = thresholdTime;
        this.lastFind = new int[drones.size()];
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
        lastFind[id] = time;
    }

    @Override
    public void informCalling(int id) {

    }

    @Override
    public void informBeingCalled(int id) {

    }

    @Override
    public IntStream filter(int id, IntStream stream) {
        return stream.filter(i -> time - lastFind[i] >= thresholdTime);
    }

    @Override
    public String toString() {
        return "最後に被災者を発見してから" + thresholdTime + "秒以上経過していないドローンを除外";
    }
}
