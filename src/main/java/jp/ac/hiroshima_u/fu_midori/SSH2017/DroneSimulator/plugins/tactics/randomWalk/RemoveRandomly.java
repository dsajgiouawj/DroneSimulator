package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.randomWalk;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling.CallingFilter;

import java.util.stream.IntStream;

/**
 * ランダムに除外
 *
 * @author 遠藤拓斗 on 2017/8/27.
 */
public class RemoveRandomly implements CallingFilter {
    private double probability;

    /**
     * コンストラクタ
     *
     * @param probability 呼び寄せられる確率(0以上1以下)(0以下は0,1以上は1とみなす)
     */
    public RemoveRandomly(double probability) {
        this.probability = probability;
    }

    @Override
    public void before() {

    }

    @Override
    public void after() {

    }

    @Override
    public void informFinding(int id, int num) {

    }

    @Override
    public void informCalling(int id) {

    }

    @Override
    public void informBeingCalled(int id) {

    }

    @Override
    public IntStream filter(int id, IntStream stream) {
        return stream.filter(i -> Math.random() < probability);
    }

    @Override
    public String toString() {
        return probability + "の確率で呼び出す";
    }
}
