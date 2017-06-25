package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.simulator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.util.Scanner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

/**
 * SimulatorCUIInterfaceのテスト
 *
 * @author 遠藤拓斗 2017/05/29.
 */
public class SimulatorCUIFrontendTest {
    @Rule
    public TextFromStandardInputStream systemIn = emptyStandardInputStream();

    private SimulatorCUIFrontend sut;

    @Before
    public void setUp() {
        sut = new SimulatorCUIFrontend();
    }

    @Test
    public void readInfoで情報を読み込んだ後getSimulatorがSimulatorクラスを返す() throws Exception {
        int numDrone = 10;
        int population = 20;
        int limitTime = 30;
        double viewRangeRadius = 40;
        //use Tactics
        String tacticsCUIName = "jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.spiralTactics.CUIFrontend";
        double searchRatio = 0.5;
        //use PlacingCircle
        String placingCUIName = "jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.VictimsPlacer.circle.CUIFrontend";
        double x = 0;
        double y = 0;
        double r = 1000;
        systemIn.provideLines("" + numDrone, "" + population, "" + limitTime, "" + viewRangeRadius, tacticsCUIName, "" + searchRatio, placingCUIName, "" + x, "" + y, "" + r);

        sut.readInfo(new Scanner(System.in));
        Simulator simulator = sut.getSimulator();

        assertThat(simulator.getDrones().size(), is(numDrone));
        assertThat(simulator.getVictims().population(), is(population));
        assertThat(simulator.getLimitTime(), is(limitTime));
        assertThat(simulator.getDrones().get(0).getViewRangeRadius(), is(viewRangeRadius));
    }

    @Test(expected = RuntimeException.class)
    public void tacticsが存在しない場合RuntimeExceptionを送出() throws Exception {
        int numDrone = 10;
        int population = 20;
        int limitTime = 30;
        double viewRangeRadius = 40;
        String tacticsCUIName = "not.exist";
        systemIn.provideLines("" + numDrone, "" + population, "" + limitTime, "" + viewRangeRadius, tacticsCUIName);

        sut.readInfo(new Scanner(System.in));
    }

    @Test(expected = RuntimeException.class)
    public void VictimsPlacerが存在しない場合RuntimeExceptionを送出() throws Exception {
        int numDrone = 10;
        int population = 20;
        int limitTime = 30;
        double viewRangeRadius = 40;
        //use Tactics
        String tacticsCUIName = "jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.spiralTactics.CUIFrontend";
        double searchRatio = 0.5;
        //use PlacingCircle
        String placerCUIName = "not.exist";
        systemIn.provideLines("" + numDrone, "" + population, "" + limitTime, "" + viewRangeRadius, tacticsCUIName, "" + searchRatio, placerCUIName);

        sut.readInfo(new Scanner(System.in));
    }
}