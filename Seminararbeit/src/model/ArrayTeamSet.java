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
	
	public void add(int index, E element){
		if(!this.contains(element))
		{
			super.add(index, element);
		}
	}
	
	public boolean addAll(Collection<? extends E> c)
	{
		int sizeBefore = this.size();
		for(E b : c)
		{
			add(b);
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
		for(E b : c)
		{
			add(currentIndex, b);
			currentIndex++;
		}
		if(this.size() == sizeBefore)
		{
			return false;
		}
		return true;
	}
	
}
