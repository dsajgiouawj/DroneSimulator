package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomWalk;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling.CallingNearestDrones;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling.FiltersManagement;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling.SelectCalleeMediator;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling.filter.RemoveRecentlyFindDrones;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.ITactics;

import java.util.ArrayList;
import java.util.List;

/**
 * ランダムウォーク(呼び出し・呼び出し確率機能付き)
 *
 * @author 遠藤拓斗 on 2017/8/27.
 */
public class Tactics implements ITactics {
    private int numDrone;
    private double viewRangeRadius;
    private double limitOfTurningAngle;
    private int turnInterval;
    private List<DroneController> drones = new ArrayList<>();
    private SelectCalleeMediator selectCalleeMediator;
    private FiltersManagement fm;
    private int thresholdTime;
    private double probability;

    /**
     * @param numDrone            ドローンの台数
     * @param viewRangeRadius     ドローンの視野半径
     * @param limitTime           ドローンの稼働時間
     * @param drones              ドローン
     * @param limitOfTurningAngle 旋回制限角度[±rad]
     * @param turnInterval        旋回間隔[s]
     * @param thresholdTime       この時間以上被災者を発見していなければ呼び寄せられる
     * @param probability         呼び出される確率(0以上1以下)(0以下の値は0,1以上の値は1とみなす)
     */
    public Tactics(int numDrone, double viewRangeRadius, int limitTime, List<Drone> drones, double limitOfTurningAngle, int turnInterval, int thresholdTime, double probability) {
        this.numDrone = numDrone;
        this.viewRangeRadius = viewRangeRadius;
        this.limitOfTurningAngle = limitOfTurningAngle;
        this.turnInterval = turnInterval;
        fm = new FiltersManagement(new RemoveRecentlyFindDrones(thresholdTime, drones)
                , new RemoveRecentlyArriveDrones(thresholdTime, drones)
                , new RemoveRandomly(probability));
        selectCalleeMediator = new CallingNearestDrones(drones, new Caller(this.drones), numDrone, fm);//全て呼ぶ
        setDrones(drones);
        this.thresholdTime = thresholdTime;
        this.probability = probability;
    }

    private void setDrones(List<Drone> drones) {
        for (int i = 0; i < numDrone; i++) {
            this.drones.add(new DroneController(drones.get(i), numDrone, i, viewRangeRadius, turnInterval, limitOfTurningAngle, selectCalleeMediator));
        }
    }

    @Override
    public void executeTurn() {
        selectCalleeMediator.before();
        for (DroneController drone : drones) {
            drone.executeTurn();
        }
        selectCalleeMediator.after();
    }

    @Override
    public String toString() {
        return "ランダムウォーク" + +turnInterval + "秒に一回±" + limitOfTurningAngle + "[rad]の範囲でランダムに旋回"
                + thresholdTime + "秒以上発見していなければ呼び寄せられる" + "呼び寄せられる確率" + probability + fm;
    }
}
