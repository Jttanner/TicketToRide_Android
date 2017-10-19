package MVP_coms_classes;

import android.content.Context;

import java.util.List;

import modeling.DestinationCard;

/**
 * Created by tyler on 10/19/2017.
 */

public interface MVP_DestCard {
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
        /**replaces destination cards drawn*/
        void addFaceUpCard(DestinationCard c);

    }
    /**
     * Operations offered to View to communicate with Presenter.
     * Processes user interactions, sends data requests to Model, etc.
     */
    interface MapPresOps{
        /**Sends the presenter what destination cards were picked*/
        void pickDestCards(List<DestinationCard> cards) ;
    }
}
