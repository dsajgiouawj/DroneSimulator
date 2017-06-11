package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.filter.CallIfTheFindingDroneHasNotCalledRecently;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.filter.CallingDronesWhichHaveNeverBeenCalled;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.filter.CallingDronesWhichHaveNotCalledRecently;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling.filter.CallingDronesWhichHaveNotFindRecently;

import java.util.ArrayList;
import java.util.List;

/**
 * FiltersManagementをGUIから利用するためのクラス
 *
 * @author 遠藤拓斗 on 2017/06/07.
 */
public class FiltersManagementGUIInterface {
    private CheckBox callOnlyDronesWhichHaveNeverBeenCalled = new CheckBox("呼び出されたことのあるドローンは呼ばない");
    private CheckBox callOnlyDronesWhichHaveNotCalledRecently = new CheckBox("最近呼び出されたことのあるドローンは呼ばない");
    private CheckBox callOnlyDronesWhichHaveNotFindRecently = new CheckBox("最近発見したことのあるドローンは呼ばない");
    private CheckBox callOnlyIfTheDroneHasNotCalledRecently = new CheckBox("最近呼び出していない場合のみ呼ぶ");

    public void makeGUI(VBox vBox) {
        vBox.getChildren().addAll(
                callOnlyDronesWhichHaveNeverBeenCalled,
                callOnlyDronesWhichHaveNotCalledRecently,
                callOnlyDronesWhichHaveNotFindRecently,
                callOnlyIfTheDroneHasNotCalledRecently
        );
    }

    public FiltersManagement getFiltersManagement(int thresholdTime, List<Drone> drones) {
        List<CallingFilter> filters = new ArrayList<>();
        if (callOnlyDronesWhichHaveNeverBeenCalled.isSelected())
            filters.add(new CallingDronesWhichHaveNeverBeenCalled(drones));
        if (callOnlyDronesWhichHaveNotCalledRecently.isSelected())
            filters.add(new CallingDronesWhichHaveNotCalledRecently(thresholdTime, drones));
        if (callOnlyDronesWhichHaveNotFindRecently.isSelected())
            filters.add(new CallingDronesWhichHaveNotFindRecently(thresholdTime, drones));
        if (callOnlyIfTheDroneHasNotCalledRecently.isSelected())
            filters.add(new CallIfTheFindingDroneHasNotCalledRecently(thresholdTime, drones));
        return new FiltersManagement(filters.toArray(new CallingFilter[0]));
    }
}
