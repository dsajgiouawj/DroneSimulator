package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.filter;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * CallingDronesWhichHaveNeverBeenCalledのテスト
 *
 * @author 遠藤拓斗 on 2017/06/10.
 */
public class RemoveEverBeenCalledDronesTest {
    private RemoveEverBeenCalledDrones sut;
    private final int NUM_DRONE = 10;

    @Before
    public void setUp() throws Exception {
        List<Drone> drones = new ArrayList<>();
        for (int i = 0; i < NUM_DRONE; i++) {
            drones.add(null);
        }
        sut = new RemoveEverBeenCalledDrones(drones);
    }

    @Test
    public void 呼び出されたことのあるドローンは除外して呼び出されたことのないドローンは除外しない() throws Exception {
        sut.informBeingCalled(3);
        sut.informBeingCalled(5);
        sut.informCalling(2);
        sut.informFinding(1, 1);
        sut.informFinding(3, 2);
        assertThat(NUM_DRONE, is(10));
        IntStream stream = IntStream.range(0, NUM_DRONE);
        int[] actual = sut.filter(0, stream).toArray();
        int[] expected = {0, 1, 2, 4, 6, 7, 8, 9};
        assertThat(actual.length, is(NUM_DRONE - 2));
        for (int i = 0; i < NUM_DRONE - 2; i++) {
            assertThat(actual[i], is(expected[i]));
        }
    }

    @Test
    public void 呼び出されたことのあるドローンは時間が経過しても除外して呼び出されたことのないドローンは除外しない() throws Exception {
        sut.informBeingCalled(3);
        sut.informBeingCalled(5);
        sut.informCalling(2);
        sut.informFinding(1, 1);
        sut.informFinding(3, 2);
        assertThat(NUM_DRONE, is(10));
        for (int i = 0; i < 1000; i++) {
            sut.before();
            sut.after();
        }
        IntStream stream = IntStream.range(0, NUM_DRONE);
        int[] actual = sut.filter(0, stream).toArray();
        int[] expected = {0, 1, 2, 4, 6, 7, 8, 9};
        assertThat(actual.length, is(NUM_DRONE - 2));
        for (int i = 0; i < NUM_DRONE - 2; i++) {
            assertThat(actual[i], is(expected[i]));
        }
    }
}