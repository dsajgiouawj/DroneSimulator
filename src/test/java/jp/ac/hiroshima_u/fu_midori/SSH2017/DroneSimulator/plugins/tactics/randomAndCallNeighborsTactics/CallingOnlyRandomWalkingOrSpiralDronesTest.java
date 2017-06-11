package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.SelectCalleeMediator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;


/**
 * CallingOnlyRandomWalkingOrSpiralDronesのテスト
 *
 * @author 遠藤拓斗 on 2017/06/10.
 */
public class CallingOnlyRandomWalkingOrSpiralDronesTest {
    private CallingOnlyRandomWalkingOrSpiralDrones sut;
    private final int NUM_DRONE = 10;
    private List<RandomDrone> drones = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < NUM_DRONE; i++) {
            drones.add(new RandomDrone(mock(Drone.class), NUM_DRONE, i, 10, 10, 10, mock(SelectCalleeMediator.class), true, 0.5));
        }
        sut = new CallingOnlyRandomWalkingOrSpiralDrones(drones);
    }

    @Test
    public void ランダムウォークまたは螺線探索中のドローンは除外せずそれ以外を除外する() throws Exception {
        drones.get(0).setState(DroneState.randomWalking);
        drones.get(1).setState(DroneState.beingCalled);
        drones.get(2).setState(DroneState.spiral);
        drones.get(3).setState(DroneState.randomWalking);
        drones.get(4).setState(DroneState.beingCalled);
        drones.get(5).setState(DroneState.spiral);
        drones.get(6).setState(DroneState.randomWalking);
        drones.get(7).setState(DroneState.beingCalled);
        drones.get(8).setState(DroneState.spiral);
        drones.get(9).setState(DroneState.randomWalking);
        IntStream stream = IntStream.range(0, NUM_DRONE);
        int[] actual = sut.filter(0, stream).toArray();
        int[] expected = {0, 2, 3, 5, 6, 8, 9};
        assertThat(actual, is(expected));
    }

}