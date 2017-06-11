package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.filter;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.CallingFilter;

import java.util.List;
import java.util.stream.IntStream;

/**
 * もし発見したドローンが最近呼び出しを行っていたら呼び出しを行いません(すべて除外します)
 *
 * @author 遠藤拓斗 on 2017/05/31.
 */
public class CallIfTheFindingDroneHasNotCalledRecently implements CallingFilter {
    private int time = 0;
    private int[] lastCall;
    private int thresholdTime;

    /**
     * コンストラクタ
     * @param thresholdTime この秒数以上経過していれば呼び出します
     * @param drones ドローン
     */
    public CallIfTheFindingDroneHasNotCalledRecently(int thresholdTime, List<Drone> drones) {
        this.thresholdTime = thresholdTime;
        this.lastCall = new int[drones.size()];
        for (int i = 0; i < lastCall.length; i++) {
            lastCall[i] = Integer.MIN_VALUE / 3;//overflow対策
        }
    }

    @Override
    public void before() {
        time++;
    }

    @Override
    public void after() {

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
        if (time - lastCall[id] < thresholdTime) {
            //呼び出さない
            return stream.filter(i -> false);
        } else {
            //呼び出す
            return stream;
        }
    }
}
