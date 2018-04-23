package eu.theappshop.twolevellist.data.model.responce;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Fedchuk Maxim on 2018-04-23.
 */

public class GetListResponse<N> {

    @SerializedName("response")
    private List<N> data;

    public List<N> getData() {
        return data;
    }

    public void setData(List<N> data) {
        this.data = data;
    }
}