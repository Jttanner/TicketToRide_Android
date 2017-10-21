package presenters;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import MVP_coms_classes.MVP_PlayerStats;
import clientModel.CModel;
import modeling.Game;
import modeling.GameList;
import modeling.Player;
import ui.views.PlayerColumns;

/**
 * Created by tyler on 10/20/2017.
 */

public class PlayerStatsPresenter implements MVP_PlayerStats.PresOps,Observer{
    private WeakReference<MVP_PlayerStats.ViewOps> myView;



    public PlayerStatsPresenter(MVP_PlayerStats.ViewOps view) {
        myView = new WeakReference<>(view);
        CModel.getInstance().addObserver(this);
    }

    @Override
    public ArrayList<PlayerColumns> getPlayerColumns() {
        List<Player> players = CModel.getInstance().getCurrGame().getPlayers();

        ArrayList<PlayerColumns> columns = new ArrayList<>();
        //create strings out of the stats of each player for the recycler
        for(Player player : players){
            columns.add(new PlayerColumns(player.toString()));
        }
        return columns;
    }

    /**This will update the map view according to appropriate changes in the model*/
    @Override
    public void update(Observable o, Object arg) {
        //TODO do we really need to poll for the whole gamelist here? why not just the currentGame?
        if(arg instanceof GameList){
            for (Game g:((GameList) arg).getGames()) {
                //if the same game
                //TODO check to see if the status stuff is actually different before redoing the whole recycler view
                if (CModel.getInstance().getCurrGame().getGameID().equals(g.getGameID())){
                    myView.get().updatePlayerStats(g);
                }
            }
        }
        //ex, game won,player attributes changed(routeClaimed),
    }
}