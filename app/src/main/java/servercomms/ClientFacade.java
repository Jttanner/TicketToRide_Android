package servercomms;

import android.util.Log;

import clientCommands.SetGameMap;
import clientModel.CModel;
import commandData.Command;
import commandData.GetCmndListDataToClient;
import modeling.User;
import result.CommandResult;
import result.GetGameCommandResult;
import result.JoinGameCommandResult;

/**
 * Created by tyler on 9/26/2017.
 * This class takes requests from the Server proxy, such as updating the game list. These operations updateWaitingRoom the model, which will
 * in turn notify the presenters of any changes made.
 */
class ClientFacade {
    private static final String TAG = "ClientFacade";
    private static ClientFacade ourInstance = new ClientFacade();

    public static ClientFacade getInstance() {
        return ourInstance;
    }

    private ClientFacade() {
    }
    /**Checks the type of command objects*/
    void checkTypeOfCommand(Command command){
        if(command.getType().equals("getCommandList")){
            GetCmndListDataToClient getCmdList = (GetCmndListDataToClient) command;
            SetGameMap setGameMap = new SetGameMap(getCmdList.getReturnCommandList(),getCmdList.getGameId());
            setGameMap.execute();
        }
    }
    /**Checks the type of command results*/
    void checkTypeOfCommand(CommandResult result) {

        if(result!=null) {
            if (result instanceof GetGameCommandResult) {
                CModel.getInstance().setAllGames(((GetGameCommandResult) result).getGameList());
            }
            //This if is for joinGame, and the result.getData is the GameID for the game we are joining
            else if (result instanceof JoinGameCommandResult) {
                CModel.getInstance().setCurrGame(((JoinGameCommandResult) result).getGame());
            }
            //TODO: Does not like this line when we stop the app, re-run it, and try to log back in
            else if(result.getType()!=null) {
                if (result.getType().equals("startGame")) {
                    CModel.getInstance().toggleGameHasStarted();
                }
            }
            else {
                Log.d(TAG, "We got a different class then expected");
            }
        }
    }

    void updateUser(User user) {
        CModel.getInstance().setMyUser(user);
    }

}