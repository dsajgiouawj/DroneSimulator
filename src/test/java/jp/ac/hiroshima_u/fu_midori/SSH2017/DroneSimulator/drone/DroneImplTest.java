package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;


/**
 * DroneImplのテスト
 *
 * @author 遠藤拓斗 on 2017/05/10.
 */
public class DroneImplTest {
    private DroneImpl sut;

    @Before
    public void setUp() {
        sut = new DroneImpl(30, new ArrayList<>());
        sut.nextTurn();
    }

    @Test
    public void goStraightで最大距離移動できる() throws Exception {
        sut.setTheta(Math.PI / 3);
        sut.goStraight();
        Point2D actual = sut.getPoint();
        Point2D expected = new Point2D(0.5 * 16, Math.sqrt(3) / 2 * 16);
        assertThat(actual.getX(), is(closeTo(expected.getX(), 0.000001)));
        assertThat(actual.getY(), is(closeTo(expected.getY(), 0.000001)));
        //point0は(0,0)
        assertThat(sut.getPoint0(), is(new Point2D(0, 0)));
        //もう動けない
        assertThat(sut.canMove(), is(false));
    }

    @Test
    public void goStraightで距離を指定して移動できる() throws Exception {
        sut.setTheta(Math.PI / 3);
        sut.goStraight(1);
        Point2D actual = sut.getPoint();
        Point2D expected = new Point2D(0.5, Math.sqrt(3) / 2);
        assertThat(actual.getX(), is(closeTo(expected.getX(), 0.000001)));
        assertThat(actual.getY(), is(closeTo(expected.getY(), 0.000001)));
        //point0は(0,0)
        assertThat(sut.getPoint0(), is(new Point2D(0, 0)));
        //まだ動ける
        assertThat(sut.canMove(), is(true));
        //最大まで動く
        sut.goStraight();
        Point2D actual2 = sut.getPoint();
        expected = new Point2D(0.5 * 16, Math.sqrt(3) / 2 * 16);
        assertThat(actual2.getX(), is(closeTo(expected.getX(), 0.000001)));
        assertThat(actual2.getY(), is(closeTo(expected.getY(), 0.000001)));
        //もう動けない
        assertThat(sut.canMove(), is(false));
        //point0は前回の位置
        assertThat(sut.getPoint0(), is(actual));
    }

    @Test
    public void goStraightで最大距離以上を指定しても最大距離しか進まない() throws Exception {
        sut.setTheta(Math.PI / 3);
        sut.goStraight(20);
        Point2D actual = sut.getPoint();
        Point2D expected = new Point2D(0.5 * 16, Math.sqrt(3) / 2 * 16);
        assertThat(actual.getX(), is(closeTo(expected.getX(), 0.000001)));
        assertThat(actual.getY(), is(closeTo(expected.getY(), 0.000001)));
        //point0は(0,0)
        assertThat(sut.getPoint0(), is(new Point2D(0, 0)));
        //もう動けない
        assertThat(sut.canMove(), is(false));
    }

    @Test
    public void goTheDirectionで最大距離移動できる() throws Exception {
        Point2D direction = new Point2D(1, Math.sqrt(3)); //PI / 3
        sut.goTheDirection(direction);
        Point2D actual = sut.getPoint();
        Point2D expected = new Point2D(0.5 * 16, Math.sqrt(3) / 2 * 16);
        assertThat(actual.getX(), is(closeTo(expected.getX(), 0.000001)));
        assertThat(actual.getY(), is(closeTo(expected.getY(), 0.000001)));
        //point0は(0,0)
        assertThat(sut.getPoint0(), is(new Point2D(0, 0)));
        //もう動けない
        assertThat(sut.canMove(), is(false));
    }

    @Test
    public void goTheDirectionで距離を指定して移動できる() throws Exception {
        Point2D direction = new Point2D(1, Math.sqrt(3)); //PI / 3
        sut.goTheDirection(direction, 1);
        Point2D actual = sut.getPoint();
        Point2D expected = new Point2D(0.5, Math.sqrt(3) / 2);
        assertThat(actual.getX(), is(closeTo(expected.getX(), 0.000001)));
        assertThat(actual.getY(), is(closeTo(expected.getY(), 0.000001)));
        //まだ動ける
        assertThat(sut.canMove(), is(true));
        //最大まで動く
        sut.goTheDirection(direction);
        Point2D actual2 = sut.getPoint();
        expected = new Point2D(0.5 * 16, Math.sqrt(3) / 2 * 16);
        assertThat(actual2.getX(), is(closeTo(expected.getX(), 0.000001)));
        assertThat(actual2.getY(), is(closeTo(expected.getY(), 0.000001)));
        //point0は前回の位置
        assertThat(sut.getPoint0(), is(actual));
        //もう動けない
        assertThat(sut.canMove(), is(false));
    }

