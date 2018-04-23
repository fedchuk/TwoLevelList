package eu.theappshop.twolevellist.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import eu.theappshop.twolevellist.R;
import eu.theappshop.twolevellist.data.model.FirstLevelModel;
import eu.theappshop.twolevellist.data.model.SecondLevelModel;

/**
 * Created by Fedchuk Maxim on 2018-04-23.
 */

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.ViewHolder> {

    private Context mContext;
    private List<FirstLevelModel> mFirstLevelModelList;

    public LevelAdapter(Context context, List<FirstLevelModel> firstLevelModels) {
        this.mContext = context;
        this.mFirstLevelModelList = firstLevelModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_level, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FirstLevelModel firstLevelModel = mFirstLevelModelList.get(position);
        if (firstLevelModel!=null) {
            if (firstLevelModel.getTitle()!=null && !firstLevelModel.getTitle().equals("")) {
                holder.textLevelTitle.setText(firstLevelModel.getTitle());
            }
            if (firstLevelModel.getChild()!=null && !firstLevelModel.getChild().isEmpty()) {
                holder.imageArrow.setVisibility(View.VISIBLE);
                if (firstLevelModel.getOpen()) {
                    holder.imageArrow.setImageResource(R.drawable.ic_arrow_up);
                    holder.linContent.setVisibility(View.VISIBLE);
                    hideChild(holder.linContent);
                    addChild(holder.linContent, firstLevelModel.getChild(), position);
                } else {
                    holder.imageArrow.setImageResource(R.drawable.ic_arrow_down);
                    holder.linContent.setVisibility(View.GONE);
                    hideChild(holder.linContent);
                }
            } else {
                holder.imageArrow.setVisibility(View.INVISIBLE);
            }
        }

        holder.relRoot.setTag(position);
        holder.relRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int)view.getTag();
                if (mFirstLevelModelList.size()>pos) {
                    if (mFirstLevelModelList.get(pos).getChild()!=null && !mFirstLevelModelList.get(pos).getChild().isEmpty()) {
                        if (mFirstLevelModelList.get(pos).getOpen()) {
                            mFirstLevelModelList.get(pos).setOpen(false);
                        } else {
                            mFirstLevelModelList.get(pos).setOpen(true);
                        }
                        notifyDataSetChanged();
                    }
                }
            }
        });
    }

    private void addChild(LinearLayout linearLayout, final List<SecondLevelModel> secondLevelModelList, final int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View layerChild[] = new View[secondLevelModelList.size()];
        for (int s = 0; s < secondLevelModelList.size(); s++) {
            SecondLevelModel secondLevelModel = secondLevelModelList.get(0);
            if (secondLevelModel!=null) {
                layerChild[s] = inflater.inflate(R.layout.item_list_second_level, null);
                TextView body = (TextView) layerChild[s].findViewById(R.id.textview_title_body);
                ImageView check = (ImageView) layerChild[s].findViewById(R.id.imageCheck);
                body.setText(secondLevelModel.getTitle());
                if (secondLevelModel.getCheck()) {
                    check.setVisibility(View.VISIBLE);
                } else {
                    check.setVisibility(View.INVISIBLE);
                }

                layerChild[s].setTag(s);
                layerChild[s].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = (int)view.getTag();
                        if (!secondLevelModelList.get(pos).getCheck()) {
                            clearOtherChoice();
                            mFirstLevelModelList.get(position).getChild().get(pos).setCheck(true);
                            notifyDataSetChanged();
                        }
                    }
                });
                linearLayout.addView(layerChild[s]);
            }
        }
    }

    private void hideChild(LinearLayout linearLayout) {
        if((linearLayout).getChildCount() > 0) {
            (linearLayout).removeAllViews();
        }
    }

    private void clearOtherChoice() {
        if (mFirstLevelModelList!=null && !mFirstLevelModelList.isEmpty()) {
            for (int i=0; i<mFirstLevelModelList.size(); i++) {
                FirstLevelModel firstLevelModel = mFirstLevelModelList.get(i);
                if (firstLevelModel.getChild()!=null && !firstLevelModel.getChild().isEmpty()) {
                    for (int j=0; j<firstLevelModel.getChild().size(); j++) {
                        firstLevelModel.getChild().get(j).setCheck(false);
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mFirstLevelModelList!=null && !mFirstLevelModelList.isEmpty()) {
            return mFirstLevelModelList.size();
        } else {
            return 0;
        }
    }

    public void updateList(List<FirstLevelModel> data) {
        this.mFirstLevelModelList = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textLevelTitle;
        private RelativeLayout relRoot;
        private LinearLayout linContent;
        private ImageView imageArrow;

        public ViewHolder(View itemView) {
            super(itemView);
            textLevelTitle = (TextView) itemView.findViewById(R.id.textview_title);
            relRoot = (RelativeLayout) itemView.findViewById(R.id.linearlayout_root);
            linContent = (LinearLayout) itemView.findViewById(R.id.linearlayout_content);
            imageArrow = (ImageView) itemView.findViewById(R.id.imageArrow);
        }
    }
}
