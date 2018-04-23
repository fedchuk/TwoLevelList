package eu.theappshop.twolevellist.data.model;


import java.io.Serializable;
import java.util.List;

/**
 * Created by Fedchuk Maxim on 2018-04-23.
 */

public class SecondLevelModel implements Serializable {

    private long id;
    private String title;
    private Boolean check;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }
}