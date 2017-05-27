package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.simulator;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.DroneGUIInterface;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.Tactics;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.Victim;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placing.PlacingVictims;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placing.VictimGUIInterface;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.*;

/**
 * Simulatorのテスト
 * Created by 遠藤拓斗 on 2017/05/18.
 */
public class SimulatorTest {
    private Simulator sut;
    private Tactics tactics = mock(Tactics.class);
    private PlacingVictims placingVictims = mock(PlacingVictims.class);
    private final int NUM_DRONE = 5;
    private final int POPULATION = 20;
    private final int LIMIT_TIME = 100;
    private final double VIEW_RANGE_RADIUS = 10;

    @Before
    public void setUp() throws Exception {
        List<Victim> victims = new ArrayList<>();
        for (int i = 0; i < POPULATION; i++) {
            victims.add(new Victim(new Point2D(0, 0)));
        }
        when(placingVictims.placeVictims(POPULATION)).thenReturn(victims);
        sut = new Simulator(tactics, placingVictims, NUM_DRONE, POPULATION, LIMIT_TIME, VIEW_RANGE_RADIUS);
        verify(tactics).setDrones(anyListOf(Drone.class));
    }

    @Test
    public void nextTurnで1ターン実行() throws Exception {
        sut.nextTurn();
        verify(tactics).executeTurn();
    }

    @Test
    public void runThroughで最後まで実行() throws Exception {
        sut.runThrough();
        verify(tactics, times(LIMIT_TIME)).executeTurn();
    }

    @Test
    public void nextTurnで1ターン実行後runThroughで最後まで実行() throws Exception {
        sut.nextTurn();
        sut.runThrough();
        verify(tactics, times(LIMIT_TIME)).executeTurn();
    }

    @Test
    public void nextTurnでもう実行できないときは実行しない() throws Exception {
        for (int i = 0; i < LIMIT_TIME + 100; i++) {
            sut.nextTurn();
        }
        verify(tactics, times(LIMIT_TIME)).executeTurn();
    }

    @Test
    public void getDronesでドローンを取得できる() throws Exception {
        List<DroneGUIInterface> drones = sut.getDrones();
        assertThat(drones.size(), is(NUM_DRONE));
    }

    @Test
    public void getVictimsで被災者を取得できる() throws Exception {
        List<VictimGUIInterface> victims = sut.getVictims();
        assertThat(victims.size(), is(POPULATION));
    }

}