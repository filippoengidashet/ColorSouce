package org.dalol.colorsouce.activity;
/*
 * Copyright (c) 2014 Filippo Engidashet <filippo.eng@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.ActionClickListener;

import org.dalol.colorsouce.R;
import org.dalol.colorsouce.adapter.DashboardAdapter;
import org.dalol.colorsouce.adapter.DrawerMenuAdapter;
import org.dalol.colorsouce.custom.DividerItemDecoration;
import org.dalol.colorsouce.model.Item;
import org.dalol.colorsouce.model.Menu;
import org.dalol.colorsouce.presenter.DataSourcePresenter;
import org.dalol.colorsouce.presenter.NavigationMenuPresenter;

/*
 * @author Filippo Engidashet
 * @version 1.0
 * @date 6/18/2015
 *
 * MainActivity.java: This class is the base activity for all views.
 */
public class MainActivity extends AppCompatActivity implements DataSourcePresenter.ViewContract, DashboardAdapter.ItemClickListener, DrawerMenuAdapter.MenuClickListener, NavigationMenuPresenter.MenuContract {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView, mRecyclerViewMenu;
    private DashboardAdapter mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private DataSourcePresenter mDataSourcePresenter;
    private NavigationMenuPresenter mNavigationMenuPresenter;
    private DrawerMenuAdapter mMenuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDataSourcePresenter = new DataSourcePresenter(MainActivity.this);
        mNavigationMenuPresenter = new NavigationMenuPresenter(this);
        configViews();
        mDataSourcePresenter.populateItems();
        mNavigationMenuPresenter.initialize();
    }

    private void configViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //getSupportActionBar().setTitle("Title");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };

        //Navigation Drawer menu List
        mRecyclerViewMenu = (RecyclerView) findViewById(R.id.drawerMenu);
        mRecyclerViewMenu.setHasFixedSize(true);
        mRecyclerViewMenu.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewMenu.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerViewMenu.setLayoutManager(new LinearLayoutManager(this));

        mMenuAdapter = new DrawerMenuAdapter(MainActivity.this);
        mRecyclerViewMenu.setAdapter(mMenuAdapter);

        //Color Dashboard
        mRecyclerView = (RecyclerView) findViewById(R.id.colorDashboard);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        mAdapter = new DashboardAdapter(MainActivity.this);
        mRecyclerView.setAdapter(mAdapter);


        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelect(int position, final Item item) {

        SnackbarManager.show(
                Snackbar.with(getApplicationContext())
                        .text("Color Code :: " + item.getIconBgColor())
                        .actionLabel("Copy")
                        .actionColor(Color.CYAN)
                        .actionListener(new ActionClickListener() {
                            @Override
                            public void onActionClicked(Snackbar snackbar) {
                                mDataSourcePresenter.setClipboard(item.getIconBgColor());
                            }
                        })
                , this);
    }

    @Override
    public void onItemLongClick(String colorName, String colorCode) {
        mDataSourcePresenter.setClipboard(colorName + ", " + colorCode);
    }

    @Override
    public AppCompatActivity getAppCompatActivity() {
        return MainActivity.this;
    }

    @Override
    public void onMenu(Menu menu) {
        mMenuAdapter.addMenu(menu);
    }

    @Override
    public void share(Intent sharingIntent) {
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    public void rate(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void onItem(Item item) {
        mAdapter.addItem(item);
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void copied(String message) {
        Toast.makeText(getApplicationContext(), "Color Copied!! " + message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMenuItemSelect(int position, String item) {

        switch (position) {
            case 0:
                mNavigationMenuPresenter.rate();
                break;
            case 1:
                mNavigationMenuPresenter.share();
                break;
            case 2:
                mNavigationMenuPresenter.showAbout();
                break;
            default:
                Toast.makeText(getApplicationContext(), "Invalid option selected!", Toast.LENGTH_SHORT).show();
        }
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }
}
