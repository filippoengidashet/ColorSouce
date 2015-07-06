package org.dalol.colorsouce.model;

/**
 * Created by Filippo-TheAppExpert on 6/18/2015.
 */
public class Item {

    private String mTitle, mTextBgColor, mIconBgColor;

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setTextBgColor(String textBgColor) {
        mTextBgColor = textBgColor;
    }

    public void setIconBgColor(String iconBgColor) {
        mIconBgColor = iconBgColor;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getTextBgColor() {
        return mTextBgColor;
    }

    public String getIconBgColor() {
        return mIconBgColor;
    }
}
