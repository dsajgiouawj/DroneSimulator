package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.RandomDrone;

/**
 * 距離を比較するためのクラス
 *
 * @author 遠藤拓斗 on 2017/05/30.
 */
public class DistanceCompare implements Comparable<DistanceCompare> {
    public final double distance;
    public final RandomDrone drone;

    public DistanceCompare(double distance, RandomDrone drone) {
        this.distance = distance;
        this.drone = drone;
    }

    @Override
    public int compareTo(DistanceCompare o) {
        return Double.compare(distance, o.distance);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (!(obj instanceof DistanceCompare)) return false;
        DistanceCompare otherDC = (DistanceCompare) obj;
        return Double.valueOf(distance).equals(otherDC.distance);
    }

    @Override
    public int hashCode() {
        return Double.hashCode(distance);
    }
}
