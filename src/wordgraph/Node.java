package wordgraph;
import java.util.Map;
import java.util.TreeMap;
/**
 * .
 * @author HolynovaSD
 * .
 */
public class Node {
    /**
     * .
     */
    private String data = new String();
    /** */
    private Map<String, Integer> child = new TreeMap<String, Integer>();
    /**
     * .
     * @return .
     */
    public Map<String, Integer> getChild() {
        return this.child;
    }
    /**
     * .
     */
    Node() {
        child.clear();
        data = "";
    }
    /**
     * .
     * @param initData ;
     */
    Node(final String initData) {
        this.data = initData;
        child.clear();
    }
//    /**
//     * .
//     * @param v ;
//     */
//    final void addNode(final String v) {
//        this.child.put(v, 1);
//    }
//    /**
//     * .
//     * @param v ;
//     * @param value ;
//     */
//    final void setValue(final String v, final Integer value) {
//        this.child.put(v, value);
//    }
//    /**
//     * .
//     * @param newData ;
//     */
//    final void setData(final String newData) {
//        this.data = newData;
//    }
//    /**
//     * .
//     * @param e ;
//     */
//    final void deleteNode(final node e) {
//        this.child.remove(e);
//    }
//    /**
//     * .
//     */
//    final void showData() {
//        System.out.print(this.data + "   ");
//    }
}
