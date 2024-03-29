package clientCommands;

import java.util.List;

import clientModel.CModel;
import commandData.DrawDestinationCardCommandData;
import modeling.DestinationCard;
import modeling.Player;

/**
 * Created by ahwang13 on 11/18/17.
 */

public class DrawDestinationCards implements ClientCommand {
    //Need Player Name
    //How many he decided to keep
    String playerName;
    //int numberOfCards;
    String gameID;
    List<DestinationCard> cardsReturned;

    public DrawDestinationCards(DrawDestinationCardCommandData data) {
        this.playerName = data.getPlayerName();
        this.gameID = data.getGameID();
        this.cardsReturned = data.getDrawDestinationCards();
    }

    @Override
    public void execute() {

        CModel.getInstance().updateCurrGameHistoryList(this.toString(), CModel.getInstance().getCurrGame().getGameID());
        if (CModel.getInstance().getCurrGame().getGameID().equals(gameID)) {
            if (CModel.getInstance().getUserPlayer().getPlayerName().equals(playerName)) {
                CModel.getInstance().getUserPlayer().addtoTemporaryHand(cardsReturned);
                Player player = CModel.getInstance().getCurrGame().getPlayer(playerName);
                CModel.getInstance().setThreeDestinationCards(player);
            }
        }



        //CModel.getInstance().getCurrGame().getPlayer(playerName).addDestinationCards(cardsReturned);
        //CModel.getInstance().setCurrGame();

        //Add card to player on Client Side and deletes that card from the deck
        //CModel.getInstance().getCurrGame().getPlayer(playerName).addDestinationCard(cardsClaimed);
        //CModel.getInstance().getCurrGame().getDestinationCardList().removeDestinationCards();
        //CModel.getInstance().updatePlayerStatsView();
        //Change the face up card on Client Side
        //CModel.getInstance().upDateFaceUpPile();
    }

    @Override
    public String toString() {
        return playerName + " drew 3 destination cards";
    }
}