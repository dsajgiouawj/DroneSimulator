package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.VictimsPlacer.circles;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.Victims;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.ViewableVictim;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;


/**
 * CirclesPlacerの簡易的なテスト
 *
 * @author 遠藤拓斗 on 2017/06/11.
 */
public class PlacerTest {
    private Placer sut;
    private final Point2D centerOfRange = new Point2D(100, 200);
    private final double radiusOfRange = 2000;
    private final double radiusOfCircles = 200;
    private final int numOfCircles = 20;

    @Before
    public void setUp() throws Exception {
        sut = new Placer(centerOfRange, radiusOfRange, radiusOfCircles, numOfCircles);
    }

    @Test
    public void 生成する人数が正しい() throws Exception {
        Victims actual = sut.placeVictims(2017);
        assertThat(actual.population(), is(2017));
        for (ViewableVictim victim : actual.getViewableVictims()) {
            assertThat(victim.getPoint().distance(centerOfRange), is(lessThanOrEqualTo(radiusOfRange)));
        }
    }

    @Test
    public void 生成する人数がnumOfCirclesより小さい時正しい() throws Exception {
        Victims actual = sut.placeVictims(numOfCircles / 2);
        assertThat(actual.population(), is(numOfCircles / 2));
        for (ViewableVictim victim : actual.getViewableVictims()) {
            assertThat(victim.getPoint().distance(centerOfRange), is(lessThanOrEqualTo(radiusOfRange)));
        }
    }

    @Test
    public void 生成する人数がゼロ人の時正しい() throws Exception {
        Victims actual = sut.placeVictims(0);
        assertThat(actual.population(), is(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void radiusOfCirclesがradiusOfRange以上だと例外を送出() throws Exception {
        sut = new Placer(centerOfRange, 1000, 1000, numOfCircles);
    }

    @Test
    public void radiusOfCirclesがradiusOfRange未満だと例外を送出しない() throws Exception {
        sut = new Placer(centerOfRange, 1000, 999.9, numOfCircles);
    }

}