package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placing;

/**
 * GUI時の被災者表示用
 *
 * @author 遠藤拓斗 on 2017/05/27.
 */
public interface VictimGUIInterface {
    /**
     * 被災者のx座標取得
     *
     * @return x座標
     */
    double getX();

    /**
     * 被災者のy座標取得
     *
     * @return y座標
     */
    double getY();

    /**
     * 発見されているかどうか
     *
     * @return 発見されていればtrue
     */
    boolean isFound();
}
