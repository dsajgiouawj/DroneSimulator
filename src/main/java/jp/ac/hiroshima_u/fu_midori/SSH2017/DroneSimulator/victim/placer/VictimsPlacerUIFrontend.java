package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placer;

/**
 * VictimsPlacerをUIから利用
 *
 * @author 遠藤拓斗 on 2017/06/11.
 */
public interface VictimsPlacerUIFrontend {
    /**
     * 入力された情報をもとにVictimsPlacerを生成します。
     *
     * @return 生成したVictimsPlacer
     */
    VictimsPlacer getVictimsPlacer();
}
