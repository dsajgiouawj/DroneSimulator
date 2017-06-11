package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics;

import javafx.scene.layout.VBox;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;

import java.util.List;

/**
 * TacticsをGUIから利用するためのインタフェースです。
 *
 * @author 遠藤拓斗 on 2017/05/11.
 */
public interface TacticsGUIInterface {
    /**
     * 情報を入力するのに必要なコントロールを配置します。
     *
     * @param vBox このvBoxにコントロールを配置します
     */
    void makeGUI(VBox vBox);

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
     * このプラグインの説明をします。
     * 戻り値はプラグイン選択に使われるためユニークである必要があります。
     * 複数のプラグインが同じ文字列を返した場合はうまく動きません
     *
     * @return 説明
     */
    String explain();
}
