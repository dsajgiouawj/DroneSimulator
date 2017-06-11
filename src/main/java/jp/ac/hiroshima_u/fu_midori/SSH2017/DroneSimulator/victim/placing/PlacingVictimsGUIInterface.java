package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placing;

import javafx.scene.layout.VBox;

/**
 * placingVictimsをGUIから利用するためのインタフェースです。
 * @author 遠藤拓斗 on 2017/05/11.
 */
public interface PlacingVictimsGUIInterface extends PlacingVictimsUI {
    /**
     * 情報を入力するのに必要なコントロールを配置します。
     * @param vBox このvBoxにコントロールを配置します
     */
    void makeGUI(VBox vBox);
}
