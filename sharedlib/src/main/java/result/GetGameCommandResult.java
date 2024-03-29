
package result;


import modeling.GameList;

/**
 * Created by tyler on 10/9/2017.
 */


public class GetGameCommandResult extends  CommandResult {

    /*
    * Will return the game result for the waiting lobby
    * */

    private GameList gameList;

    public GetGameCommandResult(boolean success, GameList gameList, String errorInfo) {
        super(success,errorInfo);
        this.gameList = gameList;
    }
    public GetGameCommandResult(boolean success, String errorInfo) {
        super(success,errorInfo);
    }

    public GameList getGameList() {
        return gameList;
    }

}

