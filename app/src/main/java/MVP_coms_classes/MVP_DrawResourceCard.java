package MVP_coms_classes;

import modeling.ResourceCard;

/**
 * Created by korea on 11/8/2017.
 */

public interface MVP_DrawResourceCard {

    //Allow presenter to Update the View
     interface DrawResourceCardViewOps {
        void upDateFaceUp();
        void close();
        void lock();
        void unlock();
        void noCards();
        void ButtonsOn();
        void ButtonsOff();
    }

    //Processes User Input
     interface DrawResourceCardPresOps {
        void drawCard(ResourceCard resourceCard);
        void shuffleDiscardPile();
    }
}
