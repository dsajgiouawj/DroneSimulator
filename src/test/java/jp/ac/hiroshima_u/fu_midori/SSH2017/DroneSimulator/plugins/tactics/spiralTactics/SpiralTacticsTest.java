package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.spiralTactics;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.DroneImpl;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.Victims;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

/**
 * SpiralTacticsのテスト
 *
 * @author 遠藤拓斗 on 2017/05/28.
 */
public class SpiralTacticsTest {
    private SpiralTactics sut;
    private final int NUM_DRONE = 10;
    private List<Drone> drones = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < NUM_DRONE; i++) {
            DroneImpl drone = new DroneImpl(10, new Victims(new ArrayList<>()));
            drone.nextTurn();
            drones.add(drone);
        }
        sut = new SpiralTactics(1, NUM_DRONE, 10, drones);
    }

    @Test
    public void executeTurnで各ドローンのexecuteTurnを呼び出す() throws Exception {
        sut.executeTurn();
        for (int i = 0; i < NUM_DRONE; i++) {
            Point2D actual = drones.get(i).getPoint();
            assertThat(actual, is(not(Point2D.ZERO)));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void コンストラクタでsearchRatioを一より大きくすると例外を送出() throws Exception {
        SpiralTactics spiralTactics = new SpiralTactics(1.000001, NUM_DRONE, 10, drones);
    }

    @Test(expected = IllegalArgumentException.class)
    public void コンストラクタでsearchRatioを零以下にすると例外を送出() throws Exception {
        SpiralTactics spiralTactics = new SpiralTactics(0, NUM_DRONE, 10, drones);
    }

}