package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.CallingACertainNumberOfDrones;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.FiltersManagement;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.SelectCalleeMediator;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.Tactics;

import java.util.ArrayList;
import java.util.List;

/**
 * ランダムに動き発見した場合周りのドローンを呼び寄せる
 *
 * @author 遠藤拓斗 on 2017/05/28.
 */
public class RandomAndCallNeighborsTactics implements Tactics {
    private int numDrone;
    private double viewRangeRadius;
    private int turnInterval;
    private double limitOfTurningAngle;
    private List<RandomDrone> drones = new ArrayList<>();
    private SelectCalleeMediator selectCalleeMediator;
    private boolean useSpiral;
    private double searchRatio;

    RandomAndCallNeighborsTactics(int numDrone, double viewRangeRadius, int turnInterval, double limitOfTurningAngle, boolean useSpiral, double searchRatio, List<Drone> drones, FiltersManagement filtersManagement, int certainNumber, boolean callOnlyRandomWalkingOrSpiral) {
        this.numDrone = numDrone;
        this.viewRangeRadius = viewRangeRadius;
        this.turnInterval = turnInterval;
        this.limitOfTurningAngle = limitOfTurningAngle;
        this.useSpiral = useSpiral;
        this.searchRatio = searchRatio;
        this.selectCalleeMediator = new CallingACertainNumberOfDrones(drones, new RandomAndCallNeighborsCaller(this.drones), certainNumber, filtersManagement);
        setDrones(drones);
        if (callOnlyRandomWalkingOrSpiral)
            filtersManagement.addFilter(new CallingOnlyRandomWalkingOrSpiralDrones(this.drones));
    }

    private void setDrones(List<Drone> drones) {
        for (int i = 0; i < drones.size(); i++) {
            this.drones.add(new RandomDrone(drones.get(i), numDrone, i, viewRangeRadius, turnInterval, limitOfTurningAngle, selectCalleeMediator, useSpiral, searchRatio));
        }
    }

    @Override
    public void executeTurn() {
        selectCalleeMediator.before();
        for (RandomDrone drone : drones) {
            drone.executeTurn();
        }
        selectCalleeMediator.after();
    }
}
