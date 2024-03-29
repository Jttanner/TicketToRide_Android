package ui.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import MVP_coms_classes.MVP_Map;
import clientModel.CModel;
import clientModel.MyTurn;
import modeling.Game;
import modeling.Player;
import modeling.Route;
import poller.Poller;
import presenters.MapPresenter;
import teamjapannumbahone.tickettoride.R;

/**
 * Activity which is the base for the game itself.  Holds the gameboard as the main view, with a sliding drawer for
 * to hold data relevant to the game for each player. Other views relevant to the game stem from this.
 */
public class MapActivity extends FragmentActivity implements MVP_Map.MapViewOps{
    private static final String TAG = "MapActivity";
    private RecyclerView mGameStatus;
    public MVP_Map.MapPresOps presenter;
    private Button destinationCard;
    private Button chatroom;
    private Button gameHistory;
    private Button turnStartOption;

    private MapBaseView mapBaseView;

    int counter = 0;


    /**
     * If we leave this activity, we want to stop the poller which polls the command list.
     * @pre Our instance of Poller is running a GetCommandsPoll
     * @post Our instance of Poller is no longer running a GetCommandsPoll
     */
    @Override
    protected void onDestroy() {
        //destroy poller
        Poller.getInstance().stopGetCommandsPoller();
        super.onDestroy();
    }

    @Override
    public void StartGameOver() {
        Intent intent = new Intent(this, GameOverActivity.class);
        //presenter.deleteObserver();
        startActivity(intent);
    }

    /**
     * Initializes the game by setting up the view for the game.
     * Also sets up demo button functions which updates the views appopriately.
     *
     * @param savedInstanceState could be used to save a state, not implemented yet.
     * @pre Game has just started
     * @post Game and view for the Game are both completely initialized
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        //RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_map);

        //changeTurnDisplay();
        updateMap();


        //((TextView) findViewById(R.id.num_cards_in_deck)).setText("Number of Cards in Deck: 136");

        presenter = new MapPresenter(this);

        //presenter.startGameState();
        if(CModel.getInstance().getUserPlayer()!=null && CModel.getInstance().getUserPlayer().getDestinationCards().size()==0)
        DestinationCardOption();

        SlidingPaneLayout slidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.activity_map);

        slidingPaneLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelOpened(View panel) {

            }

            @Override
            public void onPanelClosed(View panel) {

            }
        });

        turnStartOption = (Button) findViewById(R.id.turnStartOptionButton);
        turnStartOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                TurnStartOptionFragment fragment = new TurnStartOptionFragment();
                fragment.show(fm, "start");
                //Temporary Set State to MyTurn
                //CModel.getInstance().setCurrGameState(new MyTurn());
            }
        });

        gameHistory = (Button) findViewById(R.id.gameHistoryButton);
        gameHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                GameHistoryFragment fragment = new GameHistoryFragment();
                fragment.show(fm,"game_History_fragment");
            }
        });
        chatroom = (Button) findViewById(R.id.chatButton);
        chatroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                ChatFragment fragment = new ChatFragment();
                fragment.show(fm,"chat_fragment");

            }
        });

        //firstDrawableTrainCard = (ImageButton) findViewById(R.id.first_drawable_card);

        //firstDrawableTrainCard.setOnClickListener(new View.OnClickListener(){

          //  @Override
            //public void onClick(View v) {

//            }
  //      });


    //    secondDrawableTrainCard = (ImageButton) findViewById(R.id.second_drawable_card);



        //enableClaimRoute(); //
        setupView();


    }


    public void enableClaimRoute(){
        mapBaseView = (MapBaseView) findViewById(R.id.map_base_view);
        mapBaseView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    FragmentManager fm = getSupportFragmentManager();
                    ClaimRouteFragment fragment = new ClaimRouteFragment();
                    String selectedCity = mapBaseView.decodeCityTouched(event);
                    fragment.setSelectedCityName(selectedCity);
                    //TODO: Add check to the game state once it is implemented!
                    if (fragment.getSelectedCityName() != null){
                        //populate recyclerView with routes
                        fragment.show(fm, "fragment_claim_route");
                    }
                }
                return true;
            }
        });
    }

    public void disableClaimRoute(){
        mapBaseView = (MapBaseView) findViewById(R.id.map_base_view);
        mapBaseView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){

                }
                return true;
            }
        });
    }


    /**
     * Updates the TextView which states whose turn it currently is
     * @pre List<Player> in current Game object is not null
     * @pre only one Player in List<Player> in current game has isMyTurn = true
     * @post the Player whos isMyTurn = true now = false, the player after him has isMyTurn = true.
     *               (If it was the last Player on the list's turn, the Player at the beginning of the list sets isMyTurn = true)
     */
    /*
    public void changeTurnDisplay(){
        for (Player player : CModel.getInstance().getCurrGame().getPlayers()){
            if (player.isMyTurn()){
                ((TextView) findViewById(R.id.current_turn)).setText("Current Turn: " + player.getPlayerName());
            }
        }
    }
    */
    /**
     * Helper function
     * Draws a Player's color on a route claimed that Player.
     *
     * @pre the Connection object which specifies the connections between cityName1 and cityName2 has a canClaim == true;
     *
     * @post Claming Player's color has been drawn over the route he claimed if it was appopriate.
     *
     * @param cityName1 Name of first city on a route
     * @param cityName2 Name of connected city on a route
     * @param color Color of the player who is claiming the route
     * @param isDoubleRoute true if the route has more than one connection between the two cities, false otherwise
     * @param hasOneDoubleClaimed true if isDoubleRoute == true AND one of double connections has been claimed.
     */
    private void drawClaimedRoute(String cityName1, String cityName2, String color, boolean isDoubleRoute, boolean hasOneDoubleClaimed){
        MapBaseView mapBaseView = (MapBaseView) findViewById(R.id.map_base_view);
       // mapBaseView.indexCityPointMap();
        Map<String, Point> cities = mapBaseView.cityMap;

        mapBaseView.addClaimedRoute(cities.get(cityName1.toLowerCase()), cityName1.toLowerCase(), cities.get(cityName2.toLowerCase()), cityName2.toLowerCase(), color, isDoubleRoute, hasOneDoubleClaimed);
    }

