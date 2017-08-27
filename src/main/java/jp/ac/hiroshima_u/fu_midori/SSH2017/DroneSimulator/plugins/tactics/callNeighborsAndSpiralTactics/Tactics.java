package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.callNeighborsAndSpiralTactics;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling.CallingNearestDrones;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling.FiltersManagement;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling.SelectCalleeMediator;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.ITactics;

import java.util.ArrayList;
import java.util.List;

/**
 * 発見したら他のドローンを呼び出し螺線探索
 *
 * @author 遠藤拓斗 on 2017/06/07.
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
    private double searchRatio2;
    private int numOfDronesToCall;
    private FiltersManagement fm;

    Tactics(int numDrone, double viewRangeRadius, int turnInterval, double limitOfTurningAngle, boolean useSpiral, double searchRatio, double searchRatio2, List<Drone> drones, FiltersManagement filtersManagement, int numOfDronesToCall, int limitTime) {
        this.numDrone = numDrone;
        this.viewRangeRadius = viewRangeRadius;
        this.turnInterval = turnInterval;
        this.limitOfTurningAngle = limitOfTurningAngle;
        this.useSpiral = useSpiral;
        this.searchRatio = searchRatio;
        this.searchRatio2 = searchRatio2;
        this.selectCalleeMediator = new CallingNearestDrones(drones, new Caller(this.drones), numOfDronesToCall, filtersManagement);
        setDrones(drones);
        filtersManagement.addFilter(new RemoveSpiral2OrBeingCalledDrone(this.drones));
        //filtersManagement.addFilter(new NotCallIfNearFromThePastCallingPoints(drones,
        //        Math.sqrt(viewRangeRadius * 2 * drones.get(0).speed() * timeToContinueSpiral2SinceLastFind * numOfDronesToCall / Math.PI)));
        filtersManagement.addFilter(new NotCallIfOverlap(drones, limitTime, numOfDronesToCall));
        this.numOfDronesToCall = numOfDronesToCall;
        this.fm = filtersManagement;
    }

    private void setDrones(List<Drone> drones) {
        for (int i = 0; i < drones.size(); i++) {
            this.drones.add(new DroneController(drones.get(i), numDrone, i, viewRangeRadius, turnInterval, limitOfTurningAngle, selectCalleeMediator, useSpiral, searchRatio, searchRatio2));
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
        return "呼び出し後螺旋探索 " + turnInterval + "秒に一回±" + limitOfTurningAngle + "[rad]の範囲でランダムに旋回"
                + "最初螺旋探索:" + useSpiral + (useSpiral ? "探索割合:" + searchRatio : "")
                + "呼び出すドローン数:" + numOfDronesToCall + fm;
    }
}
