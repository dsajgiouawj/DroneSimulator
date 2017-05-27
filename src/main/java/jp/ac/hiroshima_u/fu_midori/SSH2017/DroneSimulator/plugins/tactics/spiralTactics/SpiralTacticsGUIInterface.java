package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.spiralTactics;

import javafx.scene.layout.VBox;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.Tactics;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.TacticsGUIInterface;

/**
 * spiralTacticsをGUIから利用するためのインタフェース
 *
 * @author 遠藤拓斗 on 2017/05/15.
 */
public class SpiralTacticsGUIInterface implements TacticsGUIInterface {
    @Override
    public void makeGUI(VBox vBox) {
        //do nothing
    }

    @Override
    public Tactics getTactics(int numDrone, double viewRangeRadius, int limitTime) {
        return new SpiralTactics(numDrone, viewRangeRadius);
    }

    @Override
    public String explain() {
        return "螺旋探索(隙間なし)";
    }
}