    /**
     * Initializes the MapPResenter
     *
     * @pre Activity initialization state
     * @post MapPresenter has been initialized
     */
    private void setupView() {
        Log.d(TAG,"setupView");
        /* Create the Presenter; Set the Presenter as a interface to limit communication*/
        presenter = new MapPresenter(this);
    }


    /**
     * @pre Claimed RouteList in the model has changed
     * @post Newly claimed routes have been drawn on the board
     */
    @Override
    public void updateMap() {

        Map<String , List<Route>> routeList = CModel.getInstance().getCurrGame().getClaimedRouteList().getRoutesMap();
        for (Map.Entry<String, List<Route> > entry : routeList.entrySet())
        {
            Player player = null;
            List<Route> route = entry.getValue();
            Game currentGame = CModel.getInstance().getCurrGame();
            for(Player player2 : currentGame.getPlayers()){
                if(player2.getPlayerName().equals(entry.getKey())){
                    player=player2;
                }
            }
            for(Route shawn : route) {
                drawClaimedRoute(shawn.getFirstCityName(), shawn.getSecondCityName(), player.getColor(), shawn.getIsDouble(), !shawn.getFirstOfDouble());
            }
        }
    }

    /**
     * Getter for Application Context
     *
     * @return Context of the application
     */
    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }


    /**
     * Getter for MapActicty Context
     *
     * @return the MapActivity Context
     */
    @Override
    public Context getActivityContext() {
        return this;
    }

    @Override
    public void ResourceCardOption() {
        FragmentManager fm = getSupportFragmentManager();
        DrawResourceCardFragment fragment = new DrawResourceCardFragment();
        disableClaimRoute();
        fragment.show(fm,"resource_card_fragment");
    }

    @Override
    public void DestinationCardOption() {
        FragmentManager fm = getSupportFragmentManager();
        DestinationCardFragment fragment = new DestinationCardFragment();
        disableClaimRoute();
        fragment.show(fm,"destination_card_fragment");
    }

    @Override
    public void ClaimRouteOption() {
        enableClaimRoute();
    }

}
