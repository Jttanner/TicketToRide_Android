package ui.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import Adapters.PlayerListAdapter;
import MVP_coms_classes.MVP_WaitingRoom;
import clientModel.CModel;
import modeling.Game;
import presenters.WaitingRoomPresenter;
import teamjapannumbahone.tickettoride.R;

/**
 * Created by korea on 10/6/2017.
 */

public class WaitingRoomActivity extends AppCompatActivity implements MVP_WaitingRoom.RequiredViewOps {
    private static final String TAG = "WaitingRoomActivity";
    private Context mContext = this;
    //Button StartGameButton;
    private Button StartGameButton;
    private WaitingRoomPresenter mPresenter;
    private ListView playerListView;
    private PlayerListAdapter playerListAdapter;// = new PlayerListAdapter(this,CModel.getInstance().getCurrGame().getPlayers());


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG,"OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitingroom);
        setupMVP();
        wireUp();

    }

    private void setupMVP() {
        Log.d(TAG,"setupMVP");
        /* Create the Presenter; Set the Presenter as a interface to limit communication*/
        mPresenter = new WaitingRoomPresenter(this);
        playerListAdapter = new PlayerListAdapter(this,CModel.getInstance().getCurrGame().getPlayers());
    }
    void wireUp(){
        Log.d(TAG,"wireUp");
        // StartGameButton = (Button) findViewById(R.id.StartGameButton);
        StartGameButton = (Button) findViewById(R.id.waitingRoom_StartGame);
        StartGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if it is valid. Go to new activity
                Toast.makeText(mContext, "Start Game Success", Toast.LENGTH_SHORT).show();

            }
        });
        playerListView = (ListView) findViewById(R.id.waitingRoom_PlayerList);
        playerListView.setAdapter(playerListAdapter);
    }
    @Override
    public void updateWaitingRoom(Game game) {
        Log.d(TAG,"updateWaitingRoom");
        playerListAdapter.setPlayerList(game.getPlayers());
        playerListAdapter.notifyDataSetChanged();
        //playerListAdapter = new PlayerListAdapter(this,game.getPlayers());
        //playerListView.setAdapter(playerListAdapter);
    }
}