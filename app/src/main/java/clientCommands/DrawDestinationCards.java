package clientCommands;

import clientModel.CModel;

/**
 * Created by korea on 10/27/2017.
 */

public class DrawDestinationCards implements ClientCommand {
    //Need Player Name
    //How many he decided to keep
    String playerName;
    int numberOfCards;
    @Override
    public void execute() {

        CModel.getInstance().updateCurrGameHistoryList(this.toString());
    }

    @Override
    public String toString() {
        return playerName + " took " + numberOfCards + " destination cards";
    }
}
