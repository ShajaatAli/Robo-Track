package com.mycompany.a4;

public interface IIterator 
{
	public boolean hasNext();
    public GameObject getNext();
    public GameObject getCurrent();
	public void remove(GameObject obj);
}
