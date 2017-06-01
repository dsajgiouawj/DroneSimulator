package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.callingTactics;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics.RandomDrone;

import java.util.List;
import java.util.stream.Stream;

/**
 * CallingTacticsで呼び出すドローンの条件を規定する
 *
 * @author 遠藤拓斗 on 2017/05/31.
 */
public interface CallingSubTactics {
    void setDrones(List<RandomDrone> drones);

    void before();

    void after();

    /**
     * id番のドローンがnum人発見した
     * @param id
     * @param num
     */
    void inform(int id, int num);

    /**
     * id番のドローンが呼び出しを行った
     * @param id
     */
    void call(int id);

    /**
     * id番のドローンが呼び出しを受けた
     * @param id
     */
    void beCalled(int id);

    Stream<RandomDrone> filter(int id, Stream<RandomDrone> stream);
}
