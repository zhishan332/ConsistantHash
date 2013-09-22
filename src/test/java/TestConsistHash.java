import com.mxf.hash.ConsistantHash;
import com.mxf.hash.Node;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: yfwangqing
 * Date: 13-8-23
 * Time: 下午1:08
 * To change this template use File | Settings | File Templates.
 */
public class TestConsistHash {
    public static void main(String[] args) {
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

//        List<String> dd=new ArrayList<String>();
        final ConsistantHash hash = ConsistantHash.getInstance();
        hash.setNodeList(ips);
//        hash.setAlg(HashAlgorithm.MurMurHash);
//    	hash.set(1024);
        hash.buildHashCycle();
        System.out.println(hash.getNodeMap());
//        final ConcurrentHashMap<String, Long> stat = new ConcurrentHashMap<String, Long>();
//        long start = System.currentTimeMillis();
//        for (int h = 0; h < 100000; h++) {
//            Node node = hash.findNodeByKey("ID88591" + h);
//            Long count = stat.get(node.getIp());
//            if (count == null) {
//                stat.put(node.getIp(), 1L);
//            } else {
//                stat.put(node.getIp(), count + 1);
//            }
//        }
//        System.out.println(System.currentTimeMillis() - start);
//        long all = 0;
//        for (Map.Entry<String, Long> entry : stat.entrySet()) {
//            long num = entry.getValue();
//            all += num;
//            System.out.println("mac:" + entry.getKey() + " hits:" + num);
//        }
//        System.out.println(all);
    }
}
