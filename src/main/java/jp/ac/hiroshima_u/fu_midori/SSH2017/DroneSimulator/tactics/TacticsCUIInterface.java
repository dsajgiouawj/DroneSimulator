package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics;

/**
 * TacticsをCUIから利用するためのインタフェースです。
 *
 * @author 遠藤拓斗 on 2017/05/11.
 */
public interface TacticsCUIInterface extends TacticsUI {
    /**
     * コンソールから必要な情報を読み込みます。
     */
    void readInfo();
}
