package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.spiralTactics;

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

    @Before
    public void setUp() throws Exception {
        drone = new DroneImpl(10, new ArrayList<>());
        drone.nextTurn();
        sut = new SpiralTacticsDrone(drone, 1, 0, 10);
    }

    @Test
    public void executeTurnで動く() throws Exception {
        sut.executeTurn();
        assertThat(drone.getPoint().getX(), is(not(closeTo(0, 0.000001))));
        assertThat(drone.getPoint().getY(), is(not(closeTo(0, 0.000001))));
    }

}