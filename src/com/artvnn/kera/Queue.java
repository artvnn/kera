package com.artvnn.kera;

import java.util.ArrayList;
/**
 * Created by ARTVNN on 4/12/2017.
 */
public class Queue<Item> implements IQueue<Item> {

    ArrayList<Item> queue = new ArrayList<>();

    @Override
    public void add (Item item) {
        queue.add(item);
    }

    @Override
    public Item peek () {
        return queue.size() > 0 ? queue.get(0) : null;
    }

    @Override
    public Item next () {
        return queue.size() > 0 ? queue.remove(0) : null;
    }

}
