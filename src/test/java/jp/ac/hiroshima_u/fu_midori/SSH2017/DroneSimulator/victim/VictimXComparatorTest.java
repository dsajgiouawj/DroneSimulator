package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim;

import javafx.geometry.Point2D;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * VictimXComparatorのテスト
 *
 * @author 遠藤拓斗 on 2017/05/15.
 */
public class VictimXComparatorTest {
    @Test
    public void victimをソートできる() throws Exception {
        Victim v1 = new Victim(new Point2D(0, 1));
        Victim v2 = new Victim(new Point2D(0, 0));
        Victim v3 = new Victim(new Point2D(-3, 2));
        Victim v4 = new Victim(new Point2D(5, 8));
        ArrayList<Victim> victims = new ArrayList<>();
        victims.add(v1);
        victims.add(v2);
        victims.add(v3);
        victims.add(v4);
        Collections.sort(victims, new VictimXComparator());
        assertThat(victims.get(0), is(v3));
        assertThat(victims.get(1), is(v1));
        assertThat(victims.get(2), is(v2));
        assertThat(victims.get(3), is(v4));
    }
}