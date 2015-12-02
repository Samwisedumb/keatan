package server.facades;

import shared.exceptions.ServerException;
import shared.transferClasses.AcceptTrade;
import shared.transferClasses.BuildCity;
import shared.transferClasses.BuildRoad;
import shared.transferClasses.BuildSettlement;
import shared.transferClasses.BuyDevCard;
import shared.transferClasses.CreateGameRequest;
import shared.transferClasses.CreateGameResponse;
import shared.transferClasses.DiscardCards;
import shared.transferClasses.FinishTurn;
import shared.transferClasses.MaritimeTrade;
import shared.transferClasses.Monopoly;
import shared.transferClasses.Monument;
import shared.transferClasses.OfferTrade;
import shared.transferClasses.RoadBuilding;
import shared.transferClasses.RobPlayer;
import shared.transferClasses.RollNumber;
import shared.transferClasses.SendChat;
import shared.transferClasses.Soldier;
import shared.transferClasses.YearOfPlenty;
import client.data.GameInfo;

/**
 * Server Facade that handles all "moves" commands for all games
 */
public interface IMovesFacade {

	public CreateGameResponse createGame(CreateGameRequest createGame);
	
	/**
	 * @pre Somebody has accepted a trade
	 * @param gameID the ID of the game
	 * @param accept the information regarding the trade
	 * @post the trade is accepted
	 */
	public void acceptTrade(int gameID, AcceptTrade accept);
	
	/**
	 * @pre Somebody wants to build a city
	 * @param gameID the ID of the game
	 * @param build the information about where to build the city (and its owner)
	 * @post the city is built
	 */
	public void buildCity(int gameID, BuildCity build);
	
	/**
	 * @pre Somebody wants to build a road
	 * @param gameID the ID of the game
	 * @param build the information about where to build the road (and its owner)
	 * @throws ServerException 
	 * @post the road is built
	 */
	public void buildRoad(int gameID, BuildRoad build) throws ServerException;
	
	/**
	 * @pre Somebody wants to build a settlement
	 * @param gameID the ID of the game
	 * @param build the information about where to build the settlement (and its owner)\
	 * @post the settlement is built
	 */
	public void buildSettlement(int gameID, BuildSettlement build);
	
	/**
	 * @pre Somebody wants to buy a devcard
	 * @param gameID the ID of the game
	 * @param buy the information about who wants to buy the devcard
	 * @post the player buys the dev card
	 */
	public void buyDevCard(int gameID, BuyDevCard buy);
	
	/**
	 * @pre Somebody has to discard cards
	 * @param gameID the ID of the game
	 * @param discard the cards to discard and who is discarding them
	 * @post the player discards the cards
	 */
	public void discardCards(int gameID, DiscardCards discard);
	
	/**
	 * @pre Somebody ends their turn
	 * @param gameID the ID of the game
	 * @param end information about who ended the turn
	 * @post the game goes to the next player's turn
	 */
	public void finishTurn(int gameID, FinishTurn end);
	
	/**
	 * @pre Somebody does maritime trade
	 * @param gameID the ID of the game
	 * @param trade the requested resource, the desired resource, and the player doing the trade
	 * @post the player completes the trade
	 */
	
	public void maritimeTrade(int gameID, MaritimeTrade trade);
	
	/**
	 * @pre A player plays a monopoly dev card
	 * @param gameID the ID of the game
	 * @param monoploy the player who played the card, the desired monopoly
	 * @post the player gets all of the desired resource
	 */
	public void monopoly(int gameID, Monopoly monoploy);
	
	/**
	 * @pre A player plays a monument dev card
	 * @param gameID the ID of the game
	 * @param monument the player who played the monument card
	 * @post the player gets a victory point
	 */
	public void monument(int gameID, Monument monument);

	/**
     * @pre A player offers a trade
     * @post All players begin trading as described in the rules
     * @param gameID The ID of the game to modify
     * @param plenty The transfer object containing relevant data
     */
	public void offerTrade(int gameID, OfferTrade offer);
	
     /**
     * @pre A player plays a Road Building card
     * @post The player places two roads as described in the rules
     * @param gameID The ID of the game to modify
     * @param plenty The transfer object containing relevant data
     */
	public void roadBuilding(int gameID, RoadBuilding roadBuild);
	
     /**
     * @pre A player plays a soldier or rolls a seven
     * @post The player steals a card as described in the rules
     * @param gameID The ID of the game to modify
     * @param plenty The transfer object containing relevant data
     */
	public void robPlayer(int gameID, RobPlayer robbery);
	
     /**
     * @pre A player begins their turn by rolling the dice
     * @post All players receive resources as described in the rules
     * @param gameID The ID of the game to modify
     * @param plenty The transfer object containing relevant data
     */
	public void rollNumber(int gameID, RollNumber roll);
	
     /**
     * @pre A player sends a chat message
     * @post The chat log updates to include the new message
     * @param gameID The ID of the game to modify
     * @param plenty The transfer object containing relevant data
     */
	public void sendChat(int gameID, SendChat chat);
	
     /**
     * @pre A player plays a Soldier card
     * @post The player moves the robber and robs a player as described in the rules
     * @param gameID The ID of the game to modify
     * @param plenty The transfer object containing relevant data
     */
	public void soldier(int gameID, Soldier soldier);
	
     /**
     * @pre A player plays a Year Of Plenty card
     * @post The player receives resources as described in the rules
     * @param gameID The ID of the game to modify
     * @param plenty The transfer object containing relevant data
     */
	public void yearOfPlenty(int gameID, YearOfPlenty plenty);
}
