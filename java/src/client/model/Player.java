package client.model;

import client.data.PlayerInfo;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;

/**
 * Contains data about a given player
 */
public class Player {
	public static final int TOTAL_ROADS = 15;
	public static final int TOTAL_SETTLEMENTS = 5;
	public static final int TOTAL_CITIES = 4;
	
	private String name;
	private int playerID;
	private CatanColor color; // We can't avoid being primitively obsessed while satisfying the TA's JSON objects. #swagger
	private int playerIndex;

	private boolean discarded; // if the player has discarded resources during the discard state
	private ResourceList resources;

	private boolean playedDevCard; // if the player has already played a development card on their turn
	private DevCardList newDevCards = null;
	private DevCardList oldDevCards = null;
	
	private int roads; //unplaced
	private int settlements; //unplaced
	private int cities; //unplaced
	
	private int soldiers;
	private int monuments;
	private int victoryPoints;
	
	private boolean hasLargestArmy;
	private boolean hasLongestRoad;
	
	/**
	 * @pre newPlayerIndex must be in the range [0,3], and newName must not be in use
	 * @param newName The player's name
	 * @param newPlayerIndex The player's playerIndex
	 * @param newPlayerID The player's ID
	 * @post The object's internal values are initialized
	 */
	public Player(String newName, int newPlayerIndex, CatanColor newColor, int newPlayerID) {
		name = newName;
		playerID = newPlayerID;
		color = newColor;
		playerIndex = newPlayerIndex;
		newDevCards = new DevCardList(0, 0, 0, 0, 0);
		oldDevCards = new DevCardList(0, 0, 0, 0, 0);
		playedDevCard = false;
		discarded = false;
		resources = new ResourceList(0,0,0,0,0);
		roads = TOTAL_ROADS;
		settlements = TOTAL_SETTLEMENTS;
		cities = TOTAL_CITIES;
		soldiers = 0;
		monuments = 0;
		victoryPoints = 0;
		
		hasLargestArmy = false;
		hasLongestRoad = false;
	}
	
