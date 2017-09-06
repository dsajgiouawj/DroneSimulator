package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.ArchimedesSpiral;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.DroneImpl;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.Victims;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

/**
 * ArchimedesSpiralのテスト
 *
 * @author 遠藤拓斗 on 2017/08/25.
 */
public class ArchimedesSpiralTest {
    private ArchimedesSpiral sut;
    private DroneImpl drone;
    private final double VIEW_RANGE_RADIUS = 10;
    private Victims victims = new Victims(new ArrayList<>());

    @Test
    public void executeTurnで動く() throws Exception {
        drone = new DroneImpl(VIEW_RANGE_RADIUS, victims);
        sut = new ArchimedesSpiral(drone, 1, 0, VIEW_RANGE_RADIUS, 1, Point2D.ZERO);
        drone.nextTurn();
        sut.executeTurn();
        assertThat(drone.getPoint().getX(), is(not(closeTo(0, 0.000001))));
        assertThat(drone.getPoint().getY(), is(not(closeTo(0, 0.000001))));
    }

    private final int LIMIT_TIME = 1000;
    private final double speed = 16;

    @Test
    public void searchRatioを1にしたとき正常に動く() throws Exception {
        drone = new DroneImpl(VIEW_RANGE_RADIUS, victims);
        sut = new ArchimedesSpiral(drone, 1, 0, VIEW_RANGE_RADIUS, 1, Point2D.ZERO);
        for (int time = 0; time < LIMIT_TIME; time++) {
            drone.nextTurn();
            sut.executeTurn();
        }
        double actualRadius = drone.getPoint().distance(Point2D.ZERO);
        double actualArea = Math.PI * actualRadius * actualRadius;
        double expectedArea = speed * LIMIT_TIME * VIEW_RANGE_RADIUS * 2; //重ならない場合の面積
        assertThat(actualArea, is(closeTo(expectedArea, expectedArea * 0.005)));
    }

    @Test
    public void searchRatioを05にしたとき正常に動く() throws Exception {
        drone = new DroneImpl(VIEW_RANGE_RADIUS, victims);
        sut = new ArchimedesSpiral(drone, 1, 0, VIEW_RANGE_RADIUS, 0.5, Point2D.ZERO);
        for (int time = 0; time < LIMIT_TIME; time++) {
            drone.nextTurn();
            sut.executeTurn();
        }
        double actualRadius = drone.getPoint().distance(Point2D.ZERO);
        double actualArea = Math.PI * actualRadius * actualRadius;
        double expectedArea = speed * LIMIT_TIME * VIEW_RANGE_RADIUS * 2 * (1 / 0.5);//2倍の面積を探索できる
        assertThat(actualArea, is(closeTo(expectedArea, expectedArea * 0.005)));
    }

    @Test
    public void searchRatioを1にしたときsimulateが正しい() throws Exception {
        Point2D center = new Point2D(10, 20);
        drone = new DroneImpl(VIEW_RANGE_RADIUS, victims, center);
        sut = new ArchimedesSpiral(drone, 1, 0, VIEW_RANGE_RADIUS, 1, center);
        for (int time = 0; time < LIMIT_TIME; time++) {
            drone.nextTurn();
            sut.executeTurn();
        }
        Point2D actual = ArchimedesSpiral.simulate(1, 0, VIEW_RANGE_RADIUS, 1, center, LIMIT_TIME);
        assertThat(actual.getX(), is(drone.getX()));
        assertThat(actual.getY(), is(drone.getY()));
    }

    @Test
    public void searchRatioを05にしたときsimulateが正しい() throws Exception {
        Point2D center = new Point2D(10, 20);
        drone = new DroneImpl(VIEW_RANGE_RADIUS, victims, center);
        sut = new ArchimedesSpiral(drone, 1, 0, VIEW_RANGE_RADIUS, 0.5, center);
        for (int time = 0; time < LIMIT_TIME; time++) {
            drone.nextTurn();
            sut.executeTurn();
        }
        Point2D actual = ArchimedesSpiral.simulate(1, 0, VIEW_RANGE_RADIUS, 0.5, center, LIMIT_TIME);
        assertThat(actual.getX(), is(drone.getX()));
        assertThat(actual.getY(), is(drone.getY()));
    }
}