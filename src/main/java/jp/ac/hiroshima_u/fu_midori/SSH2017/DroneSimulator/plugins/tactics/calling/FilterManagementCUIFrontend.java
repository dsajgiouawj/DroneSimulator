package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.filter.NotCallIfTheDroneCallRecently;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.filter.RemoveEverBeenCalledDrones;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.filter.RemoveRecentlyCallDrones;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.filter.RemoveRecentlyFindDrones;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * FilterManagementをCUIから利用するためのクラス
 *
 * @author 遠藤拓斗 on 2017/06/07.
 */
public class FilterManagementCUIFrontend {
    private boolean removeEverBeenCalledDrones;
    private boolean removeRecentlyCallDrones;
    private int recentlyOfRemoveRecentlyCallDrones;
    private boolean removeRecentlyFindDrones;
    private int recentlyOfRemoveRecentlyFindDrones;
    private boolean notCallIfTheDroneCallRecently;
    private int recentlyOfNotCallIfTheDroneCallRecently;

    public void readInfo(Scanner scanner) {
        System.err.println("呼び出されたことのあるドローンは呼ばない(boolean)");
        removeEverBeenCalledDrones = scanner.nextBoolean();
        System.err.println("最近呼び出したことのあるドローンは呼ばない(boolean)\n");
        removeRecentlyCallDrones = scanner.nextBoolean();
        if (removeRecentlyCallDrones) {
            System.err.println("最近");
            recentlyOfRemoveRecentlyCallDrones = scanner.nextInt();
        }
        System.err.println("最近発見したことのあるドローンは呼ばない(boolean)");
        removeRecentlyFindDrones = scanner.nextBoolean();
        if (removeRecentlyFindDrones) {
            System.err.println("最近");
            recentlyOfRemoveRecentlyFindDrones = scanner.nextInt();
        }
        System.err.println("最近呼び出していない場合のみ呼ぶ(boolean)");
        notCallIfTheDroneCallRecently = scanner.nextBoolean();
        if (notCallIfTheDroneCallRecently) {
            System.err.println("最近");
            recentlyOfNotCallIfTheDroneCallRecently = scanner.nextInt();
        }
    }

    public FiltersManagement getFilterManagement(List<Drone> drones) {
        List<CallingFilter> conditions = new ArrayList<>();
        if (removeEverBeenCalledDrones)
            conditions.add(new RemoveEverBeenCalledDrones(drones));
        if (removeRecentlyCallDrones)
            conditions.add(new RemoveRecentlyCallDrones(recentlyOfRemoveRecentlyCallDrones, drones));
        if (removeRecentlyFindDrones)
            conditions.add(new RemoveRecentlyFindDrones(recentlyOfRemoveRecentlyFindDrones, drones));
        if (notCallIfTheDroneCallRecently)
            conditions.add(new NotCallIfTheDroneCallRecently(recentlyOfNotCallIfTheDroneCallRecently, drones));
        return new FiltersManagement(conditions.toArray(new CallingFilter[0]));
    }
}
