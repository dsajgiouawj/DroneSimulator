package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.VictimsPlacer.concentricCircle;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.Victims;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.ViewableVictim;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;

/**
 * @author 遠藤拓斗 on 2017/08/28.
 */
public class PlacerTest {
    @Test
    public void 全ての被災者が内円と外円の間にいる() throws Exception {
        Point2D center = new Point2D(100, 200);
        double innerRadius = 300;
        double outerRadius = 500;
        Placer sut = new Placer(center, innerRadius, outerRadius);
        int population = 1000;
        Victims victims = sut.placeVictims(population);
        assertThat(victims.population(), is(population));
        for (ViewableVictim victim : victims.getViewableVictims()) {
            double distance = center.distance(victim.getPoint());
            assertThat(distance, is(lessThanOrEqualTo(outerRadius)));
            assertThat(distance, is(greaterThanOrEqualTo(innerRadius)));
        }
    }

}