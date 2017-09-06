package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndSpiralTactics;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling.ICaller;

import java.util.List;

import static jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndSpiralTactics.DroneState.beingCalled;

/**
 * ドローンを呼び寄せ螺線探索をさせる。
 *
 * @author 遠藤拓斗 on 2017/06/07.
 */
class Caller implements ICaller {
    private List<DroneController> drones;

    Caller(List<DroneController> drones) {
        this.drones = drones;
    }

    @Override
    public void call(int callerId, int[] calleesId) {
        Point2D target = drones.get(callerId).getPoint();
        for (int i = 0; i < calleesId.length; i++) {
            DroneController d = drones.get(calleesId[i]);
            d.setState(beingCalled);
            d.setTarget(target);
            d.setNextSettings(i, calleesId.length);
        }
    }
}