    @Test
    public void goTheDirectionで方向ベクトルに零ベクトルを指定しても何もしない() throws Exception {
        sut.goTheDirection(Point2D.ZERO);
        Point2D actual = sut.getPoint();
        Point2D expected = new Point2D(0, 0);
        assertThat(actual, is(expected));
    }

    @Test
    public void goToPointで指定された点に移動できる() throws Exception {
        Point2D target = new Point2D(3 * 16, 3 * 16);//距離は3√2=4.242...なので4回までは最大距離動く
        Point2D prev = sut.getPoint();
        for (int i = 0; i < 4; i++) {
            sut.goToPoint(target);
            //最初の4回はもう動けない
            assertThat(sut.canMove(), is(false));
            //point0は前回の位置
            assertThat(sut.getPoint0(), is(prev));
            prev = sut.getPoint();
            sut.nextTurn();
        }
        sut.goToPoint(target);
        //5回目はまだ動ける
        assertThat(sut.canMove(), is(true));
        //point0は前回の位置
        assertThat(sut.getPoint0(), is(prev));
    }

    @Test
    public void turnClockWiseで角度が正の方向に増える() throws Exception {
        sut.turnClockWise(1);
        double actual = sut.getTheta();
        double expected = 1;
        assertThat(actual, is(closeTo(expected, 0.000001)));
    }

    @Test
    public void turnCounterClockWiseで角度が負の方向に増えgetThetaは0から2πまでの値を返す() throws Exception {
        sut.turnCounterClockWise(1);
        double actual = sut.getTheta();
        double expected = 2 * Math.PI - 1;
        assertThat(actual, is(closeTo(expected, 0.000001)));
    }

    @Test
    public void setThetaで角度を指定できる() throws Exception {
        sut.setTheta(1);
        double actual = sut.getTheta();
        double expected = 1;
        assertThat(actual, is(closeTo(expected, 0.000001)));
    }

    @Test
    public void 負の角度を設定したときgetThetaは0から2πまでの値を返す() throws Exception {
        sut.setTheta(-1);
        double actual = sut.getTheta();
        double expected = 2 * Math.PI - 1;
        assertThat(actual, is(closeTo(expected, 0.000001)));
    }

    @Test
    public void 角度に2πより大きな値を設定したときgetThetaは0から2πまでの値を返す() throws Exception {
        sut.setTheta(20);
        double actual = sut.getTheta();
        double expected = 20 - 3 * 2 * Math.PI;
        assertThat(actual, is(closeTo(expected, 0.000001)));
    }

    @Test
    public void getResidualDistanceで残り移動可能距離を取得できる() throws Exception {
        sut.goStraight(13);
        double actual = sut.getResidualDistance();
        double expected = 3;
        assertThat(actual, is(closeTo(expected, 0.000001)));
        //最後まで動く
        sut.goStraight();
        actual = sut.getResidualDistance();
        expected = 0;
        assertThat(actual, is(closeTo(expected, 0.000001)));
    }

    @Test
    public void canMoveでまだ動けるときtrueを返す() throws Exception {
        sut.goStraight(15.99999);
        boolean actual = sut.canMove();
        boolean expected = true;
        assertThat(actual, is(expected));
    }

    @Test
    public void canMoveでもう動けないときfalseを返す() throws Exception {
        sut.goStraight(16);
        boolean actual = sut.canMove();
        boolean expected = false;
        assertThat(actual, is(expected));
    }

    @Test
    public void setColorで色を設定しgetColorで取得できる() throws Exception {
        sut.setColor(Color.RED);
        Color actual = sut.getColor();
        Color expected = Color.RED;
        assertThat(actual, is(expected));
    }

    @Test
    public void 初期状態でgetColorは白を返す() throws Exception {
        Color actual = sut.getColor();
        Color expected = Color.WHITE;
        assertThat(actual, is(expected));
    }

}