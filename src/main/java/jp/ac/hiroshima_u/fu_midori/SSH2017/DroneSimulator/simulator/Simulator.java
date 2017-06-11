package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.simulator;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.DroneGUIInterface;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.DroneImpl;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.Tactics;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.TacticsUI;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.Victim;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.VictimXComparator;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placing.PlacingVictimsUI;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placing.VictimGUIInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * シミュレーションを行うクラス。
 *
 * @author 遠藤拓斗 on 2017/05/12.
 */
public class Simulator {
    private List<DroneImpl> drones = new ArrayList<>();
    private List<Victim> victims;
    private Tactics tactics;
    private int residualTime;//残り時間
    private int limitTime;

    Simulator(TacticsUI tacticsUI, int limitTime, int numDrone, double viewRangeRadius, int population, PlacingVictimsUI placingVictimsUI) {
        this.victims = placingVictimsUI.getPlacingVictims().placeVictims(population);
        this.victims.sort(new VictimXComparator());
        for (int i = 0; i < numDrone; i++) {
            drones.add(new DroneImpl(viewRangeRadius, victims));
        }
        this.tactics = tacticsUI.getTactics(numDrone, viewRangeRadius, limitTime, new ArrayList<>(drones));
        this.residualTime = limitTime;
        this.limitTime = limitTime;
    }

    /**
     * 次の1秒分シミュレーションを実行します。
     * 残り時間がなければ実行しません。
     */
    public void nextTurn() {
        if (residualTime == 0) return;
        residualTime--;
        for (DroneImpl drone : drones) {
            drone.nextTurn();
        }
        tactics.executeTurn();
    }

    /**
     * 残り時間分シミュレーションを実行します。
     */
    public void runThrough() {
        while (residualTime > 0) {
            nextTurn();
        }
    }

    /**
     * 表示用のドローンを取得します
     *
     * @return ドローン
     */
    public List<DroneGUIInterface> getDronesGUI() {
        List<DroneGUIInterface> res = new ArrayList<>();
        res.addAll(drones);
        return res;
    }

    /**
     * 表示用の被災者を取得します
     *
     * @return 被災者
     */
    public List<VictimGUIInterface> getVictimsGUI() {
        List<VictimGUIInterface> res = new ArrayList<>();
        res.addAll(victims);
        return res;
    }

    /**
     * ドローンを取得します
     *
     * @return ドローン
     */
    public List<DroneImpl> getDrones() {
        return drones;
    }

    /**
     * 被災者を取得します
     *
     * @return 被災者
     */
    public List<Victim> getVictims() {
        return victims;
    }

    /**
     * 現在の経過時間を取得
     *
     * @return 経過時間[s]
     */
    public int getTime() {
        return limitTime - residualTime;
    }


    /**
     * ドローンの最大稼働時間を取得
     *
     * @return ドローンの最大稼働時間
     */
    public int getLimitTime() {
        return limitTime;
    }

}
