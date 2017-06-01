package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

/**
 * ドローンを操作するためのインターフェースです。
 * Tacticsはこのインターフェースを操作します。
 *
 * @author 遠藤拓斗 on 2017/05/07.
 */
public interface Drone {
    /**
     * 今向いている方向にまっすぐ進みます。
     * 進む距離はこの時間に進める最大距離です。
     */
    void goStraight();

    /**
     * 今向いている方向に指定された距離だけまっすぐ進みます。
     * 進める距離が指定された距離より短い場合進めるだけ進みます。
     *
     * @param distance 進む距離
     */
    void goStraight(double distance);

    /**
     * 指定された方向に進みます。
     * 進む距離はこの時間に進める最大距離です。
     * 方向ベクトルが零ベクトルの場合は何もしません。
     *
     * @param direction 方向ベクトル
     */
    void goTheDirection(Point2D direction);

    /**
     * 指定された方向に指定された距離だけ進みます。
     * 進める距離が指定された距離より短い場合進めるだけ進みます。
     * 方向ベクトルが零ベクトルの場合は何もしません。
     *
     * @param direction 方向ベクトル
     * @param distance  進む距離
     */
    void goTheDirection(Point2D direction, double distance);

    /**
     * 指定された点の方向に進みます。
     * 指定された点に行ける場合は指定された点で止まります。
     *
     * @param target 点(位置ベクトル)
     */
    void goToPoint(Point2D target);

    /**
     * ドローンを時計回りに回転させます。
     *
     * @param deltaTheta 角度(rad)
     */
    void turnClockWise(double deltaTheta);

    /**
     * ドローンを反時計回りに回転させます。
     *
     * @param deltaTheta 角度(rad)
     */
    void turnCounterClockWise(double deltaTheta);

    /**
     * ドローンの向きを指定させた角度にします。
     *
     * @param theta 角度(rad)
     */
    void setTheta(double theta);

    /**
     * ドローンの現在向いている方向を取得します。
     * 0以上2π未満の値を返します。
     *
     * @return 角度(rad)
     */
    double getTheta();

    /**
     * ドローンの現在地を取得します。
     *
     * @return ドローンの現在地
     */
    Point2D getPoint();

    /**
     * この時間に進める残り距離を返します。
     *
     * @return 進める距離
     */
    double getResidualDistance();

    /**
     * この時間にまだ進めるかどうか返します。
     *
     * @return 進める場合:true 進めない場合:false
     */
    boolean canMove();

    /**
     * 前回の動作で発見した被災者の数を返します。
     * 直前のgo*メソッドの呼び出しで発見した被災者の数です
     * @return 被災者の数
     */
    int getNumOfFoundVictimsWhileLastMovement();

    /**
     * GUI時に表示する色を設定します。
     *
     * @param color 色
     */
    void setColor(Color color);
}
