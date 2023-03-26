
import com.meixianfeng.hash.ConsistantHash;
import com.meixianfeng.hash.Node;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: yfwangqing
 * Date: 13-8-23
 * Time: 下午1:05
 * 一致性HASH测试类--多线程并发测试
 */
public class TestConsistHashWithComp {
    final ConcurrentHashMap<String, Long> stat = new ConcurrentHashMap<String, Long>();

    public static void main(String[] args) throws InterruptedException {
        final TestConsistHashWithComp testConsistHashWithComp = new TestConsistHashWithComp();
        Set<Node> ips = new HashSet<Node>();
        ips.add(new Node("192.168.1.1"));
        ips.add(new Node("192.168.1.2"));
        ips.add(new Node("192.168.1.3"));
        ips.add(new Node("192.168.1.4"));
        ips.add(new Node("192.168.1.5"));
        ips.add(new Node("192.168.1.6"));
        ips.add(new Node("192.168.1.7"));
        ips.add(new Node("192.168.1.8"));
        ips.add(new Node("192.168.1.9"));
        ips.add(new Node("192.168.1.10"));

        final ConsistantHash hash = ConsistantHash.getInstance();
        hash.setNodeList(ips);
//        hash.setAlg(HashAlgorithm.LUA_HASH);
//    	hash.set(1024);
//    	hash.setAlg(HashAlgorithm.CRC32_HASH);
        hash.buildHashCycle();

        long start = System.currentTimeMillis();

        Thread [] threads= new Thread[100];
        for (int i = 0; i < 100; i++) {
            final String name = "thread" + i;
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int h = 0; h < 100000; h++) {
                        Node node = hash.findNodeByKey(name + h);
                        testConsistHashWithComp.send(node);
                    }
                    testConsistHashWithComp.print();
                }
            }, name);
            threads[i].start();
        }
        System.out.println(System.currentTimeMillis() - start);
        for(Thread t: threads)
            t.join();
//        Thread.sleep(1000 * 20);
        testConsistHashWithComp.print();
    }

    public synchronized void send(Node node) {
        Long count = stat.get(node.getIp());
        if (count == null) {
            stat.put(node.getIp(), 1L);
        } else {
            stat.put(node.getIp(), count + 1);
        }
    }

    public ConcurrentHashMap<String, Long> getStat() {
        return stat;
    }

    public void print() {
        long all = 0;
        for (Map.Entry<String, Long> entry : stat.entrySet()) {
            long num = entry.getValue();
            all += num;
            System.out.println("mac:" + entry.getKey() + " hits:" + num);
        }
        System.out.println("all：" + all);
    }
}
