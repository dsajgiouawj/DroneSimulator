package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;

import java.util.List;

/**
 * TacticsをCUIから利用するためのインタフェースです。
 *
 * @author 遠藤拓斗 on 2017/05/11.
 */
public interface TacticsCUIInterface {
    /**
     * readInfoで読み込む引数の個数や型などの情報を表示します。
     * これはコンソールに表示されます。
     *
     * @return 説明文
     */
    String explain();

    /**
     * コンソールから必要な情報を読み込みます。
     */
    void readInfo();

    /**
     * 読み込んでおいた情報をもとにTacticsを生成します。
     *
     * @param numDrone        ドローンの数
     * @param viewRangeRadius カメラの視野の半径
     * @param limitTime       ドローンの稼働時間
     * @param drones          ドローン
     * @return Tactics
     */
    Tactics getTactics(int numDrone, double viewRangeRadius, int limitTime, List<Drone> drones);
}
