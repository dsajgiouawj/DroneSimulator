package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.ICaller;

import java.util.List;

import static jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.DroneState.beingCalled;
import static jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.DroneState.randomWalking;

/**
 * 呼び寄せられたドローンが途中で被災者を発見した場合ランダムウォークに戻します
 *
 * @author 遠藤拓斗 on 2017/06/06.
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
            Point2D target2 = target.subtract(d.getPoint());
            double theta = Math.atan2(target2.getY(), target2.getX());
            d.setTheta(theta);
        }
        drones.get(callerId).setState(randomWalking);
    }
}
