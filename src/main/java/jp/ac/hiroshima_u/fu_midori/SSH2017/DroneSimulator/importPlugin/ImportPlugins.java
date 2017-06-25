package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.importPlugin;

import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.tactics.ITacticsViewerFrontend;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.victim.placer.VictimsPlacerViewerFrontend;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * プラグイン(tacticsとplacingvictims)の読み込み
 *
 * @author 遠藤拓斗 on 2017/05/15.
 */
public class ImportPlugins {
    private ImportPlugins() {
    }

    /**
     * tacticsの読み込み
     * @return 読み込んだTacticsViewerFrontendのリスト
     */
    public static List<ITacticsViewerFrontend> getTacticsPlugins() {
        List<ITacticsViewerFrontend> list = new ArrayList<>();
        ServiceLoader<ITacticsViewerFrontend> loader = ServiceLoader.load(ITacticsViewerFrontend.class, Thread.currentThread().getContextClassLoader());
        for (ITacticsViewerFrontend plugin : loader) {
            list.add(plugin);
        }
        return list;
    }

    /**
     * VictimsPlacerの読み込み
     * @return 読み込んだVictimsPlacerViewerFrontendのリスト
     */
    public static List<VictimsPlacerViewerFrontend> getPlacerPlugins() {
        List<VictimsPlacerViewerFrontend> list = new ArrayList<>();
        ServiceLoader<VictimsPlacerViewerFrontend> loader = ServiceLoader.load(VictimsPlacerViewerFrontend.class, Thread.currentThread().getContextClassLoader());
        for (VictimsPlacerViewerFrontend plugin : loader) {
            list.add(plugin);
        }
        return list;
    }
}
