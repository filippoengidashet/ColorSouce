package org.dalol.colorsouce.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.dalol.colorsouce.R;
import org.dalol.colorsouce.model.Item;

import java.util.ArrayList;
/*
 * @author Filippo Engidashet
 * @version 1.0
 * @date 6/18/2015
 *
 * DashboardAdapter.java: This class is the adapter for the color palettes of the recyclerview.
 */
public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.Holder> {

    private static final String TAG = DashboardAdapter.class.getSimpleName();
    private ItemClickListener mItemClickListener;
    private ArrayList<Item> mItems = new ArrayList<>();

    public DashboardAdapter(ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View row = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout, null);

        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {

        final Item current = mItems.get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mItemClickListener.onItemLongClick(current.getTitle(), current.getIconBgColor());
                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemSelect(position, current);
            }
        });

        TextView title = (TextView) holder.itemView.findViewById(R.id.txtColorName);
        ImageView icon = (ImageView) holder.itemView.findViewById(R.id.imgProfilePicture);

        icon.setBackgroundColor(Color.parseColor(current.getIconBgColor()));

        title.setText(current.getTitle());
        //title.setBackgroundColor(Color.parseColor(current.mTextBgColor));
    }

    public void addItem(Item item) {
        mItems.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        public Holder(View itemView) {
            super(itemView);
        }
    }

    public interface ItemClickListener {

        void onItemSelect(int position, Item item);

        void onItemLongClick(String colorName, String colorCode);
    }
}
