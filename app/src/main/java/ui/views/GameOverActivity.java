package ui.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Adapters.GameOverListAdapter;
import MVP_coms_classes.MVP_GameOver;
import clientModel.CModel;
import clientModel.RouteCalc;
import modeling.DestinationCard;
import modeling.Player;
import teamjapannumbahone.tickettoride.R;

/**
 * Created by ACL1 on 11/12/2017.
 */

public class GameOverActivity extends FragmentActivity implements MVP_GameOver.GameOverActivity{

    TextView WinningInfo;
    TextView WinningPlayer;
    RecyclerView recyclerView;
    List<Player> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);
        SetUp();
    }

    private void SetUp(){
        WinningInfo = (TextView) findViewById(R.id.Winning_Info);
        WinningPlayer = (TextView) findViewById(R.id.WinningPlayer);

        recyclerView = (RecyclerView) findViewById(R.id.gameoverlist);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        list =CModel.getInstance().getCurrGame().getPlayers();



        SetLongestRoute(list);
        RouteCalc routeCalc = new RouteCalc();
        for(Player player: list) {

            List<DestinationCard> cards = player.getDestinationCards();
            for(DestinationCard card : cards) {
                if(routeCalc.isDestinationCardComplete(card,player.getRoutes())){
                    player.setPoints(player.getPoints() + card.getPoints());
                }
                else {
                    player.setPoints(player.getPoints() - card.getPoints());
                }
            }
        }
        recyclerView.setLayoutManager(layoutManager);
        GameOverListAdapter adapter = new GameOverListAdapter(list);
        recyclerView.setAdapter(adapter);

        int max = -100;
        Player winner = null;
        for (Player player1 : list) {
            if (player1.getPoints() > max){
                max = player1.getPoints();
                winner = player1;
            }
        }
        if (winner != null) {
            WinningPlayer.setText("The winner is " + winner.getPlayerName() + " with " + winner.getPoints() + "points!");
        }
     //   CModel.getInstance().ClearGame();
    }

    @Override
    public void SetUpWinScreen(List<Player> players) {

    }

    @Override
    public void SetLongestRoute(List<Player> players) {
        RouteCalc calc = new RouteCalc();
        Player player = calc.findLongestRoute(players);
        String longestPath = player.getPlayerName() + " has the longest path and receives 10 extra points.";
        for(Player player1 : players){
            if(player.getPlayerName().equals(player1.getPlayerName())){
                player1.setPoints(player1.getPoints()+10);
            }
        }
        /*String longestPath = player.getPlayerName() + " has won the game with the longest path with "
                + player.getPoints() + " points";*/
        WinningInfo.setText(longestPath);
    }
}
