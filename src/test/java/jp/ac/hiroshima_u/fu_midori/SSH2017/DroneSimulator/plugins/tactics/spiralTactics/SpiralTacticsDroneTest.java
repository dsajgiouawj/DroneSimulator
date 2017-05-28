package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.spiralTactics;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.DroneImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

/**
 * SpiralTacticsDroneのテスト
 *
 * @author 遠藤拓斗 on 2017/05/18.
 */
public class SpiralTacticsDroneTest {
    private SpiralTacticsDrone sut;
    private DroneImpl drone;
    private final double VIEW_RANGE_RADIUS = 10;

    @Before
    public void setUp() throws Exception {
        drone = new DroneImpl(VIEW_RANGE_RADIUS, new ArrayList<>());
    }

    @Test
    public void executeTurnで動く() throws Exception {
        sut = new SpiralTacticsDrone(drone, 1, 0, VIEW_RANGE_RADIUS, 1);
        drone.nextTurn();
        sut.executeTurn();
        assertThat(drone.getPoint().getX(), is(not(closeTo(0, 0.000001))));
        assertThat(drone.getPoint().getY(), is(not(closeTo(0, 0.000001))));
    }

    private final int LIMIT_TIME = 1000;
    private final double speed = 16;

    @Test
    public void searchRatioを1にしたとき正常に動く() throws Exception {
        sut = new SpiralTacticsDrone(drone, 1, 0, VIEW_RANGE_RADIUS, 1);
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
        sut = new SpiralTacticsDrone(drone, 1, 0, VIEW_RANGE_RADIUS, 0.5);
        for (int time = 0; time < LIMIT_TIME; time++) {
            drone.nextTurn();
            sut.executeTurn();
        }
        double actualRadius = drone.getPoint().distance(Point2D.ZERO);
        double actualArea = Math.PI * actualRadius * actualRadius;
        double expectedArea = speed * LIMIT_TIME * VIEW_RANGE_RADIUS * 2 * (1 / 0.5);//2倍の面積を探索できる
        assertThat(actualArea, is(closeTo(expectedArea, expectedArea * 0.005)));
    }
}