package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.CallingNearestDrones;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.FiltersManagement;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.SelectCalleeMediator;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.ITactics;

import java.util.ArrayList;
import java.util.List;

/**
 * ランダムに動き発見した場合周りのドローンを呼び寄せる
 *
 * @author 遠藤拓斗 on 2017/05/28.
 */
public class Tactics implements ITactics {
    private int numDrone;
    private double viewRangeRadius;
    private int turnInterval;
    private double limitOfTurningAngle;
    private List<DroneController> drones = new ArrayList<>();
    private SelectCalleeMediator selectCalleeMediator;
    private boolean useSpiral;
    private double searchRatio;
    private FiltersManagement fm;
    private int numOfDronesToCall;

    Tactics(int numDrone, double viewRangeRadius, int turnInterval, double limitOfTurningAngle, boolean useSpiral, double searchRatio, List<Drone> drones, FiltersManagement filtersManagement, int numOfDronesToCall, boolean callOnlyRandomWalkingOrSpiral) {
        this.numDrone = numDrone;
        this.viewRangeRadius = viewRangeRadius;
        this.turnInterval = turnInterval;
        this.limitOfTurningAngle = limitOfTurningAngle;
        this.useSpiral = useSpiral;
        this.searchRatio = searchRatio;
        this.selectCalleeMediator = new CallingNearestDrones(drones, new Caller(this.drones), numOfDronesToCall, filtersManagement);
        setDrones(drones);
        if (callOnlyRandomWalkingOrSpiral)
            filtersManagement.addFilter(new RemoveBeingCalledDrones(this.drones));
        this.fm = filtersManagement;
        this.numOfDronesToCall = numOfDronesToCall;
    }

    private void setDrones(List<Drone> drones) {
        for (int i = 0; i < drones.size(); i++) {
            this.drones.add(new DroneController(drones.get(i), numDrone, i, viewRangeRadius, turnInterval, limitOfTurningAngle, selectCalleeMediator, useSpiral, searchRatio));
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
        return "ランダム+呼び出し " + turnInterval + "秒に一回±" + limitOfTurningAngle + "[rad]の範囲でランダムに旋回"
                + "最初螺旋探索:" + useSpiral + (useSpiral ? "探索割合:" + searchRatio : "")
                + "呼び出すドローン数:" + numOfDronesToCall + fm;
    }
}
