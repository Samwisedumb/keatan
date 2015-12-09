package client.model;

import shared.definitions.ResourceType;

/**
 * A container to store the number of each resource
 */
public class ResourceList {	
	
	private int brick;
	private int ore;
	private int sheep;
	private int wheat;
	private int wood;
	
	/**
	 * @pre each parameter must be in the range [0,19]
	 * @param brick The number of bricks
	 * @param ore The number of ores
	 * @param sheep The number of sheep
	 * @param wheat The number of wheat
	 * @param wood The number of wood
	 * @post The object's internal values are set to the given params
	 */
	public ResourceList(int brick, int ore, int sheep, int wheat, int wood) {
		this.brick = brick;
		this.ore = ore;
		this.sheep = sheep;
		this.wheat = wheat;
		this.wood = wood;
	}
	
	public int getBrick() {
		return brick;
	}
	public void setBrick(int brick) {
		this.brick = brick;
	}
	public int getOre() {
		return ore;
	}
	public void setOre(int ore) {
		this.ore = ore;
	}
	public int getSheep() {
		return sheep;
	}
	public void setSheep(int sheep) {
		this.sheep = sheep;
	}
	public int getWheat() {
		return wheat;
	}
	public void setWheat(int wheat) {
		this.wheat = wheat;
	}
	public int getWood() {
		return wood;
	}
	public void setWood(int wood) {
		this.wood = wood;
	}
	public int getTotalCards() {
		return wood + wheat + sheep + ore + brick;
	}
	
	public boolean hasResource(ResourceType resource, int amount) {
		switch(resource) {
		case BRICK:
			return brick >= amount;
		case ORE:
			return ore >= amount;
		case SHEEP:
			return sheep >= amount;
		case WHEAT:
			return wheat >= amount;
		case WOOD:
			return wood >= amount;
		default:
			return false;
		}
	}

	public void changeResourceAmount(ResourceType resource, int amount) {
		switch(resource) {
		case BRICK:
			brick = brick + amount;
			break;
		case ORE:
			ore = ore + amount;
			break;
		case SHEEP:
			sheep = sheep + amount;
			break;
		case WHEAT:
			wheat = wheat + amount;
			break;
		case WOOD:
			wood = wood + amount;
			break;
		default:
			break;
		}
	}
	
	/**
	 * See if a player has enough resources to build a road
	 * @return true if the user has enough resources<br>
	 * false if otherwise
	 * @author djoshuac
	 */
	public boolean hasEnoughForRoad() {
		return brick > 0 && wood > 0;
	}

	/**
	 * See if a player has enough resources to build a settlement
	 * @return true if the user has enough resources<br>
	 * false if otherwise
	 * @author djoshuac
	 */
	public boolean hasEnoughForSettlement() {
		return brick > 0 && wood > 0 && wheat > 0 && sheep > 0;
	}

	/**
	 * See if a player has enough resources to build a city
	 * @return true if the user has enough resources<br>
	 * false if otherwise
	 * @author djoshuac
	 */
	public boolean hasEnoughForCity() {
		return wheat > 1 && ore > 3;
	}
	
	/**
	 * See if a player has enough resources to buy a development card
	 * @return true if the user has enough resources<br>
	 * false if otherwise
	 * @author djoshuac
	 */
	public boolean hasEnoughForDevCard() {
		return wheat > 0 && sheep > 0 && ore > 0;
	}

	/**
	 * @return true if there are more than 7 resources in this list<br>
	 * false if otherwise
	 */
	public boolean moreThanSeven() {
		return brick + ore + sheep + wheat + wood > 7;
	}
}