	public int getUnplacedCities() {
		return cities;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	
	public void setUnplacedCities(int unplacedCities) {
		this.cities = unplacedCities;
	}

	public int getUnplacedRoads() {
		return roads;
	}

	public void setUnplacedRoads(int unplacedRoads) {
		this.roads = unplacedRoads;
	}

	public int getPlacedRoads() {
		return 15 - roads;
	}
	
	public int getUnplacedSettlements() {
		return settlements;
	}

	public void setUnplacedSettlements(int unplacedSettlements) {
		this.settlements = unplacedSettlements;
	}

	public CatanColor getColor() {
		return color;
	}

	public void setColor(CatanColor color) {
		this.color = color;
	}

	public boolean hasDiscarded() {
		return discarded;
	}

	public void setDiscarded(boolean discarded) {
		this.discarded = discarded;
	}

	public int getNumMonuments() {
		return monuments;
	}

	public void setNumMonuments(int monuments) {
		this.monuments = monuments;
	}

	public DevCardList getNewDevCards() {
		return newDevCards;
	}

	public void setNewDevCards(DevCardList newDevCards) {
		this.newDevCards = newDevCards;
	}

	public DevCardList getOldDevCards() {
		return oldDevCards;
	}

	public void setOldDevCards(DevCardList oldDevCards) {
		this.oldDevCards = oldDevCards;
	}

	public boolean hasPlayedDevCard() {
		return playedDevCard;
	}

	public void setPlayedDevCard(boolean playedDevCard) {
		this.playedDevCard = playedDevCard;
	}

	public ResourceList getResources() {
		return resources;
	}

	public void setResources(ResourceList resources) {
		this.resources = resources;
	}

	public int getNumSoldiers() {
		return soldiers;
	}

	public void setNumSoldiers(int soldiers) {
		this.soldiers = soldiers;
	}

	public int getVictoryPoints() {
		return victoryPoints;
	}

	public void setVictoryPoints(int victoryPoints) {
		this.victoryPoints = victoryPoints;
	}

	public String getName() {
		return name;
	}

	public int getIndex() {
		return playerIndex;
	}

	public int getID() {
		return playerID;
	}
	
	public void addResource(ResourceType resource, int amount) {
		switch(resource) {
		case BRICK:
			resources.setBrick(resources.getBrick()+amount);
			break;
		case ORE:
			resources.setOre(resources.getOre()+amount);
			break;
		case SHEEP:
			resources.setSheep(resources.getSheep()+amount);
			break;
		case WHEAT:
			resources.setWheat(resources.getWheat()+amount);
			break;
		case WOOD:
			resources.setWood(resources.getWood()+amount);
			break;
		default:
			break;
		}
	}
	public void removeResource(ResourceType resource, int amount) {
		switch(resource) {
		case BRICK:
			resources.setBrick(resources.getBrick()-amount);
			break;
		case ORE:
			resources.setOre(resources.getOre()-amount);
			break;
		case SHEEP:
			resources.setSheep(resources.getSheep()-amount);
			break;
		case WHEAT:
			resources.setWheat(resources.getWheat()-amount);
			break;
		case WOOD:
			resources.setWood(resources.getWood()-amount);
			break;
		default:
			break;
		}
	}
	
	public int getResourceAmount(ResourceType resource) {
		switch(resource) {
		case BRICK:
			return resources.getBrick();
		case ORE:
			return resources.getOre();
		case SHEEP:
			return resources.getSheep();
		case WHEAT:
			return resources.getWheat();
		case WOOD:
			return resources.getWood();
		default:
			return 0;
		}
	}
	
	/**
	 * @post returns the player info for this player
	 * @pre player must be valid
	 */
	public PlayerInfo getPlayerInfo() {
		return new PlayerInfo(playerID, playerIndex, name, color);
	}
	
	public void playRoad() {
		this.roads--;
	}
	
	public void playSettlement() {
		this.settlements--;
		this.victoryPoints++;
	}
	
	public void playCity() {
		this.cities--;
		this.settlements++;
		this.victoryPoints++;
	}
	
	public void useRoadBuildingCard() {
		oldDevCards.setRoadBuilding(oldDevCards.getRoadBuilding() - 1);
		playedDevCard = true;
	}
	
	public void useSoldierCard() {
		oldDevCards.setSoldier(oldDevCards.getSoldier() - 1);
		playedDevCard = true;
		this.soldiers++;
	}
	
	public void useMonumentCards() {
		int cardsToPlay = oldDevCards.getMonument();

		oldDevCards.setMonument(0);
		
		this.victoryPoints += cardsToPlay;
	}
	
	public void useYearOfPlentyCard() {
		oldDevCards.setYearOfPlenty(oldDevCards.getYearOfPlenty() - 1);
		playedDevCard = true;
	}
	
	public void useMonopolyCard() {
		oldDevCards.setMonopoly(oldDevCards.getMonopoly() - 1);
		playedDevCard = true;
	}
	
	public void endTurn() {
		playedDevCard = false;
		discarded = false;
		
		oldDevCards.setRoadBuilding(oldDevCards.getRoadBuilding() + newDevCards.getRoadBuilding());
		oldDevCards.setSoldier(oldDevCards.getSoldier() + newDevCards.getSoldier());
		oldDevCards.setMonument(oldDevCards.getMonument() + newDevCards.getMonument());
		oldDevCards.setYearOfPlenty(oldDevCards.getYearOfPlenty() + newDevCards.getYearOfPlenty());
		oldDevCards.setMonopoly(oldDevCards.getMonopoly() + newDevCards.getMonopoly());
		
		newDevCards.setMonopoly(0);
		newDevCards.setMonument(0);
		newDevCards.setRoadBuilding(0);
		newDevCards.setSoldier(0);
		newDevCards.setYearOfPlenty(0);
	}
	
	public void getLargestArmy() {
		if(hasLargestArmy == false) {
			this.victoryPoints += 2;
			hasLargestArmy = true;
		}
	}
	
	public void getLongestRoad() {
		if(hasLongestRoad == false) {
			this.victoryPoints += 2;
			hasLongestRoad = true;
		}
	}
	
	public void loseLargestArmy() {
		if(hasLargestArmy == true) {
			this.victoryPoints -= 2;
			hasLargestArmy = false;
		}
	}
	
	public void loseLongestRoad() {
		if(hasLongestRoad == true) {
			this.victoryPoints -= 2;
			hasLongestRoad = false;
		}
	}
	
	/**
	 * @return the number of roads the user has placed
	 * @author djoshuac
	 */
	public int getNumPlacedRoads() {
		return TOTAL_ROADS - getUnplacedRoads();
	}

	/**
	 * @return the number of settlements the user has placed
	 * @author djoshuac
	 */
	public int getNumPlacedSettlements() {
		return TOTAL_SETTLEMENTS - getUnplacedSettlements();
	}
	
	/**
	 * @return the number of cities the user has placed
	 * @author djoshuac
	 */
	public int getNumbPlacedCities() {
		return TOTAL_CITIES - getUnplacedCities();
	}
}
