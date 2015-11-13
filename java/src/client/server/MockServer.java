package client.server;

import java.io.FileNotFoundException;
import java.io.FileReader;

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
import shared.transferClasses.YearOfPlenty;
import client.exceptions.ServerException;
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
	public void login(UserCredentials userCredentials) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void register(UserCredentials userCredentials)
			throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Game[] getGamesList() throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CreateGameResponse createGame(CreateGameRequest createGameRequest)
			throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void joinGame(JoinGameRequest joinGameRequest)
			throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addAI(AddAIRequest addAIRequest) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] listAITypes() throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransferModel sendChat(SendChat sendChat) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransferModel rollDice(RollNumber rollNumber) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransferModel robPlayer(RobPlayer robPlayer) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransferModel finishTurn(FinishTurn finishTurn)
			throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransferModel buyDevCard(BuyDevCard buyDevCard)
			throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransferModel yearOfPlenty(YearOfPlenty yearOfPlenty)
			throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransferModel roadBuilding(RoadBuilding roadBuilding)
			throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransferModel soldier(Soldier soldier) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransferModel monopoly(Monopoly monopoly) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransferModel monument(Monument monument) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransferModel buildRoad(BuildRoad buildRoad) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransferModel buildSettlement(BuildSettlement buildSettlement)
			throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransferModel buildCity(BuildCity buildCity) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransferModel offerTrade(OfferTrade offer) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransferModel respondToTrade(AcceptTrade acceptTrade)
			throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransferModel maritimeTrade(MaritimeTrade maritimeTrade)
			throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransferModel discardCards(DiscardCards discardCards)
			throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}
}
