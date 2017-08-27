package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.FormattedTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.gui.controls.IntTextFieldWithLabel;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling.filter.NotCallIfTheDroneCallRecently;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling.filter.RemoveEverBeenCalledDrones;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling.filter.RemoveRecentlyCallDrones;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling.filter.RemoveRecentlyFindDrones;

import java.util.ArrayList;
import java.util.List;

/**
 * FiltersManagementをViewerから利用するためのクラス
 *
 * @author 遠藤拓斗 on 2017/06/07.
 */
public class FiltersManagementViewerFrontend {
    private CheckBox callOnlyDronesWhichHaveNeverBeenCalled = new CheckBox("呼び出されたことのあるドローンは呼ばない");
    private CheckBox callOnlyDronesWhichHaveNotCalledRecently = new CheckBox("最近呼び出したことのあるドローンは呼ばない");
    private CheckBox callOnlyDronesWhichHaveNotFindRecently = new CheckBox("最近発見したことのあるドローンは呼ばない");
    private CheckBox callOnlyIfTheDroneHasNotCalledRecently = new CheckBox("最近呼び出していない場合のみ呼ぶ");
    private FormattedTextFieldWithLabel<Integer> recently = new IntTextFieldWithLabel("最近[s]", 30);

    public void makeGUI(VBox vBox) {
        vBox.getChildren().addAll(
                callOnlyDronesWhichHaveNeverBeenCalled,
                callOnlyDronesWhichHaveNotCalledRecently,
                callOnlyDronesWhichHaveNotFindRecently,
                callOnlyIfTheDroneHasNotCalledRecently,
                recently
        );
    }

    public FiltersManagement getFiltersManagement(List<Drone> drones) {
        List<CallingFilter> filters = new ArrayList<>();
        int thresholdTime = recently.getValue();
        if (callOnlyDronesWhichHaveNeverBeenCalled.isSelected())
            filters.add(new RemoveEverBeenCalledDrones(drones));
        if (callOnlyDronesWhichHaveNotCalledRecently.isSelected())
            filters.add(new RemoveRecentlyCallDrones(thresholdTime, drones));
        if (callOnlyDronesWhichHaveNotFindRecently.isSelected())
            filters.add(new RemoveRecentlyFindDrones(thresholdTime, drones));
        if (callOnlyIfTheDroneHasNotCalledRecently.isSelected())
            filters.add(new NotCallIfTheDroneCallRecently(thresholdTime, drones));
        return new FiltersManagement(filters.toArray(new CallingFilter[0]));
    }
}
