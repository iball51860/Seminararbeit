package model;

import java.util.*;

@SuppressWarnings("serial")
public class ArrayTeamSet<E> extends ArrayList<E> {

	public ArrayTeamSet(){
		super();
	}
	
	public ArrayTeamSet(Collection<E> c){
		super();	
		for(E e : c)
			{
				add(e);
			}
	}
	
	public void removeDuplicates(){
		for(E e : this){
			this.remove(e);
			if(!this.contains(e))
			{
				this.add(e);
			}
		}
	}
	
	public boolean add(E e)
	{
		if(this.contains(e))
		{
			return false;
		}
		else
		{
			super.add(e);
			return false;
		}
	}
	
	public void add(int index, E e){
		if(!this.contains(e))
		{
			super.add(index, e);
		}
	}
	
	public boolean addAll(Collection<? extends E> c)
	{
		int sizeBefore = this.size();
		for(E e : c)
		{
			add(e);
		}
		if(this.size() == sizeBefore)
		{
			return false;
		}
		return true;
	}
	
	public boolean addAll(int index, Collection<? extends E> c)
	{
		int sizeBefore = this.size();
		int currentIndex = index;
		for(E e : c)
		{
			add(currentIndex, e);
			currentIndex++;
		}
		if(this.size() == sizeBefore)
		{
			return false;
		}
		return true;
	}
	
	public ArrayTeamSet<E> clone()
	{
		ArrayTeamSet<E> clone = new ArrayTeamSet<E>(this);
		return clone;
	}
}
