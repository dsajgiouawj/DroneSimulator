package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * 入力に制約のあるラベル付きテキストフィールド。
 *
 * @author 遠藤拓斗 on 2016/11/20.
 */
public abstract class FormattedTextFieldWithLabel extends HBox {
    private Label label;
    private TextField textField;

    /**
     * @param text         ラベルのテキスト
     * @param formatter    フォーマットの種類
     * @param initialValue テキストフィールドの初期値
     */
    public FormattedTextFieldWithLabel(String text, Number initialValue, TextFormatter formatter) {
        super(100);

        label = new Label(text);
        textField = new TextField();
        textField.setTextFormatter(formatter);
        textField.setText(initialValue.toString());
        getChildren().addAll(label, textField);
        setHgrow(label, Priority.ALWAYS);
        setHgrow(textField, Priority.ALWAYS);
        textField.setMaxWidth(100);
        label.setMaxWidth(200);
    }

    protected String getText() {
        return textField.getText();
    }
}
