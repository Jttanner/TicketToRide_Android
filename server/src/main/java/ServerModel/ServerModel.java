package ServerModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import commandData.ChatCommandData;
import commandData.Command;
import modeling.Game;
import modeling.GameList;
import modeling.Player;
import modeling.User;
import modeling.UserInfoList;

/**
 * Serves as the root of the server where information and algorithms are stored. Accesses important data such as the list of Destination Cards,
 * list of commands, list of games, list of user info, and chat history.
 * Created by jontt on 9/27/2017.
 */

public class ServerModel {

    /*
    This holds all the information and calculations needed for the server.
     */
    private int delta_n = 0;
    private static ServerModel instance = new ServerModel();
    private Map<String, User> users = new HashMap<>(); //Key=UserName
    //private Map<String, Game> games = new HashMap<>(); //Key=gameID
    private Map<String, List<Command>> commandListMap = new HashMap<>();
    //private DestinationCardList destinationCardList = new DestinationCardList();
    private GameList gameList = new GameList();
    private UserInfoList userInfoList = new UserInfoList();
    private List<String> chatHistory = new ArrayList<>();

    /**Our Persistence manager object
    private IPersistenceManager pManager;*/
    /**Our current plugin object*/
    private IPlugin currPlugin;

    public void zeroOut(String playerID){
        for(Game game : gameList.getGames()){
            for(Player player : game.getPlayers()){
                if(player.getPlayerName().equals(playerID)){
                    player.setCommandIndex(0);
                }
            }
        }
    }

    public String checkUserInGame(String userId){
        if(gameList != null){
            List<Game> games = gameList.getGames();
            for(Game game : games){
                for(Player player : game.getPlayers()){
                    if(userId.equals(player.getPlayerName())){
                        return game.getGameID();
                    }
                }
            }
        }
        return "no";
    }

    public List<String> getChatHistory() {
        return chatHistory;
    }

    public List<Command> returnCommand = new ArrayList<>();

    public List<Command> getReturnCommand() {
        return returnCommand;
    }

    public void setReturnCommand(List<Command> returnCommand) {
        this.returnCommand = returnCommand;
    }

    public void setChatHistory(List<String> chatHistory) {
        this.chatHistory = chatHistory;
    }

    public ServerModel() {
    }

    public static ServerModel getInstance()
    {
        if(instance == null){
            instance = new ServerModel();
        }
        return instance;
    }

    User register(String userName, String password){ //If register succeeds, it'll give us back a new user object
        User user = userInfoList.register(userName, password);
        currPlugin.saveUser(user);
        return user;
    }

    User login(String userName, String password){
        //If the account exists and matches with one in the database...
        //User user = new User(userInfoList.login(userName, password));
        User myUser = currPlugin.verifyUser(userName, password);
        if(myUser.getUserName().equals(userName) && myUser.getInfo().getPassword().equals(password)){
            userInfoList.login(userName, password);
        }
        return myUser;
    }

    boolean createGame(Game newGame){
        //if the game has not already been made
        return gameList.addGame(newGame);
    }

    Game joinGame(User user, String gameID){
        return gameList.joinGame(user, gameID);
    }

    /*boolean startGame(String gameID){;
        commandListMap.put(gameID,new ArrayList<Command>());
        return gameList.startGame(gameID);
    }*/

    boolean deleteGame(Game game){
        return gameList.deleteGame(game);
    }

    /*
    public boolean leaveGame(Game game, Player player){
        return gameList.leaveGame(game);
    }
    */

    //(userName, User)
    public Map<String, User> getUsers() {
        return users;
    }

    //(gameID, Game)

    void addChatHistory(String s,String gameId){/*public Map<String, Game> getGamesAsMap(){
        return games;
    }*/

        gameList.findGame(gameId).getChatHistory().add(s);

        ChatCommandData chatCommandData = new ChatCommandData(gameList.findGame(gameId).getChatHistory(),gameId);
        ServerModel.getInstance().getReturnCommand().add(chatCommandData);
        ServerFacade.getInstance().addCommandToList(gameId, chatCommandData);
    }

    //Takes in the current list of games and returns a list of games that haven't started yet
    GameList getGames() {
                return this.gameList;
    }

    public Map<String, List<Command>> getCommandListMap() {
        return commandListMap;
    }

    public IPlugin getPlugin(){
        return currPlugin;
    }

    /**
     * @param fileName The PluginName
     * @param n "n" save integer*/
    public void saveArgs(String fileName, String n) {
        Loader loader = new Loader();
        ArrayList<String> fileArgs = loader.readFile(fileName);

        //save the persistence manager and the plugin
        if(fileArgs != null) {
            try{
                currPlugin = (IPlugin) loader.loadClass(fileName, fileArgs.get(0));
            }catch (Exception e){
                e.printStackTrace();
            }

            IPersistenceManager persistenceManager = PluginRegistry.getInstance().create(fileName, fileArgs.get(1));
            currPlugin.setPManager(persistenceManager);
        }
        else {
            System.out.println("Something went horribly wrong");
        }
        delta_n = Integer.parseInt(n);
    }


    public int getDelta_n() {
        return delta_n;
    }

    public void redoServerModel() {
        List<User> allUsers = currPlugin.getAllUsers();
        Map<String,List<Command> commands = currPlugin.getAllCommands();
        List<Game> allGames = currPlugin.getAllGames();
    }
}