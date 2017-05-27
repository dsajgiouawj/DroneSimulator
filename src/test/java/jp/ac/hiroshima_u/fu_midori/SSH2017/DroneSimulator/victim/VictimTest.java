package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim;

import javafx.geometry.Point2D;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Victimのテスト
 *
 * @author 遠藤拓斗 on 2017/05/12.
 */
public class VictimTest {
    private Victim sut;

    @Before
    public void setUp() {
        sut = new Victim(new Point2D(1, 1));
    }

    @Test
    public void getPointで点を取得できる() throws Exception {
        Point2D actual = sut.getPoint();
        Point2D expected = new Point2D(1, 1);
        assertThat(actual, is(expected));
    }

    @Test
    public void 初期状態でfoundはfalse() {
        boolean actual = sut.isFound();
        boolean expected = false;
        assertThat(actual, is(expected));
    }

    @Test
    public void setFoundで発見できる() {
        sut.setFound();
        boolean actual = sut.isFound();
        boolean expected = true;
        assertThat(actual, is(expected));
    }
}