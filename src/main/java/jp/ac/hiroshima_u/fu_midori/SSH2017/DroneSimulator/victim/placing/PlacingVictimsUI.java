package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placing;

/**
 * PlacingVictimsをUIから利用
 *
 * @author 遠藤拓斗 on 2017/06/11.
 */
public interface PlacingVictimsUI {
    /**
     * 入力された情報をもとにplacingVictimsを生成します。
     *
     * @return 生成したplacingVictims
     */
    PlacingVictims getPlacingVictims();

    /**
     * 説明文を返す
     *
     * @return 説明
     */
    String explain();
}
