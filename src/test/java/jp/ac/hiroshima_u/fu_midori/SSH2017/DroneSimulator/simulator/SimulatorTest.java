package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.simulator;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.DroneGUIInterface;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.Tactics;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.TacticsUI;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.Victims;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.ViewableVictim;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placing.PlacingVictims;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placing.PlacingVictimsUI;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Simulatorのテスト
 * Created by 遠藤拓斗 on 2017/05/18.
 */
public class SimulatorTest {
    private Simulator sut;
    private Tactics tactics = mock(Tactics.class);
    private TacticsUI tacticsUI = mock(TacticsUI.class);
    private PlacingVictims placingVictims = mock(PlacingVictims.class);
    private PlacingVictimsUI placingVictimsUI = mock(PlacingVictimsUI.class);
    private final int NUM_DRONE = 5;
    private final int POPULATION = 20;
    private final int LIMIT_TIME = 100;
    private final double VIEW_RANGE_RADIUS = 10;

    @Before
    public void setUp() throws Exception {
        when(tacticsUI.getTactics(anyInt(), anyDouble(), anyInt(), any())).thenReturn(tactics);
        when(placingVictimsUI.getPlacingVictims()).thenReturn(placingVictims);
        List<Point2D> points = new ArrayList<>();
        for (int i = 0; i < POPULATION; i++) {
            points.add(new Point2D(i, i));
        }
        Victims victims = new Victims(points);
        when(placingVictims.placeVictims(POPULATION)).thenReturn(victims);
        sut = new Simulator(tacticsUI, LIMIT_TIME, NUM_DRONE, VIEW_RANGE_RADIUS, POPULATION, placingVictimsUI);
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
        List<DroneGUIInterface> drones = sut.getDronesGUI();
        assertThat(drones.size(), is(NUM_DRONE));
    }

    @Test
    public void getVictimsで被災者を取得できる() throws Exception {
        List<ViewableVictim> victims = sut.getVictimsGUI();
        assertThat(victims.size(), is(POPULATION));
    }

}