package com.flores.nico.database;

import com.orm.SugarRecord;

/**
 * Created by nicoflores on 09-12-14.
 */
public class PrayerCategory extends SugarRecord<PrayerCategory> {
    private Category category;
    private Prayer prayer;

    public PrayerCategory () {
    }

    public PrayerCategory (Category category, Prayer prayer) {
        this.category = category;
        this.prayer = prayer;
    }

    public Category getCategory () {
        return category;
    }

    public void setCategory (Category category) {
        this.category = category;
    }

    public Prayer getPrayer () {
        return prayer;
    }

    public void setPrayer (Prayer prayer) {
        this.prayer = prayer;
    }
}
