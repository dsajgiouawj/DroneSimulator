package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling;

/**
 * 実際に呼び出しを行うクラス
 *
 * @author 遠藤拓斗 on 2017/06/06.
 */
public interface ICaller {
    /**
     * 呼び出しを行います
     * @param callerId 呼び出す側のid
     * @param calleesId 呼び出される側のid
     */
    void call(int callerId, int[] calleesId);
}
