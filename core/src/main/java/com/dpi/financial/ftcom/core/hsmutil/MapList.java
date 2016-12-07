/**
 * Utilities: A Map that maps keys to multiple values.
 * 
 * @author Bharavi Gade
 * @version 1.0
 *
 **/

package com.dpi.financial.ftcom.core.hsmutil;

import java.util.*;
import java.util.Map.Entry;
 

public class MapList {

	public Map<Object, Object> _hm;

	public MapList() {
		//this(false);
		_hm = new HashMap<Object,Object>();
	}

	//public MapList( boolean hash) {
	//	_hm = hash ? new HashMap<Object,Object>() : new TreeMap<Object,Object>();
	//}

   /*
    * Call this only for repeated values
    */
	public ArrayList<Object> getValues(Object key) {
		Object s=_hm.get(key);
		//ArrayList<Object> s = (ArrayList<Object>)v; 
		if (s == null) {
			s = new ArrayList<Object>();
			_hm.put(key, s);
		}
		return (ArrayList)s;
	}
	public Object getValue(Object key) {
		//ArrayList s = (ArrayList) _hm.get(key);
		return _hm.get(key);
	 
	}

	public void putValue(Object key, Object value) {
		//if (_bUnique) {
		//	ArrayList vals = getValues(key);
		//	vals.clear();
		//	vals.add(value);
		//} else
		//	getValues(key).add(value);
		_hm.put(key, value);
	}

	//public void putValues(Object key, ArrayList<Object> values) {

	public void putValues(Object key, Object value) {
		   	getValues(key).add(value);		
	}

	public void clear() {
		_hm.clear();
	}

	public boolean contains(Object key, Object value) {
		return getValues(key).contains(value);
	}

	public Set<Object> keySet() {
		return _hm.keySet();
	}

	public String toString() {
		return toString(" ");//default indent
		/*StringBuffer sb = new StringBuffer();
		Iterator<Entry<Object, Object>> i = _hm.entrySet().iterator();
		while (i.hasNext()) {
			Entry<Object, Object> me = i.next();
			sb.append("    " + me.getKey() + " -> " + me.getValue() + "\n");
		}
		return sb.toString();*/
	}
	public String toString(String indent) {
		StringBuffer sb = new StringBuffer();
		Iterator<Entry<Object, Object>> i = _hm.entrySet().iterator();
		while (i.hasNext()) {
			Entry<Object, Object> me = i.next();
			sb.append(indent + me.getKey() + " -> " + me.getValue() + "\n");
		}
		return sb.toString();
	}
	ArrayList<Object> _hsTrans = new ArrayList<Object>();

	// Node could be problems if this return value is modified,
	// or used after the method called a second time.
	public ArrayList<Object> transClosure(Object key) {
		_hsTrans.clear();
		transClosureInt(key);
		return _hsTrans;
	}

	public void transClosureInt(Object key) {
		if (_hsTrans.contains(key)) {
			return;
		}
		_hsTrans.add(key);
		ArrayList<Object> s = getValues(key);
		Iterator<Object> i = s.iterator();
		while (i.hasNext()) {
			transClosureInt(i.next());
		}
	}
	public static void main(String args[]){
		MapList ml=new MapList();
		ml.putValue("a", "aaaaa");
		ml.putValue("b", "bbbbb");
		ml.putValues("c", "c1");
		ml.putValues("c", "c2");
		ml.putValues("c", "c3");
	System.out.println(ml.getValue("a"));
	System.out.println(ml.getValue("b"));
	System.out.println(ml.getValue("c"));
	
		
				
		//
	}
}
