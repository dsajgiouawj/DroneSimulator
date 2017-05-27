package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.importPlugin;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.TacticsCUIInterface;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placing.PlacingVictimsCUIInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * CUI時のプラグイン読み込み
 *
 * @author 遠藤拓斗 on 2017/05/15.
 */
public class ImportCUIPlugins {
    private ImportCUIPlugins() {
    }

    /**
     * tacticsの読み込み
     * @return 読み込んだUITacticsInterfaceのリスト
     */
    public static List<TacticsCUIInterface> getTacticsPlugins() {
        List<TacticsCUIInterface> list = new ArrayList<>();
        ServiceLoader<TacticsCUIInterface> loader = ServiceLoader.load(TacticsCUIInterface.class, Thread.currentThread().getContextClassLoader());
        for (TacticsCUIInterface plugin : loader) {
            list.add(plugin);
        }
        return list;
    }

    /**
     * placingVictimsの読み込み
     * @return 読み込んだCUIPlacingVictimsInterfaceのリスト
     */
    public static List<PlacingVictimsCUIInterface> getPlacingPlugins() {
        List<PlacingVictimsCUIInterface> list = new ArrayList<>();
        ServiceLoader<PlacingVictimsCUIInterface> loader = ServiceLoader.load(PlacingVictimsCUIInterface.class, Thread.currentThread().getContextClassLoader());
        for (PlacingVictimsCUIInterface plugin : loader) {
            list.add(plugin);
        }
        return list;
    }
}
