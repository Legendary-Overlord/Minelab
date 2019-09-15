package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventory {
	
	private Stack<Block>[] inv;
	private HashMap<String,Integer> counter;
	private String[] typeBlocks;
	
	public Inventory() {
		inv= new Stack[4*9];
		typeBlocks = new String[5];
		counter = new HashMap<>();
		for(String s:typeBlocks) {
			counter.put(s, 0);
		}
	}
	public void insertBlock(Block b,int n) {
		Stack<Block> c = new Stack<>();
		for(int i=0;i<n;i++)
			c.push(b);
		for (Stack<Block> s:inv) {
			if(s.isEmpty()) {
				s=c;
				break;
			}
		}
	}
	public void groupBlocks() {
		countBlocks();
		Stack<Block>[] s = new Stack[4*9];
		for(String st : typeBlocks) {
			int n = counter.get(st);
			for(Stack<Block> b :s) {
				if(b.isEmpty()) {
					if(n>=64) {
						for(int i =0; i<63;i++)
							b.push(new Block("st"));
						n-=64;
					}else if(n>0) {
						for(int i=0;i<n;i++)
							b.push(new Block("st"));
						break;
					}else
						break;
				}
			}		
		}
		inv=s;
	}
	public void sortBlocks() {
		
	}
	public ArrayList<Stack<Block>[]> groupQuickAccess() {
		countBlocks();
		ArrayList<Stack<Block>[]> qt = new ArrayList<>();
		for(String st : typeBlocks) {
			int n = counter.get(st);
			Stack<Block>[] s = new Stack[(int) Math.ceil(n/64)];
			for(Stack<Block> b :s) {
				if(b.isEmpty()) {
					if(n>=64) {
						for(int i =0; i<63;i++)
							b.push(new Block("st"));
						n-=64;
					}else if(n>0) {
						for(int i=0;i<n;i++)
							b.push(new Block("st"));
						break;
					}else
						break;
				}
			}
			qt.add(s);
		}
		return qt;
	}
	private void countBlocks() {
		counter.clear();
		for(Stack<Block> s : inv) {
			if(!s.isEmpty()) {
				String com = s.peek().getType();
				if (counter.containsKey(com)){
					counter.replace(com, (counter.get(com)+s.size()));
				}else
					counter.put(com, s.size());
			}
		}
	}
	
}
