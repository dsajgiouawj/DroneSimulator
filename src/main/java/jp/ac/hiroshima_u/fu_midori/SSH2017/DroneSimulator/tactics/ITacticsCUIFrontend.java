package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics;

import java.util.Scanner;

/**
 * TacticsをCUIから利用するためのインタフェースです。
 *
 * @author 遠藤拓斗 on 2017/05/11.
 */
public interface ITacticsCUIFrontend extends TacticsUIFrontend {
    /**
     * コンソールから必要な情報を読み込みます。
     * System.outは禁止。説明はSystem.errに表示。
     */
    void readInfo(Scanner scanner);
}
