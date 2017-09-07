package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndSpiralTactics;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.DroneImpl;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.ArchimedesSpiral.ArchimedeanSpiral;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling.CallingNearestDrones;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling.SelectCalleeMediator;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.Victims;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * DroneControllerのテスト
 *
 * @author 遠藤拓斗 on 2017/06/09.
 */
@RunWith(Enclosed.class)
public class DroneControllerTest {
    private static final int NUM_DRONE = 10;
    private static final int ID = 3;
    private static final double VIEW_RANGE_RADIUS = 20;
    private static final int TURN_INTERVAL = 30;
    private static final double LIMIT_OF_TURNING_ANGLE = 0.5;

    public static class 最初に螺旋探索を行う場合 {
        private DroneController sut;
        private final double SEARCH_RATIO = 0.5;
        private DroneImpl drone = new DroneImpl(VIEW_RANGE_RADIUS, new Victims(new ArrayList<>()));
        private DroneImpl droneSpy = spy(drone);
        private SelectCalleeMediator selectCalleeMediator = mock(CallingNearestDrones.class);

        @Before
        public void setUp() throws Exception {
            sut = new DroneController(droneSpy, NUM_DRONE, ID, VIEW_RANGE_RADIUS, TURN_INTERVAL, LIMIT_OF_TURNING_ANGLE, selectCalleeMediator, true, SEARCH_RATIO, 0.5, 10000);
            droneSpy.nextTurn();
        }

        @Test
        public void 初期状態でgetStateはspiralを返す() throws Exception {
            DroneState actual = sut.getState();
            assertThat(actual, is(DroneState.spiral));
        }

        @Test
        public void executeTurnを実行すると螺旋探索() throws Exception {
            sut.executeTurn();
            Point2D actual = sut.getPoint();
            DroneImpl droneForSpiral = new DroneImpl(VIEW_RANGE_RADIUS, new Victims(new ArrayList<>()));
            ArchimedeanSpiral spiralDrone = new ArchimedeanSpiral(droneForSpiral, NUM_DRONE, ID, VIEW_RANGE_RADIUS, SEARCH_RATIO);
            droneForSpiral.nextTurn();
            spiralDrone.executeTurn();
            Point2D expected = droneForSpiral.getPoint();
            assertThat(actual, is(expected));
        }

        @Test
        public void setStateで状態を変更できる() throws Exception {
            sut.setState(DroneState.beingCalled);
            assertThat(sut.getState(), is(DroneState.beingCalled));
        }

        @Test
        public void getIdでidを取得できる() throws Exception {
            assertThat(sut.getId(), is(ID));
        }

        @Test
        public void 被災者を見つけるとcallerに知らせるが状態は変化しない() throws Exception {
            when(droneSpy.getNumOfFoundVictimsWhileThisTurn()).thenReturn(1);
            sut.executeTurn();
            verify(selectCalleeMediator).inform(ID, 1);
            assertThat(sut.getState(), is(DroneState.spiral));
        }
    }

    public static class ランダムウォーク {
        private DroneController sut;
        private DroneImpl drone = new DroneImpl(VIEW_RANGE_RADIUS, new Victims(new ArrayList<>()));
        private DroneImpl droneSpy = spy(drone);
        private SelectCalleeMediator selectCalleeMediator = mock(CallingNearestDrones.class);

        @Before
        public void setUp() throws Exception {
            sut = new DroneController(droneSpy, NUM_DRONE, ID, VIEW_RANGE_RADIUS, TURN_INTERVAL, LIMIT_OF_TURNING_ANGLE, selectCalleeMediator, false, 0.5, 0.5, 10000);
            droneSpy.nextTurn();
        }

        @Test
        public void 初期状態でgetStateはrandomWalkingを返す() throws Exception {
            DroneState actual = sut.getState();
            assertThat(actual, is(DroneState.randomWalking));
        }

        @Test
        public void executeTurnを実行すると回転して直進() throws Exception {
            sut.executeTurn();
            verify(droneSpy).turnClockWise(anyDouble());
            verify(droneSpy).goStraight();
        }

        @Test
        public void setStateで状態を変更できる() throws Exception {
            sut.setState(DroneState.beingCalled);
            assertThat(sut.getState(), is(DroneState.beingCalled));
        }

        @Test
        public void getIdでidを取得できる() throws Exception {
            assertThat(sut.getId(), is(ID));
        }

        @Test
        public void 被災者を見つけるとcallerに知らせるが状態は変化しない() throws Exception {
            when(droneSpy.getNumOfFoundVictimsWhileLastMovement()).thenReturn(1);
            sut.executeTurn();
            verify(selectCalleeMediator).inform(ID, 1);
            assertThat(sut.getState(), is(DroneState.randomWalking));
        }

        @Test
        public void executeTurnを実行するとturnInterval回に一回回転() throws Exception {
            for (int i = 0; i < TURN_INTERVAL * 10; i++) {
                sut.executeTurn();
                verify(droneSpy, times(i / TURN_INTERVAL + 1)).turnClockWise(anyDouble());
            }
        }
    }

