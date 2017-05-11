package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics;

import javafx.scene.layout.VBox;

/**
 * TacticsをGUIから利用するためのインタフェースです。
 * @author 遠藤拓斗 on 2017/05/11.
 */
public interface GUITacticsInterface {
    /**
     * 情報を入力するのに必要なコントロールを配置します。
     * @param vBox このvBoxにコントロールを配置します
     */
    void makeGUI(VBox vBox);

    /**
     * 入力された情報をもとにTacticsを生成します。
     * @return 生成したTactics
     */
    Tactics getTactics();

    /**
     * このプラグインの説明をします。
     * 戻り値はプラグイン選択に使われるためユニークである必要があります。
     * 複数のプラグインが同じ文字列を返した場合はうまく動きません
     * @return 説明
     */
    String explain();
}
