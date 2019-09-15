package model;

import java.util.ArrayList;
import java.util.List;

public class Stack<E> {
	
	private List<E> l;
	
	public Stack() {
		l= new ArrayList<E>();
	}
	
	public void push(E obj) {
		l.add(obj);
	}
	public E pop() {
		int idx = l.size()-1;
		E x = l.get(idx);
		l.remove(idx);
		return x;
	}
	public E peek() {
		return l.get(l.size()-1);
	}
	public int search(E x) {
		return l.lastIndexOf(x); 
	}
	public boolean isEmpty() {
		if(l.isEmpty())
			return true;
		else
			return false;
	}

	public Integer size() {
		return l.size();
	}
	
}
