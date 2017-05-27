package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls;

import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

/**
 * 整数値のみ入力できるラベル付きテキストフィールド
 *
 * @author 遠藤拓斗 on 2017/05/15.
 */
public class IntTextFieldWithLabel extends FormattedTextFieldWithLabel {
    /**
     * コンストラクタ
     * @param text ラベルのテキスト
     * @param initialValue テキストフィールドの初期値
     */
    public IntTextFieldWithLabel(String text, int initialValue) {
        super(text, initialValue, new TextFormatter<>(new IntegerStringConverter()));
    }

    /**
     * テキストフィールドに入力された値を返します。
     * @return テキストフィールドに入力された値
     */
    public int getValue() {
        return Integer.parseInt(getText());
    }
}
