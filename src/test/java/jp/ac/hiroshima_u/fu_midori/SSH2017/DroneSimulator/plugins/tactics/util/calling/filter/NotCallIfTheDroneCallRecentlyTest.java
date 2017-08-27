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
 * CallIfTheFindingDroneHasNotCalledRecentlyのテスト
 *
 * @author 遠藤拓斗 on 2017/06/10.
 */
public class NotCallIfTheDroneCallRecentlyTest {
    private NotCallIfTheDroneCallRecently sut;
    private final int NUM_DRONE = 10;
    private final int THRESHOLD_TIME = 30;

    @Before
    public void setUp() throws Exception {
        List<Drone> drones = new ArrayList<>();
        for (int i = 0; i < NUM_DRONE; i++) {
            drones.add(null);
        }
        sut = new NotCallIfTheDroneCallRecently(THRESHOLD_TIME, drones);
    }

    @Test
    public void 発見したドローンが一度も呼び出しを行っていない場合除外しない() throws Exception {
        sut.informFinding(0, 1);
        sut.informBeingCalled(1);
        IntStream stream = IntStream.range(0, NUM_DRONE);
        int[] actual = sut.filter(0, stream).toArray();
        assertThat(actual.length, is(NUM_DRONE));
        for (int i = 0; i < NUM_DRONE; i++) {
            assertThat(actual[i], is(i));
        }
    }

    @Test
    public void 発見したドローンが最後に呼び出ししてからthreshold秒経過しているとき呼び出す() throws Exception {
        sut.informFinding(0, 1);
        sut.informBeingCalled(1);
        IntStream stream = IntStream.range(0, NUM_DRONE);
        sut.informCalling(0);
        for (int i = 0; i < THRESHOLD_TIME; i++) {
            sut.before();
            sut.after();
        }
        int[] actual = sut.filter(0, stream).toArray();
        assertThat(actual.length, is(NUM_DRONE));
        for (int i = 0; i < NUM_DRONE; i++) {
            assertThat(actual[i], is(i));
        }
    }

    @Test
    public void 発見したドローンが最後に呼び出ししてからthresholdマイナス一秒経過しているとき呼び出さない() throws Exception {
        sut.informFinding(0, 1);
        sut.informBeingCalled(1);
        IntStream stream = IntStream.range(0, NUM_DRONE);
        sut.informCalling(0);
        for (int i = 0; i < THRESHOLD_TIME - 1; i++) {
            sut.before();
            sut.after();
        }
        int[] actual = sut.filter(0, stream).toArray();
        assertThat(actual.length, is(0));
    }
}