package modeling;

import java.util.HashSet;

import java.util.ArrayList;

import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

/**
 * Created by tyler on 9/26/2017.
 * The game class is everything that a Game is in ticket to ride. So, you know, stuff
 */
public class Game {





    ArrayList<Player> players;

    boolean hasStarted;

    String gameID;

    String gameName;

    int playerMax;

    public Game(){
        this.hasStarted = false;
        gameID = UUID.randomUUID().toString();

        players = new ArrayList<> ();

        players = new ArrayList<>();

    }

    public int getPlayerMax() {
        return playerMax;
    }

    public void setPlayerMax(int playerMax) {
         this.playerMax = playerMax;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public boolean canJoinGame(){
        return false;
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
    public boolean addPlayer(Player p) {
        if(players.contains(p)) {
            return false;
        }
        else {
            players.add(p);
            return true;
        }
    }
    public void removePlayer(Player player) {
        players.remove(players.indexOf(player));
    }
    public String getGameID() {
        return gameID;
    }

    public boolean isHasStarted() {
        return hasStarted;
    }

}
