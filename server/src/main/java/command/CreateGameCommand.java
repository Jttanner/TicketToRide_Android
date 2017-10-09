package command;

import ServerModel.ServerFacade;
import commandData.CreateGameCommandData;
import modeling.Game;
import result.CommandResult;

/**
 * Created by Hwang on 9/29/2017.
 */

public class CreateGameCommand extends CreateGameCommandData implements ICommand {

    public CreateGameCommand() {
        super();
    }

    @Override
    public CommandResult execute() {


        boolean gameCreatedSuccessful = ServerFacade.getInstance().createGame(getGameObject());
        if (gameCreatedSuccessful) {
            CommandResult result = new CommandResult(true, "", "");
            result.setType("createGame");
            return result;
        } else {
            CommandResult result = new CommandResult(false, "", "");
            result.setType("createGame");
            return result;
        }
    }
}
