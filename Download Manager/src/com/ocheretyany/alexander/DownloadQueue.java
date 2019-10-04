package com.ocheretyany.alexander;

public class DownloadQueue {
	
	private static Data head;
	private static Data tail;
	private static int depth;
	
	public DownloadQueue() {
		head = null;
		tail = null;
		depth = 0;
	}
	
	public void enqueue(Data item){
		if(head == null){
			head = item;
			tail = item;
		}
		else{
			tail.setNext(item);
			item.setPrevious(tail);
			tail = item;
		}
		
		depth++;
		Manager.gui.re();
	}

	public Data dequeue(){
		Data result = head;
		if(head.getNext() != null){
			head.getNext().setPrevious(null);
			head = head.getNext();
		}
		depth--;
		return result;
	}
	
	public void delete(Data a){
		Data current = head;		
		for(int i = 0; i < depth; i++){
			if(a.getName().equals(current.getName())){
				Data prev = current.getPrevious();
				Data next = current.getNext();
				if(prev != null){
					prev.setNext(next);
				}
				else{
					head = next;
				}
				if(next != null){
					next.setPrevious(prev);
				}
				else{
					tail = prev;
				}
				depth--;
			}
		}
	}
	
	public static int getDepth(){
		return depth;
	}

	public static Data getHead() {
		return head;
	}

	public static Data getTail() {
		return tail;
	}
}