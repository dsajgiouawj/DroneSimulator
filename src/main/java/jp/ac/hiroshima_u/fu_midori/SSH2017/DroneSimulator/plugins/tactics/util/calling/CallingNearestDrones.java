package jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.plugins.tactics.util.calling;

import javafx.geometry.Point2D;
import jp.ac.hiroshima_u.fu_midori.SSH2017.DroneSimulator.drone.Drone;

import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

/**
 * ドローンが被災者を発見したとき近くにいる一定台数(条件を満たすならば呼び出したドローン自身も含む)のドローンを呼び寄せます。
 *
 * @author 遠藤拓斗 on 2017/05/30.
 */
public class CallingNearestDrones implements SelectCalleeMediator {
    private int numDrone;
    private int numOfDronesToCall;
    private FiltersManagement filters;
    private List<Drone> drones;
    private ICaller caller;

    public CallingNearestDrones(List<Drone> drones, ICaller caller, int numOfDronesToCall, FiltersManagement filters) {
        this.numOfDronesToCall = numOfDronesToCall;
        this.filters = filters;
        this.numDrone = drones.size();
        this.drones = drones;
        this.caller = caller;
    }

    @Override
    public void before() {
        filters.before();
    }

    @Override
    public void after() {
        filters.after();
    }

    @Override
    public void inform(int id, int num) {
        filters.informFinding(id, num);
        IntStream stream = IntStream.range(0, numDrone);
        stream = filters.filter(id, stream);
        call(id, stream.toArray());
    }

    private void call(int findingDroneId, int[] qualifiedDronesID) {
        PriorityQueue<DistanceCompare> que = new PriorityQueue<>(Collections.reverseOrder());
        Point2D target = drones.get(findingDroneId).getPoint();
        for (int droneId : qualifiedDronesID) {
            double dist = drones.get(droneId).getPoint().distance(target);
            que.add(new DistanceCompare(dist, drones.get(droneId), droneId));
            while (que.size() > numOfDronesToCall) {
                que.poll();
            }
        }
        if (que.size() != 0) {
            filters.informCalling(findingDroneId);
        }
        int[] calleesId = new int[que.size()];
        int index = 0;
        while (!que.isEmpty()) {
            DistanceCompare distanceCompare = que.poll();
            calleesId[index++] = distanceCompare.id;
            filters.informBeingCalled(distanceCompare.id);
        }
        caller.call(findingDroneId, calleesId);
    }
}
