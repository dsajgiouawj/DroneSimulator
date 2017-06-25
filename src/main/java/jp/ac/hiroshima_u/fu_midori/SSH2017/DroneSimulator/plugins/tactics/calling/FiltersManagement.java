package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.calling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * filterの管理
 *
 * @author 遠藤拓斗 on 2017/06/06.
 */
public class FiltersManagement {
    private List<CallingFilter> filters = new ArrayList<>();

    public FiltersManagement(CallingFilter... filters) {
        this.filters.addAll(Arrays.asList(filters));
    }

    public void addFilter(CallingFilter filter) {
        filters.add(filter);
    }

    public void before() {
        for (CallingFilter filter : filters) {
            filter.before();
        }
    }

    public void after() {
        for (CallingFilter filter : filters) {
            filter.after();
        }
    }

    public void informFinding(int id, int num) {
        for (CallingFilter filter : filters) {
            filter.informFinding(id, num);
        }
    }

    public void informCalling(int id) {
        for (CallingFilter filter : filters) {
            filter.informCalling(id);
        }
    }

    public void informBeingCalled(int id) {
        for (CallingFilter filter : filters) {
            filter.informBeingCalled(id);
        }
    }

    public IntStream filter(int id, IntStream stream) {
        for (CallingFilter filter : filters) {
            stream = filter.filter(id, stream);
        }
        return stream;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("Filters[");
        for (int i = 0; i < filters.size(); i++) {
            CallingFilter filter = filters.get(i);
            res.append(filter.toString());
            if (i != filters.size() - 1) res.append(",");
        }
        res.append("]");
        return res.toString();
    }
}
