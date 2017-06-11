package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;

import java.util.List;

/**
 * TacticsをUIから利用
 *
 * @author 遠藤拓斗 on 2017/06/11.
 */
public interface TacticsUI {
    /**
     * 入力された情報をもとにTacticsを生成します。
     *
     * @param numDrone        ドローンの数
     * @param viewRangeRadius カメラの視野の半径
     * @param limitTime       ドローンの稼働時間
     * @param drones          ドローン
     * @return 生成したTactics
     */
    Tactics getTactics(int numDrone, double viewRangeRadius, int limitTime, List<Drone> drones);

    /**
     * 説明文を返す
     *
     * @return 説明
     */
    String explain();
}
