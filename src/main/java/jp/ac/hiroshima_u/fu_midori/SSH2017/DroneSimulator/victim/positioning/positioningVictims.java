package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.positioning;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.Victim;

import java.util.ArrayList;

/**
 * 被災者を配置するインタフェースです。
 * @author 遠藤拓斗 on 2017/05/11.
 */
public interface positioningVictims {
    /**
     * 被災者を配置します。
     * @return 被災者のリスト
     */
    ArrayList<Victim> positionVictims();
}
