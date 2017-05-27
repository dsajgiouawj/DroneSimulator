package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.Victim;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

/**
 * Cameraのテスト
 * Created by 遠藤拓斗 on 2017/05/14.
 */
public class CameraTest {
    private Camera sut;
    private ArrayList<Victim> victims;

    @Before
    public void setUp() throws Exception {
        victims = new ArrayList<Victim>();
        victims.add(new Victim(new Point2D(-10, 9)));
        victims.add(new Victim(new Point2D(3, 0)));
        victims.add(new Victim(new Point2D(5, 2)));
        victims.add(new Victim(new Point2D(5, 10)));
        sut = new Camera(1, victims);
    }

    @Test
    public void findPeopleで人を発見できる() throws Exception {
        sut.findPeople(new Point2D(-9, 8), new Point2D(-9, 10));
        assertThat(victims.get(0).isFound(), is(true));
        assertThat(victims.get(1).isFound(), is(false));
        assertThat(victims.get(2).isFound(), is(false));
        assertThat(victims.get(3).isFound(), is(false));
    }

    @Test
    public void findPeopleで人を発見できない場合() throws Exception {
        sut.findPeople(new Point2D(-8.9, 8), new Point2D(-8.9, 10));
        assertThat(victims.get(0).isFound(), is(false));
        assertThat(victims.get(1).isFound(), is(false));
        assertThat(victims.get(2).isFound(), is(false));
        assertThat(victims.get(3).isFound(), is(false));
    }

    @Test
    public void distanceのでpointから線分におろした垂線の足が端点() throws Exception {
        Method distance = Camera.class.getDeclaredMethod("distance", Point2D.class, Point2D.class, Point2D.class);
        distance.setAccessible(true);
        double actual = (Double) distance.invoke(sut, new Point2D(1, 1), new Point2D(2, 2), new Point2D(2, 0));
        double expected = Math.sqrt(2);
        assertThat(actual, is(closeTo(expected, 0.000001)));
    }

    @Test
    public void distanceのでpointから線分におろした垂線の足が線分上() throws Exception {
        Method distance = Camera.class.getDeclaredMethod("distance", Point2D.class, Point2D.class, Point2D.class);
        distance.setAccessible(true);
        double actual = (Double) distance.invoke(sut, new Point2D(1, 1), new Point2D(3, 3), new Point2D(3, 1));
        double expected = Math.sqrt(2);
        assertThat(actual, is(closeTo(expected, 0.000001)));
    }

    @Test
    public void distanceで同一直線状でpointは線分の中にない() throws Exception {
        Method distance = Camera.class.getDeclaredMethod("distance", Point2D.class, Point2D.class, Point2D.class);
        distance.setAccessible(true);
        double actual = (Double) distance.invoke(sut, new Point2D(1, 1), new Point2D(2, 2), new Point2D(4, 4));
        double expected = 2 * Math.sqrt(2);
        assertThat(actual, is(closeTo(expected, 0.000001)));
    }

    @Test
    public void distanceで同一直線状でpointは線分の中にある() throws Exception {
        Method distance = Camera.class.getDeclaredMethod("distance", Point2D.class, Point2D.class, Point2D.class);
        distance.setAccessible(true);
        double actual = (Double) distance.invoke(sut, new Point2D(1, 1), new Point2D(2, 2), new Point2D(1.5, 1.5));
        double expected = 0;
        assertThat(actual, is(closeTo(expected, 0.000001)));
    }

    @Test
    public void distanceでpointはpoint0に近い() throws Exception {
        Method distance = Camera.class.getDeclaredMethod("distance", Point2D.class, Point2D.class, Point2D.class);
        distance.setAccessible(true);
        double actual = (Double) distance.invoke(sut, new Point2D(1, 1), new Point2D(2, 2), new Point2D(1, 0));
        double expected = 1;
        assertThat(actual, is(closeTo(expected, 0.000001)));
    }

    @Test
    public void distanceでpointはpoint1に近い() throws Exception {
        Method distance = Camera.class.getDeclaredMethod("distance", Point2D.class, Point2D.class, Point2D.class);
        distance.setAccessible(true);
        double actual = (Double) distance.invoke(sut, new Point2D(1, 1), new Point2D(2, 2), new Point2D(3, 2));
        double expected = 1;
        assertThat(actual, is(closeTo(expected, 0.000001)));
    }
}