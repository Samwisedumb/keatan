package client.join;

import shared.definitions.CatanColor;
import shared.exceptions.ServerException;
import shared.transferClasses.CreateGameRequest;
import shared.transferClasses.Game;
import shared.transferClasses.JoinGameRequest;
import client.base.Controller;
import client.base.IAction;
import client.data.GameInfo;
import client.data.PlayerInfo;
import client.misc.IMessageView;
import client.model.ModelFacade;
import client.model.TransferModel;
import client.server.ServerProxy;


/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements IJoinGameController {

	private INewGameView newGameView;
	private ISelectColorView selectColorView;
	private IMessageView messageView;
	private IAction joinAction;
	
	/**
	 * JoinGameController constructor
	 * 
	 * @param view Join game view
	 * @param newGameView New game view
	 * @param selectColorView Select color view
	 * @param messageView Message view (used to display error messages that occur while the user is joining a game)
	 */
	public JoinGameController(IJoinGameView view, INewGameView newGameView, 
								ISelectColorView selectColorView, IMessageView messageView) {

		super(view);

		setNewGameView(newGameView);
		setSelectColorView(selectColorView);
		setMessageView(messageView);
		
		ModelFacade.addObserver(this);
	}
	
	public IJoinGameView getJoinGameView() {
		
		return (IJoinGameView)super.getView();
	}
	
	/**
	 * Returns the action to be executed when the user joins a game
	 * 
	 * @return The action to be executed when the user joins a game
	 */
	public IAction getJoinAction() {
		
		return joinAction;
	}

	/**
	 * Sets the action to be executed when the user joins a game
	 * 
	 * @param value The action to be executed when the user joins a game
	 */
	public void setJoinAction(IAction value) {	
		
		joinAction = value;
	}
	
	public INewGameView getNewGameView() {
		
		return newGameView;
	}

	public void setNewGameView(INewGameView newGameView) {
		
		this.newGameView = newGameView;
	}
	
	public ISelectColorView getSelectColorView() {
		
		return selectColorView;
	}
	public void setSelectColorView(ISelectColorView selectColorView) {
		
		this.selectColorView = selectColorView;
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	public void setMessageView(IMessageView messageView) {
		
		this.messageView = messageView;
	}

	
	
	
	/**
	 * Shows an error dialog message to the user.
	 * @pre messageView cannot be null, errorMessage cannot be null
	 * @post the given errorMessage is displayed in a modal dialog
	 * @param errorMessage - the message to display in the dialog
	 */
	private void showModalError(String errorMessage) {
		messageView.setMessage(errorMessage);
		messageView.setTitle("Error");
		messageView.showModal();
	}
	
	/**
	 * Refreshes the game list in the join game view
	 */
	private void refreshGameList() {
		try {
			Game[] gameArray = ServerProxy.getGamesList();
			GameInfo[] games = new GameInfo[gameArray.length];
			for (int i = 0; i < games.length; i++) {
				games[i] = new GameInfo(gameArray[i]);
			}
			getJoinGameView().setGames(games, new PlayerInfo());
		}
		catch (ServerException e) {
			showModalError(e.getReason());
		}
	}
	
	@Override
	public void start() {
		refreshGameList();
		getJoinGameView().showModal();
	}

	@Override
	public void startCreateNewGame() {
		getNewGameView().showModal();
	}

	@Override
	public void cancelCreateNewGame() {
		refreshGameList();
		getNewGameView().closeModal();
	}

	@Override
	public void createNewGame() {
		boolean randHex = getNewGameView().getRandomlyPlaceHexes();
		boolean randChits = getNewGameView().getRandomlyPlaceNumbers();
		boolean randPorts = getNewGameView().getUseRandomPorts();
		String gameTitle = getNewGameView().getTitle();
		
		if (gameTitle == null || gameTitle.equals("")) {
			showModalError("Input a title for the game");
		}
		else {
			try {
				ServerProxy.createGame(new CreateGameRequest(randHex, randChits, randPorts, gameTitle));
				refreshGameList();
				getNewGameView().closeModal();
			}
			catch (ServerException e) {
				showModalError(e.getReason());
			}
		}
	}

	private GameInfo gameToJoin;
	
	@Override
	public void startJoinGame(GameInfo game) {
		gameToJoin = game;
		getSelectColorView().showModal();
	}

	@Override
	public void cancelJoinGame() {
		gameToJoin = null;
		refreshGameList();
		getSelectColorView().closeModal();
	}

	@Override
	public void joinGame(CatanColor color) {
		try {
			Game game = ServerProxy.joinGame(new JoinGameRequest(gameToJoin.getId(), color));
			// If join succeeded
			ModelFacade.setGameInfo(new GameInfo(game));
			
			getSelectColorView().closeModal();
			getJoinGameView().closeModal();
			
			joinAction.execute();
		}
		catch (ServerException e) {
			showModalError(e.getReason());
		}
	}

	@Override
	public void update() {
		// do nothing		
	}
}

