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
 * CallingDronesWhichHaveNotFindRecentlyのテスト
 * Created by 遠藤拓斗 on 2017/06/10.
 */
public class RemoveRecentlyFindDronesTest {
    private RemoveRecentlyFindDrones sut;
    private final int NUM_DRONE = 10;
    private final int THRESHOLD_TIME = 30;

    @Before
    public void setUp() throws Exception {
        List<Drone> drones = new ArrayList<>();
        for (int i = 0; i < NUM_DRONE; i++) {
            drones.add(null);
        }
        sut = new RemoveRecentlyFindDrones(THRESHOLD_TIME, drones);
    }

    @Test
    public void 最近被災者を発見したドローンを除外() throws Exception {
        //time0
        sut.informFinding(0, 1);
        sut.before();
        sut.after();
        //time1
        sut.informFinding(1, 1);
        sut.informBeingCalled(2);
        sut.informCalling(3);
        for (int i = 0; i < THRESHOLD_TIME - 1; i++) {
            sut.before();
            sut.after();
        }
        //time threshold
        IntStream stream = IntStream.range(0, NUM_DRONE);
        assertThat(NUM_DRONE, is(10));
        int[] actual = sut.filter(3, stream).toArray();
        int[] expected = {0, 2, 3, 4, 5, 6, 7, 8, 9};
        assertThat(actual.length, is(9));
        for (int i = 0; i < expected.length; i++) {
            assertThat(actual[i], is(expected[i]));
        }
    }
}