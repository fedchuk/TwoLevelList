package eu.theappshop.twolevellist.data.model;


import java.io.Serializable;
import java.util.List;

/**
 * Created by Fedchuk Maxim on 2018-04-23.
 */

public class FirstLevelModel implements Serializable {

    private long id;
    private String title;
    private List <SecondLevelModel> child;
    private Boolean open;

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

    public List<SecondLevelModel> getChild() {
        return child;
    }

    public void setChild(List<SecondLevelModel> child) {
        this.child = child;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }
}