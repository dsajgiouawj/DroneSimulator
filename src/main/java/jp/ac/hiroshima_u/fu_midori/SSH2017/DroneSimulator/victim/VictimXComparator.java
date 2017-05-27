package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Victimをx昇順に並び替え
 *
 * @author 遠藤拓斗 on 2017/05/15.
 */
public class VictimXComparator implements Comparator<Victim>, Serializable {
    private static final long serialVersionUID = 1L;

    public int compare(Victim o1, Victim o2) {
        return Double.compare(o1.getPoint().getX(), o2.getPoint().getX());
    }
}
