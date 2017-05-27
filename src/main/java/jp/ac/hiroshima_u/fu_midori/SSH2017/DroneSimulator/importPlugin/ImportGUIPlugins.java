package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.importPlugin;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.TacticsGUIInterface;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placing.PlacingVictimsGUIInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * GUI時のプラグイン(tacticsとplacingvictims)の読み込み
 *
 * @author 遠藤拓斗 on 2017/05/15.
 */
public class ImportGUIPlugins {
    private ImportGUIPlugins() {
    }

    /**
     * tacticsの読み込み
     * @return 読み込んだGUITacticsInterfaceのリスト
     */
    public static List<TacticsGUIInterface> getTacticsPlugins() {
        List<TacticsGUIInterface> list = new ArrayList<>();
        ServiceLoader<TacticsGUIInterface> loader = ServiceLoader.load(TacticsGUIInterface.class, Thread.currentThread().getContextClassLoader());
        for (TacticsGUIInterface plugin : loader) {
            list.add(plugin);
        }
        return list;
    }

    /**
     * placingVictimsの読み込み
     * @return 読み込んだGUIPlacingVictimsInterfaceのリスト
     */
    public static List<PlacingVictimsGUIInterface> getPlacingPlugins() {
        List<PlacingVictimsGUIInterface> list = new ArrayList<>();
        ServiceLoader<PlacingVictimsGUIInterface> loader = ServiceLoader.load(PlacingVictimsGUIInterface.class, Thread.currentThread().getContextClassLoader());
        for (PlacingVictimsGUIInterface plugin : loader) {
            list.add(plugin);
        }
        return list;
    }
}
