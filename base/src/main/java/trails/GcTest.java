package trails;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * @author qiuyiliang
 */
public class GcTest {
    public static void main(String[] args) throws InterruptedException {
        List<Order> orderList = new ArrayList<>();
        for (int i = 0; i < 7000000; i++) {
            Order order = new Order();
            order.setId(i);
            order.setGid(i);
            order.setOid(i);
            orderList.add(order);
        }
        List<Order> filteredOrders = filterOrder(orderList);

        List<Order> statisticOrders = statisticOrder(filteredOrders);
    }

    private static List<Order> statisticOrder(List<Order> filteredOrders) throws InterruptedException {
        List<Order> statisticOrders = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < 300; j++) {
                statisticOrders.add(filteredOrders.get(i));
            }
            sleep(1000);
        }
        return statisticOrders;
    }

    private static List<Order> filterOrder(List<Order> orderList) {
        List<Order> filteredOrders = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < 300; j++) {
                filteredOrders.add(orderList.get(i));
            }
        }
        return filteredOrders;
    }
}
class Order{
    private int id;
    private int oid;
    private int gid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }
}
