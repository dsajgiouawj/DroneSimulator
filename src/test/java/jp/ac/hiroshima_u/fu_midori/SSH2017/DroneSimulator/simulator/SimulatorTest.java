package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.simulator;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.VieableDrone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.ITactics;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.TacticsUIFrontend;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.Victims;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.ViewableVictim;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placer.VictimsPlacer;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placer.VictimsPlacerUIFrontend;
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
    private ITactics ITactics = mock(ITactics.class);
    private TacticsUIFrontend tacticsUIFrontend = mock(TacticsUIFrontend.class);
    private VictimsPlacer victimsPlacer = mock(VictimsPlacer.class);
    private VictimsPlacerUIFrontend victimsPlacerUIFrontend = mock(VictimsPlacerUIFrontend.class);
    private final int NUM_DRONE = 5;
    private final int POPULATION = 20;
    private final int LIMIT_TIME = 100;
    private final double VIEW_RANGE_RADIUS = 10;

    @Before
    public void setUp() throws Exception {
        when(tacticsUIFrontend.getTactics(anyInt(), anyDouble(), anyInt(), any())).thenReturn(ITactics);
        when(victimsPlacerUIFrontend.getVictimsPlacer()).thenReturn(victimsPlacer);
        List<Point2D> points = new ArrayList<>();
        for (int i = 0; i < POPULATION; i++) {
            points.add(new Point2D(i, i));
        }
        Victims victims = new Victims(points);
        when(victimsPlacer.placeVictims(POPULATION)).thenReturn(victims);
        sut = new Simulator(tacticsUIFrontend, LIMIT_TIME, NUM_DRONE, VIEW_RANGE_RADIUS, POPULATION, victimsPlacerUIFrontend);
    }

    @Test
    public void nextTurnで1ターン実行() throws Exception {
        sut.nextTurn();
        verify(ITactics).executeTurn();
    }

    @Test
    public void runThroughで最後まで実行() throws Exception {
        sut.runThrough();
        verify(ITactics, times(LIMIT_TIME)).executeTurn();
    }

    @Test
    public void nextTurnで1ターン実行後runThroughで最後まで実行() throws Exception {
        sut.nextTurn();
        sut.runThrough();
        verify(ITactics, times(LIMIT_TIME)).executeTurn();
    }

    @Test
    public void nextTurnでもう実行できないときは実行しない() throws Exception {
        for (int i = 0; i < LIMIT_TIME + 100; i++) {
            sut.nextTurn();
        }
        verify(ITactics, times(LIMIT_TIME)).executeTurn();
    }

    @Test
    public void getDronesでドローンを取得できる() throws Exception {
        List<VieableDrone> drones = sut.getVieableDrones();
        assertThat(drones.size(), is(NUM_DRONE));
    }

    @Test
    public void getVictimsで被災者を取得できる() throws Exception {
        List<ViewableVictim> victims = sut.getVieableVictims();
        assertThat(victims.size(), is(POPULATION));
    }

}