package org.dalol.colorsouce.presenter;

/*
 * @author Filippo Engidashet
 * @version 1.0
 * @date 7/6/2015
 *
 * Presenter.java: This interface is the parent of all sub presenters
 */
public interface Presenter {

    void initialize();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
