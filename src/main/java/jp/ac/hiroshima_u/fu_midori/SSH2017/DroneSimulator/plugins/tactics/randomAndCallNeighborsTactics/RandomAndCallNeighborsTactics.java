package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics.CallingTactics;
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
    private CallingTactics caller;
    private boolean useSpiral;
    private double searchRatio;

    RandomAndCallNeighborsTactics(int numDrone, double viewRangeRadius, int turnInterval, double limitOfTurningAngle, CallingTactics caller, boolean useSpiral, double searchRatio) {
        this.numDrone = numDrone;
        this.viewRangeRadius = viewRangeRadius;
        this.turnInterval = turnInterval;
        this.limitOfTurningAngle = limitOfTurningAngle;
        this.caller = caller;
        this.useSpiral = useSpiral;
        this.searchRatio = searchRatio;
    }

    @Override
    public void setDrones(List<? extends Drone> drones) {
        for (int i = 0; i < drones.size(); i++) {
            this.drones.add(new RandomDrone(drones.get(i), numDrone, i, viewRangeRadius, turnInterval, limitOfTurningAngle, caller, useSpiral, searchRatio));
        }
        caller.setDrones(this.drones);
    }

    @Override
    public void executeTurn() {
        caller.before();
        for (RandomDrone drone : drones) {
            drone.executeTurn();
        }
        caller.after();
    }
}
