package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placing;

/**
 * placingVictimsをCUIから利用するためのインタフェースです。
 *
 * @author 遠藤拓斗 on 2017/05/11.
 */
public interface PlacingVictimsCUIInterface extends PlacingVictimsUI {
    /**
     * コンソールから必要な情報を読み込みます。
     */
    void readInfo();
}
