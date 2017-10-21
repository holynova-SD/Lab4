package wordgraph;
import java.util.Map;
import java.util.TreeMap;
/**
 * .
 * @author HolynovaSD
 * .
 */
public class SingleNode {
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
    SingleNode() {
        child.clear();
        data = "";
    }
    /**
     * .
     * @param initData ;
     */
    SingleNode(final String initData) {
        this.data = initData;
        child.clear();
    }
    /**
     * .
     * @param v ;
     */
    final void addNode(final String v) {
        this.child.put(v, 1);
    }
    /**
     * .
     * @param v ;
     * @param value ;
     */
    final void setValue(final String v, final Integer value) {
        this.child.put(v, value);
    }
    /**
     * .
     * @param newData ;
     */
    final void setData(final String newData) {
        this.data = newData;
    }
    /**
     * .
     * @param e ;
     */
    final void deleteNode(final String e) {
        this.child.remove(e);
    }
    /**
     * .
     */
    final void showData() {
        System.out.print(this.data + "   ");
    }
}
