package model;

import java.util.*;

public class ArrayTeamSet<E> extends ArrayList<E> {

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
}
