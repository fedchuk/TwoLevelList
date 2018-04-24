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

import java.util.ArrayList;
import java.util.List;

import eu.theappshop.twolevellist.R;
import eu.theappshop.twolevellist.data.entity.LevelListEntity;

/**
 * Created by Fedchuk Maxim on 2018-04-23.
 */

public class LevelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static OnClickLevelListListner mListener;
    private final int ITEM_FIRST_LEVEL = 0;
    private final int ITEM_SECOND_LEVEL = 1;
    private Context mContext;
    private List<LevelListEntity> mLevelListEntities;

    public LevelAdapter(Context context) {
        this.mContext = context;
        this.mLevelListEntities = new ArrayList<>();
    }

    public void setOnClickCategoryListener(OnClickLevelListListner listener) {
        mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (mLevelListEntities.get(position).getParent() == 0) {
            return ITEM_FIRST_LEVEL;
        } else {
            return ITEM_SECOND_LEVEL;
        }
    }

    @Override
    public int getItemCount() {
        return mLevelListEntities.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_FIRST_LEVEL:
                return new ViewHolderFirst(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_first_level, parent, false));
            case ITEM_SECOND_LEVEL:
                return new ViewHolderSecond(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_second_level, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case ITEM_FIRST_LEVEL:
                ViewHolderFirst viewHolderFirst = (ViewHolderFirst) holder;
                bindFirst(viewHolderFirst, position);
                break;
            case ITEM_SECOND_LEVEL:
                ViewHolderSecond viewHolderSecond = (ViewHolderSecond) holder;
                bindSecond(viewHolderSecond, position);
                break;
        }
    }

    public void bindFirst(ViewHolderFirst holder, int position) {
        LevelListEntity levelListEntity = mLevelListEntities.get(position);
        if (levelListEntity != null) {
            if (levelListEntity.getTitle() != null && !levelListEntity.getTitle().equals("")) {
                holder.textLevelTitle.setText(levelListEntity.getTitle());
            }
            if (checkChild(levelListEntity.getId())) {
                holder.imageArrow.setVisibility(View.VISIBLE);
                if (levelListEntity.isOpen()) {
                    holder.imageArrow.setImageResource(R.drawable.ic_arrow_up);
                } else {
                    holder.imageArrow.setImageResource(R.drawable.ic_arrow_down);
                }
                holder.relRoot.setTag(position);
                holder.relRoot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = (int) view.getTag();
                        LevelListEntity entity = mLevelListEntities.get(pos);
                        if (entity.isOpen()) {
                            entity.setOpen(false);
                        } else {
                            entity.setOpen(true);
                        }
                        mListener.adapterUpdateOpen(entity);
                    }
                });
            } else {
                holder.imageArrow.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void bindSecond(ViewHolderSecond holder, int position) {
        LevelListEntity levelListEntity = mLevelListEntities.get(position);
        if (levelListEntity != null) {
            if (checkParent(mLevelListEntities.get(position).getParent())) {
                holder.setVisibility(true);
                if (levelListEntity.getTitle() != null && !levelListEntity.getTitle().equals("")) {
                    holder.textLevelBody.setText(levelListEntity.getTitle());
                }
                if (levelListEntity.isChecks()) {
                    holder.imageCheck.setVisibility(View.VISIBLE);
                } else {
                    holder.imageCheck.setVisibility(View.INVISIBLE);
                }
                holder.relChild.setTag(position);
                holder.relChild.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = (int)view.getTag();
                        LevelListEntity entity = mLevelListEntities.get(pos);
                        entity.setChecks(true);
                        mListener.adapterAddNewCheck(entity);
                    }
                });
            } else {
                holder.setVisibility(false);
            }
        }

    }

    private Boolean checkParent(long id) {
        if (mLevelListEntities != null && !mLevelListEntities.isEmpty()) {
            for (int i = 0; i < mLevelListEntities.size(); i++) {
                if (mLevelListEntities.get(i).getId() == id) {
                    return mLevelListEntities.get(i).isOpen();
                }
            }
        }
        return true;
    }

    private Boolean checkChild(long id) {
        if (mLevelListEntities != null && !mLevelListEntities.isEmpty()) {
            for (int i = 0; i < mLevelListEntities.size(); i++) {
                if (mLevelListEntities.get(i).getParent() == id) {
                    return true;
                }
            }
        }
        return false;
    }

    public void updateList(List<LevelListEntity> data) {
        this.mLevelListEntities = data;
        notifyDataSetChanged();
    }

    public interface OnClickLevelListListner {
        void adapterAddNewCheck(LevelListEntity levelListEntity);

        void adapterUpdateOpen(LevelListEntity levelListEntity);
    }

    class ViewHolderFirst extends RecyclerView.ViewHolder {
        private TextView textLevelTitle;
        private RelativeLayout relRoot;
        private ImageView imageArrow;


        ViewHolderFirst(View itemView) {
            super(itemView);
            textLevelTitle = (TextView) itemView.findViewById(R.id.textview_title);
            relRoot = (RelativeLayout) itemView.findViewById(R.id.relavitelayout_root);
            imageArrow = (ImageView) itemView.findViewById(R.id.imageArrow);
        }
    }

    class ViewHolderSecond extends RecyclerView.ViewHolder {
        private TextView textLevelBody;
        private RelativeLayout relChild;
        private ImageView imageCheck;


        ViewHolderSecond(View itemView) {
            super(itemView);
            textLevelBody = (TextView) itemView.findViewById(R.id.textview_title_body);
            relChild = (RelativeLayout) itemView.findViewById(R.id.relativelayout_child);
            imageCheck = (ImageView) itemView.findViewById(R.id.imageCheck);
        }

        public void setVisibility(boolean isVisible) {
            RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            if (isVisible) {
                param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                param.width = LinearLayout.LayoutParams.MATCH_PARENT;
                itemView.setVisibility(View.VISIBLE);
            } else {
                itemView.setVisibility(View.GONE);
                param.height = 0;
                param.width = 0;
            }
            itemView.setLayoutParams(param);
        }
    }
}
