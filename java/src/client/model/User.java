package client.model;

import java.util.ArrayList;
import java.util.List;

import shared.definitions.CatanColor;
import shared.transferClasses.UserInfo;
import client.data.GameInfo;
import client.data.PlayerInfo;

/**
 * A class to store all of a user's information
 * @author djoshuac
 */
public class User {
	private UserInfo userInfo;
	private CatanColor color;
	private int playerIndex;
	private GameInfo game;
	
	public User() {
		
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public CatanColor getColor() {
		return color;
	}

	public void setColor(CatanColor color) {
		this.color = color;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	
	/**
	 * Returns a plyerInfo object
	 * @return a player info object that corresponds to the current user
	 * @pre userInfo cannot be null, color cannot be null, index should be valid
	 * @post see return
	 */
	public PlayerInfo getPlayerInfo() {
		return new PlayerInfo(userInfo.getUserID(), playerIndex, userInfo.getUsernameString(), color);
	}

	/**
	 * Sets the game infor for the player
	 * @post game info is set to given<br>
	 * color is set according to game<br>
	 * playerIndex is set according to game
	 * @param game - the game to set the user to
	 */
	public void setGameInfo(GameInfo game) {
		PlayerInfo player = game.getPlayerInfoByID(userInfo.getUserID());
		setColor(player.getColor());
		setPlayerIndex(player.getIndex());
		this.game = game;
	}
	public GameInfo getGameInfo() {
		return game;
	}

	/**
	 * @post clears the current game info,<br>
	 * sets this player's index to -1<br>
	 * sets this player's color to null
	 */
	public void clearGameInfo() {
		game = null;
		playerIndex = -1;
		color = null;
	}

	/**
	 * @post updates the game object to the given transfermodel
	 * @param transferModel - the model to update to
	 * @pre transfer model must not be null, user info and game info  must be set
	 */
	public void update(TransferModel transferModel) {
		game.clearPlayers();
		
		for (Player player : transferModel.getPlayers()) {
			game.addPlayer(player.getPlayerInfo());
		}
	}
}




