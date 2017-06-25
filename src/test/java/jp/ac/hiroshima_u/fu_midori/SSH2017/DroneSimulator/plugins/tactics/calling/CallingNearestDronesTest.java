package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * CallingACertainNumberOfDronesのテスト
 *
 * @author 遠藤拓斗 on 2017/06/10.
 */
public class CallingNearestDronesTest {
    private CallingNearestDrones sut;
    private List<Drone> drones = new ArrayList<>();
    private final int NUM_DRONE = 10;
    private final int CERTAIN_NUMBER = 3;
    private FiltersManagement fm = spy(new FiltersManagement());
    private ICaller caller = mock(ICaller.class);

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < NUM_DRONE; i++) {
            Drone drone = mock(Drone.class);
            when(drone.getPoint()).thenReturn(new Point2D(i * 10, i * 10));
            drones.add(drone);
        }
        sut = new CallingNearestDrones(drones, caller, CERTAIN_NUMBER, fm);
    }

    @Test
    public void informするとfmのinformCallingとinformBeingCalledとcallerのcallを呼ぶ() throws Exception {
        ArgumentCaptor<int[]> captor = ArgumentCaptor.forClass(int[].class);
        sut.inform(0, 1);
        verify(fm).informCalling(0);
        verify(fm).informBeingCalled(0);
        verify(fm).informBeingCalled(1);
        verify(fm).informBeingCalled(2);
        for (int i = 4; i < NUM_DRONE; i++) {
            verify(fm, never()).informBeingCalled(i);
        }
        verify(caller).call(anyInt(), captor.capture());
        assertThat(captor.getValue().length, is(CERTAIN_NUMBER));
        int[] expected = {2, 1, 0};//遠いほうから順番に
        assertThat(captor.getValue(), is(expected));
    }

    @Test
    public void filterではじく() throws Exception {
        ArgumentCaptor<int[]> captor = ArgumentCaptor.forClass(int[].class);
        when(fm.filter(anyInt(), any())).thenReturn(IntStream.of(0, 2, 4, 6, 8));
        sut.inform(0, 1);
        verify(fm).informCalling(0);
        verify(fm).informBeingCalled(0);
        verify(fm).informBeingCalled(2);
        verify(fm).informBeingCalled(4);
        for (int i = 1; i < NUM_DRONE; i += 2) {
            verify(fm, never()).informBeingCalled(i);
        }
        verify(caller).call(anyInt(), captor.capture());
        assertThat(captor.getValue().length, is(CERTAIN_NUMBER));
        int[] expected = {4, 2, 0};//遠いほうから順番に自身も含む
        assertThat(captor.getValue(), is(expected));
    }

    @Test
    public void beforeでfmのbeforeを呼ぶ() throws Exception {
        sut.before();
        verify(fm).before();
    }

    @Test
    public void afterでfmのafterを呼ぶ() throws Exception {
        sut.after();
        verify(fm).after();
    }

}