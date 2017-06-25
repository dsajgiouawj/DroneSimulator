package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placer;

import java.util.Scanner;

/**
 * VictimsPlacerをCUIから利用するためのインタフェースです。
 *
 * @author 遠藤拓斗 on 2017/05/11.
 */
public interface VictimsPlacerCUIFrontend extends VictimsPlacerUIFrontend {
    /**
     * コンソールから必要な情報を読み込みます。
     * System.outは禁止。説明はSystem.errに表示。
     */
    void readInfo(Scanner scanner);
}
