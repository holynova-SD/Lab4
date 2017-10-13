package wordgraph;

import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public class node{
	String data = new String();
	
	Map<String, Integer> child=new TreeMap<String, Integer>();
	node(){
		child.clear();
		data="";
	}
	node(String data){
		this.data=data;
		child.clear();
	}
	void add_node(String v){
		this.child.put(v, 1);
	}
	void set_value(String v,Integer value){
		this.child.put(v,value);
	}
	
	void set_data(String newData){
		this.data=newData;
	}
	
	void delete_node(node e){
		this.child.remove(e);
	}
	void show_data(){
		System.out.print(this.data+"   ");
	}
}