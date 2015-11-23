package client.model;

import shared.definitions.CatanColor;
import shared.definitions.ResourceType;

/**
 * Contains data about a given player
 */
public class Player {	
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
		roads = 15;
		settlements = 5;
		cities = 4;
		soldiers = 0;
		monuments = 0;
		victoryPoints = 0;
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
	
	public void useRoadBuildingCard() {
		oldDevCards.setRoadBuilding(oldDevCards.getRoadBuilding() - 1);
		playedDevCard = true;
	}
}
