package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placing;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.Victim;

import java.util.List;

/**
 * 被災者を配置するインタフェースです。
 * @author 遠藤拓斗 on 2017/05/11.
 */
public interface PlacingVictims {
    /**
     * 被災者を配置します。
     * @param population 人口
     * @return 被災者のリスト(サイズはpopulation)
     */
    List<Victim> placeVictims(int population);
}
