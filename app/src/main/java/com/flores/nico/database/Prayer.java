package com.flores.nico.database;

import com.orm.SugarRecord;

/**
 * Created by nicoflores on 07-12-14.
 */
public class Prayer extends SugarRecord<Prayer> {
    private String title;
    private String text;

    public Prayer () {
    }

    public Prayer (String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public String getText () {
        return text;
    }

    public void setText (String text) {
        this.text = text;
    }

    @Override
    public String toString () {
        return title;
    }
}
