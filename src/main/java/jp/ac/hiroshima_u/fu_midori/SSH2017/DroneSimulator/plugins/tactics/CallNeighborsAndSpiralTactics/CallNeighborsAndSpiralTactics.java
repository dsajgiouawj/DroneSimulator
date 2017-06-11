package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.CallNeighborsAndSpiralTactics;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.CallingACertainNumberOfDrones;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.FiltersManagement;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.SelectCalleeMediator;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.Tactics;

import java.util.ArrayList;
import java.util.List;

/**
 * 発見したら他のドローンを呼び出し螺線探索
 *
 * @author 遠藤拓斗 on 2017/06/07.
 */
public class CallNeighborsAndSpiralTactics implements Tactics {
    private int numDrone;
    private double viewRangeRadius;
    private int turnInterval;
    private double limitOfTurningAngle;
    private List<DroneController> drones = new ArrayList<>();
    private SelectCalleeMediator selectCalleeMediator;
    private boolean useSpiral;
    private double searchRatio;
    private double searchRatio2;

    CallNeighborsAndSpiralTactics(int numDrone, double viewRangeRadius, int turnInterval, double limitOfTurningAngle, boolean useSpiral, double searchRatio, double searchRatio2, List<Drone> drones, FiltersManagement filtersManagement, int certainNumber) {
        this.numDrone = numDrone;
        this.viewRangeRadius = viewRangeRadius;
        this.turnInterval = turnInterval;
        this.limitOfTurningAngle = limitOfTurningAngle;
        this.useSpiral = useSpiral;
        this.searchRatio = searchRatio;
        this.searchRatio2 = searchRatio2;
        this.selectCalleeMediator = new CallingACertainNumberOfDrones(drones, new CallerImpl(this.drones), certainNumber, filtersManagement);
        setDrones(drones);
        filtersManagement.addFilter(new FilterSpiral2OrBeingCalledDrone(this.drones));
        filtersManagement.addFilter(new CallIfNotNearFromThePastCallingPoints(drones, 1000));
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
}
