package client.server;

import java.io.FileNotFoundException;
import java.io.FileReader;

import shared.exceptions.ServerException;
import shared.json.Converter;
import shared.transferClasses.AcceptTrade;
import shared.transferClasses.AddAIRequest;
import shared.transferClasses.BuildCity;
import shared.transferClasses.BuildRoad;
import shared.transferClasses.BuildSettlement;
import shared.transferClasses.BuyDevCard;
import shared.transferClasses.CreateGameRequest;
import shared.transferClasses.CreateGameResponse;
import shared.transferClasses.DiscardCards;
import shared.transferClasses.FinishTurn;
import shared.transferClasses.Game;
import shared.transferClasses.JoinGameRequest;
import shared.transferClasses.MaritimeTrade;
import shared.transferClasses.Monopoly;
import shared.transferClasses.Monument;
import shared.transferClasses.OfferTrade;
import shared.transferClasses.RoadBuilding;
import shared.transferClasses.RobPlayer;
import shared.transferClasses.RollNumber;
import shared.transferClasses.SendChat;
import shared.transferClasses.Soldier;
import shared.transferClasses.UserCredentials;
import shared.transferClasses.UserInfo;
import shared.transferClasses.YearOfPlenty;
import client.model.TransferModel;

public class MockServer implements IServer  {

	@Override
	public TransferModel getModel(int version) throws ServerException {
		FileReader fileReader;
		try {
			fileReader = new FileReader("Utilities/MockServerTransferModel");
			return Converter.fromJson(fileReader, TransferModel.class);
		}
		catch (FileNotFoundException e) {
			System.err.println("Failed to load file: " + "Utilities/MockServerTransferModel");
			return null;
		}
	}
	
	@Override
	public UserInfo login(UserCredentials userCredentials) throws ServerException {
		
		return null;
	}

	@Override
	public void register(UserCredentials userCredentials)
			throws ServerException {
		
		
	}

	@Override
	public Game[] getGamesList() throws ServerException {
		
		return null;
	}

	@Override
	public CreateGameResponse createGame(CreateGameRequest createGameRequest)
			throws ServerException {
		
		return null;
	}

	@Override
	public Game joinGame(JoinGameRequest joinGameRequest) throws ServerException {
		
		return null;
	}

	@Override
	public void addAI(AddAIRequest addAIRequest) throws ServerException {
		
		
	}

	@Override
	public String[] listAITypes() throws ServerException {
		
		return null;
	}

	@Override
	public void sendChat(SendChat sendChat) throws ServerException {
		
		return;
	}

	@Override
	public void rollDice(RollNumber rollNumber) throws ServerException {
		
		return;
	}

	@Override
	public void robPlayer(RobPlayer robPlayer) throws ServerException {
		
		return;
	}

	@Override
	public void finishTurn(FinishTurn finishTurn)
			throws ServerException {
		
		return;
	}

	@Override
	public void buyDevCard(BuyDevCard buyDevCard)
			throws ServerException {
		
		return;
	}

	@Override
	public void yearOfPlenty(YearOfPlenty yearOfPlenty)
			throws ServerException {
		
		return;
	}

	@Override
	public void roadBuilding(RoadBuilding roadBuilding)
			throws ServerException {
		
		return;
	}

	@Override
	public void soldier(Soldier soldier) throws ServerException {
		
		return;
	}

	@Override
	public void monopoly(Monopoly monopoly) throws ServerException {
		
		return;
	}

	@Override
	public void monument(Monument monument) throws ServerException {
		
		return;
	}

	@Override
	public void buildRoad(BuildRoad buildRoad) throws ServerException {
		
		return;
	}

	@Override
	public void buildSettlement(BuildSettlement buildSettlement)
			throws ServerException {
		
		return;
	}

	@Override
	public void buildCity(BuildCity buildCity) throws ServerException {
		
		return;
	}

	@Override
	public void offerTrade(OfferTrade offer) throws ServerException {
		
		return;
	}

	@Override
	public void respondToTrade(AcceptTrade acceptTrade)
			throws ServerException {
		
		return;
	}

	@Override
	public void maritimeTrade(MaritimeTrade maritimeTrade)
			throws ServerException {
		
		return;
	}

	@Override
	public void discardCards(DiscardCards discardCards)
			throws ServerException {
		
		return;
	}
}
