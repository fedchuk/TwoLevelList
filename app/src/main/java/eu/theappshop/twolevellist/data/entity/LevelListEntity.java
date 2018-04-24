package eu.theappshop.twolevellist.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Fedchuk Maxim on 2018-04-24.
 */

@Entity(tableName = "levellist")
public class LevelListEntity {

    @PrimaryKey(autoGenerate = false)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "parent")
    private long parent;

    @ColumnInfo(name = "open")
    private boolean open;

    @ColumnInfo(name = "checks")
    private boolean checks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getParent() {
        return parent;
    }

    public void setParent(long parent) {
        this.parent = parent;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isChecks() {
        return checks;
    }

    public void setChecks(boolean checks) {
        this.checks = checks;
    }
}