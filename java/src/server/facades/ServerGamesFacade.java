package server.facades;

import java.util.List;

import shared.transferClasses.CreateGameRequest;
import shared.transferClasses.CreateGameResponse;
import shared.transferClasses.Game;
import shared.transferClasses.JoinGameRequest;


/**
 * A facade that executes commands associated with user login and registration
 * and games creation, joining, and listing.
 * @author djoshuac
 */
public class ServerGamesFacade implements IGamesFacade {

	@Override
	public boolean login(String username, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean register(String newUsername, String newPassword) {
		// TODO Auto-generated method stub
		return false;
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
