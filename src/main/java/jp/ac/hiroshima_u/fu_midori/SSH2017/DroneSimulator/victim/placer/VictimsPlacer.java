package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placer;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.Victims;

/**
 * 被災者を配置するインタフェースです。
 * @author 遠藤拓斗 on 2017/05/11.
 */
public interface VictimsPlacer {
    /**
     * 被災者を配置します。
     * @param population 人口
     * @return 被災者のリスト(サイズはpopulation)
     */
    Victims placeVictims(int population);
}
