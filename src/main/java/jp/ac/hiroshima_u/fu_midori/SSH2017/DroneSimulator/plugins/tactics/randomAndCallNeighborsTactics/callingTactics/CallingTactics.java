package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.RandomDrone;

import java.util.List;

/**
 * 周りのドローンを呼び寄せる方法
 * informで知らされた情報をもとにbeforeまたはafter(またはinform内)でドローンに指示を出します(setState等で)
 *
 * @author 遠藤拓斗 on 2017/05/30.
 */
public interface CallingTactics {
    /**
     * 最初に呼ばれるメソッドです。ドローンが渡されます。
     *
     * @param drones ドローン
     */
    void setDrones(List<RandomDrone> drones);

    /**
     * ターンの最初に呼ばれます。
     */
    void before();

    /**
     * ターンの終わりに呼ばれます。
     */
    void after();

    /**
     * ドローンが被災者を発見したときに呼び出します。
     *
     * @param id  呼び出し元のドローンのid(setDronesで渡されたdrones.get(id)が呼び出し元)
     * @param num そのドローンが今回発見した被災者の数
     */
    void inform(int id, int num);
}
