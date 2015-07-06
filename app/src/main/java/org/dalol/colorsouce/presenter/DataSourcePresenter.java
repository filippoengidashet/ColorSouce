package org.dalol.colorsouce.presenter;
/*
 * @author Filippo Engidashet
 * @version 1.0
 * @date 7/6/2015
 *
 * DataSourcePresenter.java: This class is responsible for managing the xml file and pass to the view
 */

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.support.v7.app.AppCompatActivity;

import org.dalol.colorsouce.R;
import org.dalol.colorsouce.model.Item;
import org.dalol.colorsouce.constants.XmlElements;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class DataSourcePresenter implements Presenter {

    private ViewContract mViewContract;

    public DataSourcePresenter(ViewContract viewContract) {
        mViewContract = viewContract;
    }

    @Override
    public void initialize() {

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

    public synchronized void populateItems() {
        new ParseXML(mViewContract).start();
    }

    public interface ViewContract {

        AppCompatActivity getAppCompatActivity();

        void onItem(Item item);

        void onError(String error);

        void copied(String message);
    }

    private class ParseXML extends Thread {

        private ViewContract mContract;
        private Item item = null;

        public ParseXML(ViewContract contract) {
            mContract = contract;
        }

        @Override
        public void run() {

            try {
                XmlResourceParser parser = mContract.getAppCompatActivity().getResources().getXml(R.xml.colors);
                parser.next();
                int eventType = parser.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:

                            String tagName = parser.getName();

                            if (XmlElements.COLOR.equals(tagName)) {
                                item = new Item();
                                item.setTitle(parser.getAttributeValue(null, XmlElements.NAME));
                            }
                            break;
                        case XmlPullParser.TEXT:
                            item.setIconBgColor(parser.getText());
                            break;
                        case XmlPullParser.END_TAG:
                            String endTagName = parser.getName();

                            if (!XmlElements.COLORS.equals(endTagName)) {
                                mContract.onItem(item);
                            }

                            break;
                    }

                    eventType = parser.next();
                }
            } catch (XmlPullParserException e) {
                mContract.onError(e.getMessage());
            } catch (IOException e) {
                mContract.onError(e.getMessage());
            }
        }
    }

    public void setClipboard(String color) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) mViewContract.getAppCompatActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(color);
            mViewContract.copied(color);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) mViewContract.getAppCompatActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Color name and Color code Copied", color);
            clipboard.setPrimaryClip(clip);
            mViewContract.copied(color);
        }
    }
}
