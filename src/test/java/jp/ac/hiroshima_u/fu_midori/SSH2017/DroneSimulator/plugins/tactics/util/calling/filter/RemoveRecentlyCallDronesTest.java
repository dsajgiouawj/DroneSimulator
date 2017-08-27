package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling.filter;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * CallingDronesWhichHaveNotCalledRecentlyのテスト
 *
 * @author 遠藤拓斗 on 2017/06/10.
 */
public class RemoveRecentlyCallDronesTest {
    private RemoveRecentlyCallDrones sut;
    private final int NUM_DRONE = 10;
    private final int THRESHOLD_TIME = 30;

    @Before
    public void setUp() throws Exception {
        List<Drone> drones = new ArrayList<>();
        for (int i = 0; i < NUM_DRONE; i++) {
            drones.add(null);
        }
        sut = new RemoveRecentlyCallDrones(THRESHOLD_TIME, drones);
    }

    @Test
    public void 最近呼び出したドローンを除外() throws Exception {
        //time0
        sut.informCalling(0);
        sut.before();
        sut.after();
        //time1
        sut.informCalling(1);
        sut.informCalling(2);
        sut.informBeingCalled(3);
        sut.informFinding(4, 1);
        for (int i = 0; i < THRESHOLD_TIME - 1; i++) {
            sut.before();
            sut.after();
        }
        //time threshold
        IntStream stream = IntStream.range(0, NUM_DRONE);
        int[] actual = sut.filter(3, stream).toArray();
        assertThat(actual.length, is(NUM_DRONE - 2));
        assertThat(NUM_DRONE, is(10));
        int[] expected = {0, 3, 4, 5, 6, 7, 8, 9};
        for (int i = 0; i < expected.length; i++) {
            assertThat(actual[i], is(expected[i]));
        }
    }

}