package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling;

import java.util.stream.IntStream;

/**
 * CallingTacticsで呼び出すドローンの条件を規定する
 *
 * @author 遠藤拓斗 on 2017/05/31.
 */
public interface CallingFilter {
    void before();

    void after();

    /**
     * id番のドローンがnum人発見した
     *
     * @param id  id
     * @param num 発見人数
     */
    void informFinding(int id, int num);

    /**
     * id番のドローンが呼び出しを行った
     *
     * @param id id
     */
    void informCalling(int id);

    /**
     * id番のドローンが呼び出しを受けた
     *
     * @param id id
     */
    void informBeingCalled(int id);

    IntStream filter(int id, IntStream stream);
}
