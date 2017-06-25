package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.simulator;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.DroneImpl;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.VieableDrone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.ITactics;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.TacticsUIFrontend;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.Victims;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.ViewableVictim;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placer.VictimsPlacer;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placer.VictimsPlacerUIFrontend;

import java.util.ArrayList;
import java.util.List;

/**
 * シミュレーションを行うクラス。
 *
 * @author 遠藤拓斗 on 2017/05/12.
 */
public class Simulator {
    private List<DroneImpl> drones = new ArrayList<>();
    private Victims victims;
    private ITactics tactics;
    private int residualTime;//残り時間
    private int limitTime;
    private VictimsPlacer placer;
    private int numDrone;
    private int population;
    private double viewRangeRadius;

    Simulator(TacticsUIFrontend tacticsUIFrontend, int limitTime, int numDrone, double viewRangeRadius, int population, VictimsPlacerUIFrontend victimsPlacerUIFrontend) {
        this.placer = victimsPlacerUIFrontend.getVictimsPlacer();
        this.victims = placer.placeVictims(population);
        for (int i = 0; i < numDrone; i++) {
            drones.add(new DroneImpl(viewRangeRadius, victims));
        }
        this.tactics = tacticsUIFrontend.getTactics(numDrone, viewRangeRadius, limitTime, new ArrayList<>(drones));
        this.residualTime = limitTime;
        this.limitTime = limitTime;
        this.numDrone = numDrone;
        this.population = population;
        this.viewRangeRadius = viewRangeRadius;
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
    public List<VieableDrone> getVieableDrones() {
        List<VieableDrone> res = new ArrayList<>();
        res.addAll(drones);
        return res;
    }

    /**
     * 表示用の被災者を取得します
     *
     * @return 被災者
     */
    public List<ViewableVictim> getVieableVictims() {
        return victims.getViewableVictims();
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
    public Victims getVictims() {
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

    public VictimsPlacer getPlacer() {
        return placer;
    }

    public ITactics getTactics() {
        return tactics;
    }

    @Override
    public String toString() {
        return "ドローン数:" + numDrone + "稼働時間:" + limitTime + "視野半径:" + viewRangeRadius + "人口:" + population;
    }

}
