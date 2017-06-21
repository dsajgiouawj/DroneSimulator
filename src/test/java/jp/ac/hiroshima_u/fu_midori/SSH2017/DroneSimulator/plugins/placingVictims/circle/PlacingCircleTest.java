package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.placingVictims.circle;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.Victims;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.ViewableVictim;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

/**
 * PlacingCircleのテスト
 *
 * @author 遠藤拓斗 on 2017/05/20.
 */
public class PlacingCircleTest {
    @Test
    public void placeVictimsですべての被災者が円内にいる() throws Exception {
        Point2D center = new Point2D(100, 200);
        double radius = 50;
        PlacingCircle sut = new PlacingCircle(center, radius);
        int population = 1000;
        Victims victims = sut.placeVictims(population);
        assertThat(victims.population(), is(population));
        for (ViewableVictim victim : victims.getViewableVictims()) {
            double distance = center.distance(victim.getPoint());
            assertThat(distance, is(lessThanOrEqualTo(radius)));
        }
    }

}