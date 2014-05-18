package model;

import java.util.*;
/**
 * Collection specifically designed to hold teams. Even though it is generic it
 * is recommended to only use this Collection for Teams.
 * Even though the Class extends {@link ArrayList}, it behaves like a Set, allowing 
 * no more than one Object where equals of two objects returns true, to be added.
 * <br><br>
 * NOTE: This class was intentionally created to be able to return iterators that are 
 * always in order. However, this was never implemented. At this time there is no additional
 * functionality to a {@link SortedSet}.
 * 
 * @author Jan Fritze & Manuel Kaiser
 *
 * @param <E>
 */
@SuppressWarnings("serial")
public class ArrayTeamSet<E> extends ArrayList<E> {
	
	/**
	 * Constructs an Empty List with an initial capacity of ten.
	 */
	public ArrayTeamSet(){
		super();
	}
	
	/**
	 * Constructs a list containing the objects of the collection in the parameter
	 * @param c
	 */
	public ArrayTeamSet(Collection<E> c){
		super();	
		for(E e : c)
			{
				add(e);
			}
	}

	/**
	 * Removes Duplicates.
	 */
	public void removeDuplicates(){
		for(E e : this){
			this.remove(e);
			if(!this.contains(e))
			{
				this.add(e);
			}
		}
	}
	
	/**
	 * Adds the given Object.
	 */
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
	
	/**
	 * adds the given Object at the specified index
	 */
	public void add(int index, E e){
		if(!this.contains(e))
		{
			super.add(index, e);
		}
	}
	
	/**
	 * Adds all Objects of the given collection
	 */
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
	
	/**
	 * Adds all objects of the given Collection at the given index.
	 */
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
	
	/**
	 * returns a clone of this ArrayTeamSet.
	 */
	public ArrayTeamSet<E> clone()
	{
		ArrayTeamSet<E> clone = new ArrayTeamSet<E>(this);
		return clone;
	}
}
