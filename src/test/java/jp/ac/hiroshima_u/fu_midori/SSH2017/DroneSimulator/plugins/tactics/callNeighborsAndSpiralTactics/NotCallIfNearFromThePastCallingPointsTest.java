package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.callNeighborsAndSpiralTactics;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * CallIfNotNearFromThePastCallingPointsのテスト
 *
 * @author 遠藤拓斗 on 2017/06/11.
 */
public class NotCallIfNearFromThePastCallingPointsTest {
    private NotCallIfNearFromThePastCallingPoints sut;
    private final double THRESHOLD_DISTANCE = 100;
    private final int NUM_DRONE = 10;
    private List<Drone> drones = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < NUM_DRONE; i++) {
            drones.add(mock(Drone.class));
        }
        sut = new NotCallIfNearFromThePastCallingPoints(drones, THRESHOLD_DISTANCE);
    }

    @Test
    public void 一定距離未満なら呼び出さない() throws Exception {
        when(drones.get(0).getPoint()).thenReturn(new Point2D(100, 100));
        when(drones.get(1).getPoint()).thenReturn(new Point2D(170.7, 170.7));
        sut.informCalling(0);
        IntStream stream = IntStream.range(0, NUM_DRONE);
        int[] actual = sut.filter(1, stream).toArray();
        assertThat(actual.length, is(0));
    }

    @Test
    public void 一定距離以上なら呼び出す() throws Exception {
        when(drones.get(0).getPoint()).thenReturn(new Point2D(100, 100));
        when(drones.get(1).getPoint()).thenReturn(new Point2D(170.8, 170.8));
        sut.informCalling(0);
        IntStream stream = IntStream.range(0, NUM_DRONE);
        int[] actual = sut.filter(1, stream).toArray();
        assertThat(actual.length, is(NUM_DRONE));
        int[] expected = IntStream.range(0, NUM_DRONE).toArray();
        for (int i = 0; i < expected.length; i++) {
            assertThat(actual[i], is(expected[i]));
        }
    }

}