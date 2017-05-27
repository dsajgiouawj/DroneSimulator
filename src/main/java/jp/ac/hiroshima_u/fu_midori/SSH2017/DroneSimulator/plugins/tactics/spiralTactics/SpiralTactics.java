package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.spiralTactics;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.Tactics;

import java.util.ArrayList;
import java.util.List;

/**
 * アルキメデスの螺旋を利用して隙間なく探索します
 *
 * @author 遠藤拓斗 on 2017/05/15.
 */
public class SpiralTactics implements Tactics {
    private List<SpiralTacticsDrone> drones = new ArrayList<>();
    private int numDrone;
    private double viewRangeRadius;

    SpiralTactics(int numDrone, double viewRangeRadius) {
        this.numDrone = numDrone;
        this.viewRangeRadius = viewRangeRadius;
    }

    @Override
    public void setDrones(List<? extends Drone> drones) {
        for (int i = 0; i < drones.size(); i++) {
            this.drones.add(new SpiralTacticsDrone(drones.get(i), numDrone, i, viewRangeRadius));
        }
    }

    @Override
    public void executeTurn() {
        for (SpiralTacticsDrone drone : drones) {
            drone.executeTurn();
        }
    }
}
