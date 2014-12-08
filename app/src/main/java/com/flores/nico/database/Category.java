package com.flores.nico.database;

import com.orm.SugarRecord;

/**
 * Created by nicoflores on 07-12-14.
 */
public class Category extends SugarRecord<Category> {
    private String name;

    public Category () {
    }

    public Category (String name) {
        this.name = name;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    @Override
    public String toString () {
        return name;
    }
}
