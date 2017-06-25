package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placer;

import javafx.scene.layout.VBox;

/**
 * VictimsPlacerをviewerから利用するためのインタフェースです。
 * @author 遠藤拓斗 on 2017/05/11.
 */
public interface VictimsPlacerViewerFrontend extends VictimsPlacerUIFrontend {
    /**
     * 情報を入力するのに必要なコントロールを配置します。
     * @param vBox このvBoxにコントロールを配置します
     */
    void makeGUI(VBox vBox);

    /**
     * 説明文を返す
     *
     * @return 説明
     */
    String explain();
}
