package org.dalol.colorsouce.presenter;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.dalol.colorsouce.R;
import org.dalol.colorsouce.constants.Constant;
import org.dalol.colorsouce.model.Menu;

import java.util.ArrayList;

/**
 * Created by Filippo-TheAppExpert on 7/6/2015.
 */
public class NavigationMenuPresenter implements Presenter {

    private MenuContract mMenuContract;

    public NavigationMenuPresenter(MenuContract menuContract) {
        mMenuContract = menuContract;
    }

    @Override
    public void initialize() {

        ArrayList<Menu> menus = new ArrayList<>();

        Menu menu = new Menu();
        menu.setTitle("Rate");
        menu.setLogo(R.mipmap.ic_rate);
        menus.add(menu);

        menu = new Menu();
        menu.setTitle("Share");
        menu.setLogo(R.mipmap.ic_share);
        menus.add(menu);

        menu = new Menu();
        menu.setTitle("About");
        menu.setLogo(R.mipmap.ic_about);
        menus.add(menu);

        for (Menu currentMenu : menus) {
            mMenuContract.onMenu(currentMenu);
        }
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    public void rate() {

        String url;
        try {
            mMenuContract.getAppCompatActivity().getPackageManager().getPackageInfo("com.android.vending", 0);
            url = "market://details?id=" + Constant.PACKAGE_NAME;
        } catch (final Exception e) {
            url = Constant.LINK_TO_APP;
        }
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        mMenuContract.rate(intent);
    }

    public void share() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Hey check out Color Souce App at: https://play.google.com/store/apps/details?id=org.dalol.colorsouce";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Color Souce");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        mMenuContract.share(sharingIntent);
    }

    public void showAbout() {


        View dialog = LayoutInflater.from(mMenuContract.getAppCompatActivity().getApplicationContext()).inflate(R.layout.dialog_layout, null);

        final AlertDialog alertDialog = new AlertDialog.Builder(mMenuContract.getAppCompatActivity())
                .setCancelable(true)
                .setView(dialog)
                .create();
        Button close = (Button) dialog.findViewById(R.id.close_button);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    public interface MenuContract {

        AppCompatActivity getAppCompatActivity();

        void onMenu(Menu menu);

        void share(Intent sharingIntent);

        void rate(Intent intent);
    }
}