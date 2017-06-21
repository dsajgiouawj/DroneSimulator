package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomAndCallNeighborsTactics;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.DroneImpl;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.CallingACertainNumberOfDrones;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.SelectCalleeMediator;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.spiralTactics.SpiralTacticsDrone;
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
 * RandomDroneのテスト
 *
 * @author 遠藤拓斗 on 2017/06/04.
 */
@RunWith(Enclosed.class)
public class RandomDroneTest {
    private static final int NUM_DRONE = 10;
    private static final int ID = 3;
    private static final double VIEW_RANGE_RADIUS = 20;
    private static final int TURN_INTERVAL = 30;
    private static final double LIMIT_OF_TURNING_ANGLE = 0.5;

    public static class 最初に螺旋探索を行う場合 {
        private RandomDrone sut;
        private final double SEARCH_RATIO = 0.5;
        private DroneImpl drone = new DroneImpl(VIEW_RANGE_RADIUS, new Victims(new ArrayList<>()));
        private DroneImpl droneSpy = spy(drone);
        private SelectCalleeMediator selectCalleeMediator = mock(CallingACertainNumberOfDrones.class);

        @Before
        public void setUp() throws Exception {
            sut = new RandomDrone(droneSpy, NUM_DRONE, ID, VIEW_RANGE_RADIUS, TURN_INTERVAL, LIMIT_OF_TURNING_ANGLE, selectCalleeMediator, true, SEARCH_RATIO);
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
            SpiralTacticsDrone spiralTacticsDrone = new SpiralTacticsDrone(droneForSpiral, NUM_DRONE, ID, VIEW_RANGE_RADIUS, SEARCH_RATIO);
            droneForSpiral.nextTurn();
            spiralTacticsDrone.executeTurn();
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

    public static class 最初に螺旋探索を行わない場合 {
        private RandomDrone sut;
        private DroneImpl drone = new DroneImpl(VIEW_RANGE_RADIUS, new Victims(new ArrayList<>()));
        private DroneImpl droneSpy = spy(drone);
        private SelectCalleeMediator selectCalleeMediator = mock(CallingACertainNumberOfDrones.class);

        @Before
        public void setUp() throws Exception {
            sut = new RandomDrone(droneSpy, NUM_DRONE, ID, VIEW_RANGE_RADIUS, TURN_INTERVAL, LIMIT_OF_TURNING_ANGLE, selectCalleeMediator, false, 0.5);
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
        private RandomDrone sut;
        private DroneImpl drone = new DroneImpl(VIEW_RANGE_RADIUS, new Victims(new ArrayList<>()));
        private DroneImpl droneSpy = spy(drone);
        private SelectCalleeMediator selectCalleeMediator = mock(CallingACertainNumberOfDrones.class);

        @Before
        public void setUp() throws Exception {
            sut = new RandomDrone(droneSpy, NUM_DRONE, ID, VIEW_RANGE_RADIUS, TURN_INTERVAL, LIMIT_OF_TURNING_ANGLE, selectCalleeMediator, false, 0.5);
            droneSpy.nextTurn();
            sut.setState(DroneState.beingCalled);
        }

        @Test
        public void executeTurnを実行すると目標に向かう() throws Exception {
            Point2D target = new Point2D(100, 200);
            sut.setTarget(target);
            sut.executeTurn();
            verify(droneSpy).goToPoint(target);
        }

        @Test
        public void 目標に達するとrandomWalkingへ() throws Exception {
            Point2D target = new Point2D(3, 2);
            sut.setTarget(target);
            sut.executeTurn();
            verify(droneSpy).goToPoint(target);
            verify(droneSpy).goStraight();
            assertThat(sut.getState(), is(DroneState.randomWalking));
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
}