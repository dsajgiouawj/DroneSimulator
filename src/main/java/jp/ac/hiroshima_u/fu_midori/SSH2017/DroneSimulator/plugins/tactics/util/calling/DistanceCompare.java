package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;

/**
 * 距離を比較するためのクラス
 *
 * @author 遠藤拓斗 on 2017/05/30.
 */
public class DistanceCompare implements Comparable<DistanceCompare> {
    public final double distance;
    public final int id;

    public DistanceCompare(double distance, Drone drone, int id) {
        this.distance = distance;
        this.id = id;
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
