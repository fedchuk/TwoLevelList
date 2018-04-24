package eu.theappshop.twolevellist.utils;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Fedchuk Maxim on 2018-04-24.
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        for (int i = 0; i < parent.getChildCount(); i++) {

            if (parent.getChildAt(i).getVisibility() == View.GONE)
                continue;
        }
    }
}
