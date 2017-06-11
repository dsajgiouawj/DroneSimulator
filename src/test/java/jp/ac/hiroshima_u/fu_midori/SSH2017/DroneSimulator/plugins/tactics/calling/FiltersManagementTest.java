package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

/**
 * FilterManagementのテスト
 *
 * @author 遠藤拓斗 on 2017/06/10.
 */
public class FiltersManagementTest {
    private FiltersManagement sut;
    private List<CallingFilter> filters = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        CallingFilter filterA = mock(CallingFilter.class);
        CallingFilter filterB = mock(CallingFilter.class);
        sut = new FiltersManagement(filterA, filterB);
        filters.add(filterA);
        filters.add(filterB);
        CallingFilter filterC = mock(CallingFilter.class);
        sut.addFilter(filterC);
        filters.add(filterC);
    }

    @Test
    public void before() throws Exception {
        sut.before();
        for (CallingFilter filter : filters) {
            verify(filter).before();
        }
    }

    @Test
    public void after() throws Exception {
        sut.after();
        for (CallingFilter filter : filters) {
            verify(filter).after();
        }
    }

    @Test
    public void informFinding() throws Exception {
        sut.informFinding(0, 1);
        for (CallingFilter filter : filters) {
            verify(filter).informFinding(0, 1);
        }
    }

    @Test
    public void informCalling() throws Exception {
        sut.informCalling(3);
        for (CallingFilter filter : filters) {
            verify(filter).informCalling(3);
        }
    }

    @Test
    public void informBeingCalled() throws Exception {
        sut.informBeingCalled(2);
        for (CallingFilter filter : filters) {
            verify(filter).informBeingCalled(2);
        }
    }

    @Test
    public void filter() throws Exception {
        IntStream stream = IntStream.range(0, 10);
        when(filters.get(0).filter(anyInt(), anyObject())).thenReturn(IntStream.range(0, 5));
        when(filters.get(1).filter(anyInt(), anyObject())).thenReturn(IntStream.range(0, 6));
        when(filters.get(2).filter(anyInt(), anyObject())).thenReturn(IntStream.range(0, 4));
        int[] actual = sut.filter(0, stream).toArray();
        for (CallingFilter filter : filters) {
            verify(filter).filter(anyInt(), anyObject());
        }
        int[] expected = {0, 1, 2, 3};
        assertThat(actual.length, is(expected.length));
        for (int i = 0; i < expected.length; i++) {
            assertThat(actual[i], is(expected[i]));
        }
    }

}