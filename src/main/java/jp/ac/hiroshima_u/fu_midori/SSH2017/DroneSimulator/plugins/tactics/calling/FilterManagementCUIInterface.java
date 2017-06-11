package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.filter.CallIfTheFindingDroneHasNotCalledRecently;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.filter.CallingDronesWhichHaveNeverBeenCalled;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.filter.CallingDronesWhichHaveNotCalledRecently;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.filter.CallingDronesWhichHaveNotFindRecently;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * FilterManagementをCUIから利用するためのクラス
 *
 * @author 遠藤拓斗 on 2017/06/07.
 */
public class FilterManagementCUIInterface {
    private boolean callOnlyDronesWhichHaveNeverBeenCalled;
    private boolean callOnlyDronesWhichHaveNotCalledRecently;
    private boolean callOnlyDronesWhichHaveNotFindRecently;
    private boolean callOnlyIfTheDroneHasNotCalledRecently;

    public String explain() {
        return "呼び出されたことのあるドローンは呼ばない(boolean)\n" +
                "最近呼び出されたことのあるドローンは呼ばない(boolean)\n" +
                "最近発見したことのあるドローンは呼ばない(boolean)\n" +
                "最近呼び出していない場合のみ呼ぶ(boolean)\n";
    }

    public void readInfo() {
        Scanner scanner = new Scanner(System.in, "UTF-8");
        callOnlyDronesWhichHaveNeverBeenCalled = scanner.nextBoolean();
        callOnlyDronesWhichHaveNotCalledRecently = scanner.nextBoolean();
        callOnlyDronesWhichHaveNotFindRecently = scanner.nextBoolean();
        callOnlyIfTheDroneHasNotCalledRecently = scanner.nextBoolean();
    }

    public FiltersManagement getFilterManagement(int thresholdTime, List<Drone> drones) {
        List<CallingFilter> conditions = new ArrayList<>();
        if (callOnlyDronesWhichHaveNeverBeenCalled)
            conditions.add(new CallingDronesWhichHaveNeverBeenCalled(drones));
        if (callOnlyDronesWhichHaveNotCalledRecently)
            conditions.add(new CallingDronesWhichHaveNotCalledRecently(thresholdTime, drones));
        if (callOnlyDronesWhichHaveNotFindRecently)
            conditions.add(new CallingDronesWhichHaveNotFindRecently(thresholdTime, drones));
        if (callOnlyIfTheDroneHasNotCalledRecently)
            conditions.add(new CallIfTheFindingDroneHasNotCalledRecently(thresholdTime, drones));
        return new FiltersManagement(conditions.toArray(new CallingFilter[0]));
    }
}
