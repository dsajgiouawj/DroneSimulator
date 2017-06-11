package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.CallNeighborsAndSpiralTactics;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.Caller;

import java.util.List;

import static jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.CallNeighborsAndSpiralTactics.DroneState.beingCalled;

/**
 * ドローンを呼び寄せ螺線探索をさせる。
 *
 * @author 遠藤拓斗 on 2017/06/07.
 */
public class CallerImpl implements Caller {
    private List<DroneController> drones;

    public CallerImpl(List<DroneController> drones) {
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
