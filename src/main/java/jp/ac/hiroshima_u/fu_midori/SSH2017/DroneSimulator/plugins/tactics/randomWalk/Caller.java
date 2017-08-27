package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomWalk;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling.ICaller;

import java.util.List;

public class Caller implements ICaller {
    private List<DroneController> drones;

    public Caller(List<DroneController> drones) {
        this.drones=drones;
    }

    @Override
    public void call(int callerId, int[] calleesId) {
        Point2D target = drones.get(callerId).getPoint();
        for (int i = 0; i < calleesId.length; i++) {
            DroneController d = drones.get(calleesId[i]);
            d.called(target);
        }
    }
}
