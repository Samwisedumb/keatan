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
	public List<Game> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CreateGameResponse create(CreateGameRequest makeGame) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean joinGame(JoinGameRequest requestJoin) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void verifyUserInformation(UserInfo user) throws ServerException {
		// TODO Auto-generated method stub
		
	}

}
