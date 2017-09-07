package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.spiralTactics;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.ArchimedesSpiral.ArchimedeanSpiral;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.ITactics;

import java.util.ArrayList;
import java.util.List;

/**
 * アルキメデスの螺旋を利用して探索します
 *
 * @author 遠藤拓斗 on 2017/05/15.
 */
public class Tactics implements ITactics {
    private List<ArchimedeanSpiral> drones = new ArrayList<>();
    private int numDrone;
    private double viewRangeRadius;
    private double searchRatio;

    /**
     * コンストラクタ
     *
     * @param searchRatio     探索割合(0より大きく1以下)
     * @param numDrone        ドローン数
     * @param viewRangeRadius ドローンの視野の半径
     * @throws IllegalArgumentException searchRatioが0以下または1より大きい時
     */
    Tactics(double searchRatio, int numDrone, double viewRangeRadius, List<Drone> drones) throws IllegalArgumentException {
        if (!(0 < searchRatio && searchRatio <= 1))
            throw new IllegalArgumentException("探索割合は0より大きく1下でなければいけませんが" + searchRatio + "が渡されました");
        this.searchRatio = searchRatio;
        this.numDrone = numDrone;
        this.viewRangeRadius = viewRangeRadius;
        setDrones(drones);
    }

    private void setDrones(List<Drone> drones) {
        for (int i = 0; i < drones.size(); i++) {
            this.drones.add(new ArchimedeanSpiral(drones.get(i), numDrone, i, viewRangeRadius, searchRatio));
        }
    }

    @Override
    public void executeTurn() {
        for (ArchimedeanSpiral drone : drones) {
            drone.executeTurn();
        }
    }

    @Override
    public String toString() {
        return "螺旋探索 探索割合:" + searchRatio;
    }
}