    public static class 他のドローンに向かっている場合 {
        private DroneController sut;
        private DroneImpl drone = new DroneImpl(VIEW_RANGE_RADIUS, new Victims(new ArrayList<>()));
        private DroneImpl droneSpy = spy(drone);
        private SelectCalleeMediator selectCalleeMediator = mock(CallingNearestDrones.class);

        @Before
        public void setUp() throws Exception {
            sut = new DroneController(droneSpy, NUM_DRONE, ID, VIEW_RANGE_RADIUS, TURN_INTERVAL, LIMIT_OF_TURNING_ANGLE, selectCalleeMediator, false, 0.5, 0.5, 10000);
            droneSpy.nextTurn();
            sut.setState(DroneState.beingCalled);
            sut.setNextSettings(0, 5);
        }

        @Test
        public void executeTurnを実行すると目標に向かう() throws Exception {
            Point2D target = new Point2D(100, 200);
            sut.setTarget(target);
            sut.executeTurn();
            verify(droneSpy).goToPoint(target);
        }

        @Test
        public void 目標に達するとspiral2へ() throws Exception {
            Point2D target = new Point2D(3, 2);
            sut.setTarget(target);
            sut.executeTurn();
            verify(droneSpy).goToPoint(target);
            assertThat(sut.getState(), is(DroneState.spiral2));
        }

        @Test
        public void setStateで状態を変更できる() throws Exception {
            sut.setState(DroneState.randomWalking);
            assertThat(sut.getState(), is(DroneState.randomWalking));
        }

        @Test
        public void 被災者を見つけるとcallerに知らせるが状態は変化しない() throws Exception {
            when(droneSpy.getNumOfFoundVictimsWhileLastMovement()).thenReturn(1);
            sut.setTarget(new Point2D(100, 200));
            sut.executeTurn();
            verify(selectCalleeMediator).inform(ID, 1);
            assertThat(sut.getState(), is(DroneState.beingCalled));
        }
    }

    public static class spiral2の時 {
        private DroneController sut;
        private final Point2D spiral2Center = new Point2D(100, 200);
        private DroneImpl drone = new DroneImpl(VIEW_RANGE_RADIUS, new Victims(new ArrayList<>()), spiral2Center);
        private DroneImpl droneSpy = spy(drone);
        private SelectCalleeMediator selectCalleeMediator = mock(CallingNearestDrones.class);
        private final double SEARCH_RATIO2 = 1.0;
        private final int spiral2ID = 2;
        private final int spiral2NumDrone = 5;
        private final int timeToContinueSpiral = 500;

        @Before
        public void setUp() throws Exception {
            sut = new DroneController(droneSpy, NUM_DRONE, ID, VIEW_RANGE_RADIUS, TURN_INTERVAL, LIMIT_OF_TURNING_ANGLE, selectCalleeMediator, false, 0.5, SEARCH_RATIO2, timeToContinueSpiral);
            sut.setNextSettings(spiral2ID, spiral2NumDrone);
            sut.setState(DroneState.spiral2);
            droneSpy.nextTurn();
        }

        @Test
        public void executeTurnを実行すると螺線探索() throws Exception {
            sut.executeTurn();
            Point2D actual = sut.getPoint();
            Point2D expected = ArchimedeanSpiral.simulate(spiral2NumDrone, spiral2ID, VIEW_RANGE_RADIUS, SEARCH_RATIO2, spiral2Center, 1);
            assertThat(actual, is(expected));
        }

        @Test
        public void 被災者を一定時間発見しないとランダムに戻る() throws Exception {
            for (int i = 0; i < timeToContinueSpiral - 1; i++) {
                sut.executeTurn();
                assertThat(sut.getState(), is(DroneState.spiral2));
            }
            when(droneSpy.getNumOfFoundVictimsWhileThisTurn()).thenReturn(1);
            sut.executeTurn();
            assertThat(sut.getState(), is(DroneState.spiral2));
            when(droneSpy.getNumOfFoundVictimsWhileThisTurn()).thenReturn(0);
            for (int i = 0; i < timeToContinueSpiral - 1; i++) {
                sut.executeTurn();
                assertThat(sut.getState(), is(DroneState.spiral2));
            }
            sut.executeTurn();
            assertThat(sut.getState(), is(DroneState.randomWalking));
        }

        @Test
        public void setStateで状態を変更できる() throws Exception {
            sut.setState(DroneState.randomWalking);
            assertThat(sut.getState(), is(DroneState.randomWalking));
        }

        @Test
        public void 被災者を見つけてもcallerに知らせない() throws Exception {
            when(droneSpy.getNumOfFoundVictimsWhileLastMovement()).thenReturn(1);
            sut.executeTurn();
            verify(selectCalleeMediator, never()).inform(anyInt(), anyInt());
            assertThat(sut.getState(), is(DroneState.spiral2));
        }
    }

}