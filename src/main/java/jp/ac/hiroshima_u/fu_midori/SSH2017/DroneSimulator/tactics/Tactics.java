package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics;

/**
 * 探索方法のインターフェース。
 *
 * @author 遠藤拓斗 on 2017/05/11.
 */
public interface Tactics {
    /**
     * 1ターン実行します。
     * 全てのドローンを1ターン(1秒)分動かします。
     */
    void executeTurn();
}
