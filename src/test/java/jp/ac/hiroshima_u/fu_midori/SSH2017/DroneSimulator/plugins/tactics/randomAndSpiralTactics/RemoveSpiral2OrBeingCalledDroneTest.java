package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndSpiralTactics;

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
 * FilterSpiral2OrBeingCalledDroneのテスト
 *
 * @author 遠藤拓斗 on 2017/06/11.
 */
public class RemoveSpiral2OrBeingCalledDroneTest {
    private RemoveSpiral2OrBeingCalledDrone sut;
    private List<DroneController> drones = new ArrayList<>();
    private final int NUM_DRONE = 8;

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < NUM_DRONE; i++) {
            drones.add(mock(DroneController.class));
        }
        sut = new RemoveSpiral2OrBeingCalledDrone(drones);
    }

    @Test
    public void spiral二またはbeingCalledをはじき他はそのまま() throws Exception {
        assertThat(NUM_DRONE, is(8));
        when(drones.get(0).getState()).thenReturn(DroneState.beingCalled);
        when(drones.get(1).getState()).thenReturn(DroneState.randomWalking);
        when(drones.get(2).getState()).thenReturn(DroneState.spiral);
        when(drones.get(3).getState()).thenReturn(DroneState.spiral2);
        when(drones.get(4).getState()).thenReturn(DroneState.beingCalled);
        when(drones.get(5).getState()).thenReturn(DroneState.randomWalking);
        when(drones.get(6).getState()).thenReturn(DroneState.spiral);
        when(drones.get(7).getState()).thenReturn(DroneState.spiral2);
        IntStream stream = IntStream.range(0, NUM_DRONE);
        int[] actual = sut.filter(0, stream).toArray();
        int[] expected = {1, 2, 5, 6};
        assertThat(actual.length, is(expected.length));
        for (int i = 0; i < expected.length; i++) {
            assertThat(actual[i], is(expected[i]));
        }
    }

}