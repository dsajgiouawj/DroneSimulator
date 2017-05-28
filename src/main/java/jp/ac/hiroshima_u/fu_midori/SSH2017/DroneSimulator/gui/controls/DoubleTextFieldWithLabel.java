package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls;

import javafx.scene.control.TextFormatter;
import javafx.util.converter.DoubleStringConverter;

/**
 * 実数地の未入力できるラベル付きテキストフィールド
 *
 * @author 遠藤拓斗 on 2017/05/15.
 */
public class DoubleTextFieldWithLabel extends FormattedTextFieldWithLabel<Double> {
    /**
     * コンストラクタ
     *
     * @param text         ラベルのテキスト
     * @param initialValue テキストフィールドの初期値
     */
    public DoubleTextFieldWithLabel(String text, double initialValue) {
        super(text, initialValue, new TextFormatter<>(new DoubleStringConverter()));
    }

    /**
     * テキストフィールドに入力された値を返します。
     *
     * @return テキストフィールドに入力された値
     */
    @Override
    public Double getValue() {
        return Double.parseDouble(getText());
    }
}
