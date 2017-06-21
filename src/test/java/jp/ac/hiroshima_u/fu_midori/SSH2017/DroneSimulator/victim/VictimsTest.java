package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim;

import javafx.geometry.Point2D;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

/**
 * Victimsのテスト
 *
 * @author 遠藤拓斗 on 2017/06/21.
 */
public class VictimsTest {
    @Test
    public void population() throws Exception {
    }

    @Test
    public void foundPopulation() throws Exception {
    }

    @Test
    public void findPeople() throws Exception {
    }

    @Test
    public void getViewableVictims() throws Exception {
    }

    private Victims sut;
    private List<Point2D> points = new ArrayList<>();
    private final double VIEW_RANGE_RADIUS = 1;

    @Before
    public void setUp() throws Exception {
        points.add(new Point2D(-10, 9));
        points.add(new Point2D(3, 0));
        points.add(new Point2D(5, 2));
        points.add(new Point2D(5, 10));
        sut = new Victims(points);
    }

    @Test
    public void getNumOfFoundVictimsWhileLastMovementで発見人数を取得できる() throws Exception {
        int actual = sut.findPeople(new Point2D(-9, 8), new Point2D(-9, 10), VIEW_RANGE_RADIUS);
        int expected = 1;
        assertThat(actual, is(expected));
        assertThat(sut.foundPopulation(), is(1));
        assertThat(sut.population(), is(4));
        actual = sut.findPeople(new Point2D(-9, 8), new Point2D(-9, 10), VIEW_RANGE_RADIUS);
        expected = 0;
        assertThat(actual, is(expected));
        assertThat(sut.foundPopulation(), is(1));
        assertThat(sut.population(), is(4));
    }

    @Test
    public void findPeopleで人を発見できる() throws Exception {
        sut.findPeople(new Point2D(-9, 8), new Point2D(-9, 10), VIEW_RANGE_RADIUS);
        List<ViewableVictim> vs = sut.getViewableVictims();
        assertThat(vs.get(0).isFound(), is(true));
        assertThat(vs.get(1).isFound(), is(false));
        assertThat(vs.get(2).isFound(), is(false));
        assertThat(vs.get(3).isFound(), is(false));
    }

    @Test
    public void findPeopleで人を発見できない場合() throws Exception {
        sut.findPeople(new Point2D(-8.9, 8), new Point2D(-8.9, 10), VIEW_RANGE_RADIUS);
        List<ViewableVictim> vs = sut.getViewableVictims();
        assertThat(vs.get(0).isFound(), is(false));
        assertThat(vs.get(1).isFound(), is(false));
        assertThat(vs.get(2).isFound(), is(false));
        assertThat(vs.get(3).isFound(), is(false));
    }

    @Test
    public void distanceのでpointから線分におろした垂線の足が端点() throws Exception {
        Method distance = Victims.class.getDeclaredMethod("distance", Point2D.class, Point2D.class, Point2D.class);
        distance.setAccessible(true);
        double actual = (Double) distance.invoke(sut, new Point2D(1, 1), new Point2D(2, 2), new Point2D(2, 0));
        double expected = Math.sqrt(2);
        assertThat(actual, is(closeTo(expected, 0.000001)));
    }

    @Test
    public void distanceのでpointから線分におろした垂線の足が線分上() throws Exception {
        Method distance = Victims.class.getDeclaredMethod("distance", Point2D.class, Point2D.class, Point2D.class);
        distance.setAccessible(true);
        double actual = (Double) distance.invoke(sut, new Point2D(1, 1), new Point2D(3, 3), new Point2D(3, 1));
        double expected = Math.sqrt(2);
        assertThat(actual, is(closeTo(expected, 0.000001)));
    }

    @Test
    public void distanceで同一直線状でpointは線分の中にない() throws Exception {
        Method distance = Victims.class.getDeclaredMethod("distance", Point2D.class, Point2D.class, Point2D.class);
        distance.setAccessible(true);
        double actual = (Double) distance.invoke(sut, new Point2D(1, 1), new Point2D(2, 2), new Point2D(4, 4));
        double expected = 2 * Math.sqrt(2);
        assertThat(actual, is(closeTo(expected, 0.000001)));
    }

    @Test
    public void distanceで同一直線状でpointは線分の中にある() throws Exception {
        Method distance = Victims.class.getDeclaredMethod("distance", Point2D.class, Point2D.class, Point2D.class);
        distance.setAccessible(true);
        double actual = (Double) distance.invoke(sut, new Point2D(1, 1), new Point2D(2, 2), new Point2D(1.5, 1.5));
        double expected = 0;
        assertThat(actual, is(closeTo(expected, 0.000001)));
    }

    @Test
    public void distanceでpointはpoint0に近い() throws Exception {
        Method distance = Victims.class.getDeclaredMethod("distance", Point2D.class, Point2D.class, Point2D.class);
        distance.setAccessible(true);
        double actual = (Double) distance.invoke(sut, new Point2D(1, 1), new Point2D(2, 2), new Point2D(1, 0));
        double expected = 1;
        assertThat(actual, is(closeTo(expected, 0.000001)));
    }

    @Test
    public void distanceでpointはpoint1に近い() throws Exception {
        Method distance = Victims.class.getDeclaredMethod("distance", Point2D.class, Point2D.class, Point2D.class);
        distance.setAccessible(true);
        double actual = (Double) distance.invoke(sut, new Point2D(1, 1), new Point2D(2, 2), new Point2D(3, 2));
        double expected = 1;
        assertThat(actual, is(closeTo(expected, 0.000001)));
    }

}