package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.simulator;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.DroneGUIInterface;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.DroneImpl;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.Tactics;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.Victim;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placing.PlacingVictims;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placing.VictimGUIInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * シミュレーションを行うクラス。
 *
 * @author 遠藤拓斗 on 2017/05/12.
 */
public class Simulator {
    private List<DroneImpl> drones;
    private List<Victim> victims;
    private Tactics tactics;
    private int residualTime;//残り時間

    Simulator(Tactics tactics, PlacingVictims placingVictims, int numDrone, int population, int limitTime, double viewRangeRadius) {
        this.victims = placingVictims.placeVictims(population);
        this.drones = new ArrayList<>();
        for (int i = 0; i < numDrone; i++) {
            this.drones.add(new DroneImpl(viewRangeRadius, victims));
        }
        this.tactics = tactics;
        this.tactics.setDrones(drones);
        this.residualTime = limitTime;
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
    public List<DroneGUIInterface> getDrones() {
        List<DroneGUIInterface> res = new ArrayList<>();
        res.addAll(drones);
        return res;
    }

    /**
     * 表示用の被災者を取得します
     *
     * @return 被災者
     */
    public List<VictimGUIInterface> getVictims() {
        List<VictimGUIInterface> res = new ArrayList<>();
        res.addAll(victims);
        return res;
    }
}
