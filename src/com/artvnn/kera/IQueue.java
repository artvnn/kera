package com.artvnn.kera;

/**
 * Created by ARTVNN on 4/12/2017.
 */
public interface IQueue<Item> {

    public void add (Item item);

    public Item peek ();

    public Item next ();

}
