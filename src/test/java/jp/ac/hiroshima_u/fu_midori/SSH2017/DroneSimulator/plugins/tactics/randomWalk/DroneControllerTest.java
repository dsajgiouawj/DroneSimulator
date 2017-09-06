package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomWalk;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.DroneImpl;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling.CallingNearestDrones;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling.SelectCalleeMediator;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.Victims;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.*;

@RunWith(Enclosed.class)
public class DroneControllerTest {
    private static final int NUM_DRONE = 10;
    private static final int ID = 3;
    private static final double VIEW_RANGE_RADIUS = 20;
    private static final int TURN_INTERVAL = 30;

    private static final double LIMIT_OF_TURNING_ANGLE = 0.5;

    public static class 通常 {
        private DroneController sut;
        private DroneImpl drone = new DroneImpl(VIEW_RANGE_RADIUS, new Victims(new ArrayList<>()));
        private DroneImpl droneSpy = spy(drone);
        private SelectCalleeMediator selectCalleeMediator = mock(CallingNearestDrones.class);

        @Before
        public void setUp() throws Exception {
            sut = new DroneController(droneSpy, NUM_DRONE, ID, VIEW_RANGE_RADIUS, TURN_INTERVAL, LIMIT_OF_TURNING_ANGLE, selectCalleeMediator);
            droneSpy.nextTurn();
        }

        @Test
        public void 被災者を見つけるとcallerに知らせる() throws Exception {
            when(droneSpy.getNumOfFoundVictimsWhileLastMovement()).thenReturn(1);
            sut.executeTurn();
            verify(selectCalleeMediator).inform(ID, 1);
        }

        @Test
        public void executeTurnを実行するとturnInterval回に一回回転() throws Exception {
            for (int i = 0; i < TURN_INTERVAL * 10; i++) {
                sut.executeTurn();
                verify(droneSpy, times((i + 1) / TURN_INTERVAL)).turnClockWise(anyDouble());
                verify(droneSpy, times(i + 1)).goStraight();
            }
        }
    }

    public static class 他のドローンに向かっている場合 {
        private DroneController sut;
        private DroneImpl drone = new DroneImpl(VIEW_RANGE_RADIUS, new Victims(new ArrayList<>()));
        private DroneImpl droneSpy = spy(drone);
        private SelectCalleeMediator selectCalleeMediator = mock(CallingNearestDrones.class);
        private final Point2D TARGET = new Point2D(100, 200);

        @Before
        public void setUp() throws Exception {
            sut = new DroneController(droneSpy, NUM_DRONE, ID, VIEW_RANGE_RADIUS, TURN_INTERVAL, LIMIT_OF_TURNING_ANGLE, selectCalleeMediator);
            droneSpy.nextTurn();
            sut.called(TARGET);
        }

        @Test
        public void executeTurnを実行すると目標に向かう() throws Exception {
            sut.executeTurn();
            verify(droneSpy).goToPoint(TARGET);
        }
    }
}