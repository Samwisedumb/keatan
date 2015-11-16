package server.facades;

import java.util.List;

import shared.transferClasses.CreateGameRequest;
import shared.transferClasses.CreateGameResponse;
import shared.transferClasses.Game;
import shared.transferClasses.JoinGameRequest;

public class MockGameFacade implements IGamesFacade{

	@Override
	public boolean login(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void register(String newUsername, String newPassword) {
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

}
