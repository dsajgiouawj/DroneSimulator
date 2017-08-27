package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomWalk;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling.CallingFilter;

import java.util.List;
import java.util.stream.IntStream;

/**
 * 最近呼び出された地点に着いたドローンは呼ばない
 *
 * @author 遠藤拓斗 on 2017/8/27.
 */
public class RemoveRecentlyArriveDrones implements CallingFilter {
    private int time = 0;
    private int[] notCallUntil;
    private int thresholdTime;
    private List<Drone> drones;

    /**
     * コンストラクタ
     *
     * @param thresholdTime この時間以上経過していれば除外しない
     * @param drones        ドローン
     */
    public RemoveRecentlyArriveDrones(int thresholdTime, List<Drone> drones) {
        this.thresholdTime = thresholdTime;
        this.notCallUntil = new int[drones.size()];
        this.drones = drones;
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

    private Point2D target;

    @Override
    public void informCalling(int id) {
        notCallUntil[id] = time + thresholdTime;
        target = drones.get(id).getPoint();
    }

    @Override
    public void informBeingCalled(int id) {
        //呼び出されたので到達予定時刻の(threshold)秒後まで呼ばない
        notCallUntil[id] = time + (int) (drones.get(id).getPoint().distance(target) / drones.get(id).speed()) + thresholdTime;
    }

    @Override
    public IntStream filter(int id, IntStream stream) {
        return stream.filter(i -> time >= notCallUntil[i]);
    }

    @Override
    public String toString() {
        return "呼び出されている途中もしくは到着してから" + thresholdTime + "秒未満しか経過していないドローンを除外";
    }
}
