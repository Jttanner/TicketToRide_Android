package MVP_coms_classes;

import android.content.Context;

/**
 * Created by tyler on 10/19/2017.
 */

public interface MVP_GameHistory {
    /**
     * Required View methods available to Presenter.
     * A passive layer, responsible to show data
     * and receive user interactions
     */
    interface MapViewOps{
        /**Gets application context*/
        Context getAppContext();
        /**gets activity context*/
        Context getActivityContext();
        /**Any commands toString method is passed in here to add to the GameHistoryView*/
        void addCommandString(String c);

    }
    /**
     * Operations offered to View to communicate with Presenter.
     * Processes user interactions, sends data requests to Model, etc.
     */
    interface MapPresOps{
        /**Tells the view to exit itself*/
        void exitView();
    }
}