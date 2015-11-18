package server.facades;

import java.util.List;

import shared.exceptions.ServerException;
import shared.transferClasses.CreateGameRequest;
import shared.transferClasses.CreateGameResponse;
import shared.transferClasses.Game;
import shared.transferClasses.JoinGameRequest;
import shared.transferClasses.Password;
import shared.transferClasses.UserInfo;
import shared.transferClasses.Username;

public class MockGameFacade implements IGamesFacade{

	@Override
	public UserInfo loginUser(Username username, Password password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerUser(Username username, Password password) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Game> listGames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CreateGameResponse createGame(String gameTitle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void joinGame(UserInfo user, JoinGameRequest requestJoin) {
		// TODO Auto-generated method stub
	}

	@Override
	public void verifyUserInformation(UserInfo user) {
		// TODO Auto-generated method stub
		
	}

}
