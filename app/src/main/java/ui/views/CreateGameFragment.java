package ui.views;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import clientModel.CModel;
import modeling.Game;
import modeling.Player;
import presenters.GameListPresenter;
import teamjapannumbahone.tickettoride.R;

/**
 * Created by LabUser on 10/4/2017.
 */

public class CreateGameFragment extends DialogFragment {
    Spinner spinner;
    EditText gameName;
    Game game;
    Player player = new Player(CModel.getInstance().getMyUser().getUserID());
    Button buttonRed;
    Button buttonGreen;
    Button buttonYellow;
    Button buttonBlue;
    Button buttonCancel;
    Button buttonSubmit;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        game = new Game();
        game.setPlayerMax(2);
        player.setColor("red");
        View v = inflater.inflate(R.layout.fragment_creategame, container, false);
        setUp(v);

        return null;
    }
    private void setUp(View v){
        gameName = (EditText) v.findViewById(R.id.gameName);
        gameName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                game.setGameName(s.toString());
            }
        });

        List<String> number = new ArrayList<>();
        number.add("2");
        number.add("3");
        number.add("4");
        number.add("5");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,number);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) v.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String size = (String) adapterView.getItemAtPosition(position);
                game.setPlayerMax(Integer.parseInt(size));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonBlue = (Button) v.findViewById(R.id.ColorBlue);
        buttonGreen = (Button) v.findViewById(R.id.ColorGreen);
        buttonRed = (Button) v.findViewById(R.id.ColorRed);
        buttonYellow = (Button) v. findViewById(R.id.ColorYellow);
        buttonCancel = (Button) v.findViewById(R.id.buttonCancel);
        buttonSubmit = (Button) v.findViewById(R.id.buttonSubmit);

        wireUp();
    }
    void wireUp(){
        buttonBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setColor("blue");
            }
        });
        buttonRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setColor("red");
            }
        });
        buttonYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setColor("yellow");
            }
        });
        buttonGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setColor("green");
            }
        });
        buttonSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                game.addPlayer(player);
                GameListPresenter presenter = new GameListPresenter();
                presenter.CreateGame(game);

            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getDialog().dismiss();

            }
        });
    }
}