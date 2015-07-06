package org.dalol.colorsouce.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.dalol.colorsouce.R;
import org.dalol.colorsouce.model.Menu;

import java.util.ArrayList;
/*
 * @author Filippo Engidashet
 * @version 1.0
 * @date 6/18/2015
 *
 * DrawerMenuAdapter.java: This class is the adapter for the navigation drawer items of recyclerview.
 */
public class DrawerMenuAdapter extends RecyclerView.Adapter<DrawerMenuAdapter.Holder> {

    private static final String TAG = DrawerMenuAdapter.class.getSimpleName();
    private MenuClickListener mMenuClickListener;
    private ArrayList<Menu> mItems = new ArrayList<>();

    public DrawerMenuAdapter(MenuClickListener menuClickListener) {
        mMenuClickListener = menuClickListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_layout, null);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {

        final Menu current = mItems.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMenuClickListener.onMenuItemSelect(position, current.getTitle());
            }
        });

        TextView title = (TextView) holder.itemView.findViewById(R.id.menuTitle);
        ImageView icon = (ImageView) holder.itemView.findViewById(R.id.menuIcon);

        title.setText(current.getTitle());
        icon.setImageResource(current.getLogo());
        //icon.setImageResource(R.drawable.abc_ic_search_api_mtrl_alpha);

    }

    public void addMenu(Menu menu) {
        mItems.add(menu);
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

    public interface MenuClickListener {

        void onMenuItemSelect(int position, String item);
    }
}
