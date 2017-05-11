package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.positioning;

/**
 * positionVictimsをCUIから利用するためのインタフェースです。
 * @author 遠藤拓斗 on 2017/05/11.
 */
public interface CUIPositioningVictimsInterface {
    /**
     * readInfoで読み込む引数の個数や型などの情報を表示します。
     * これはコンソールに表示されます。
     * @return 説明文
     */
    String explain();

    /**
     * コンソールから必要な情報を読み込みます。
     */
    void readInfo();

    /**
     * 読み込んでおいた情報をもとにpositioningVictimsを生成します。
     * @return 生成したpositioningVictims
     */
    positioningVictims getPositionVictims();
}
