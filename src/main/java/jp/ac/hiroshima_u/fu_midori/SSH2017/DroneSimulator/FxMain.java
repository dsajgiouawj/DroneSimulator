package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * GUI時のMainクラス。
 *
 * @author 遠藤拓斗 on 2017/05/07.
 */
public final class FxMain extends Application {
    /**
     * メインクラス。
     *
     * @param args args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * javafxのstartメソッド。
     *
     * @param primaryStage primaryStage
     * @throws Exception Exception
     */
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("gui/main.fxml"));
        primaryStage.setTitle("Simulator");
        Scene scene = new Scene(root, 0, 0);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
