package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling;

/**
 * 呼び寄せるドローンを決める
 * informで知らされた情報をもとにbefore,after,inform内で呼び出すドローンを決めます
 *
 * @author 遠藤拓斗 on 2017/05/30.
 */
public interface SelectCalleeMediator {
    /**
     * ターンの最初に呼ばれます。
     */
    void before();

    /**
     * ターンの終わりに呼ばれます。
     */
    void after();

    /**
     * ドローンが被災者を発見したときに呼び出します。
     *
     * @param id  呼び出し元のドローンのid(setDronesで渡されたdrones.get(id)が呼び出し元)
     * @param num そのドローンが今回発見した被災者の数
     */
    void inform(int id, int num);
}
