package client.model;

import shared.definitions.DevCardType;

/**
 * Contains a list of development cards
 */
public class DevCardList {
	private int monopoly;
	private int monument;
	private int roadBuilding;
	private int soldier;
	private int yearOfPlenty;

	/**
	 * @pre 
	 * @param monopoly The number of monopoly cards
	 * @param monument The number of monument cards
	 * @param roadBuilding The number of road building cards
	 * @param soldier The number of soldier cards
	 * @param yearOfPlenty The number of year of plenty cards
	 * @post The object's values are set to the given params
	 */
	public DevCardList(int monopoly, int monument, int roadBuilding, int soldier, int yearOfPlenty) {
		this.monopoly = monopoly;
		this.monument = monument;
		this.roadBuilding = roadBuilding;
		this.soldier = soldier;
		this.yearOfPlenty = yearOfPlenty;
	}
	
	public int getMonopoly() {
		return monopoly;
	}
	public void setMonopoly(int monopoly) {
		this.monopoly = monopoly;
	}
	public int getMonument() {
		return monument;
	}
	public void setMonument(int monument) {
		this.monument = monument;
	}
	public int getRoadBuilding() {
		return roadBuilding;
	}
	public void setRoadBuilding(int roadBuilding) {
		this.roadBuilding = roadBuilding;
	}
	public int getSoldier() {
		return soldier;
	}
	public void setSoldier(int soldier) {
		this.soldier = soldier;
	}
	public int getYearOfPlenty() {
		return yearOfPlenty;
	}
	public void setYearOfPlenty(int yearOfPlenty) {
		this.yearOfPlenty = yearOfPlenty;
	}
	
	public int getTotalCards() {
		return monopoly + monument + roadBuilding + soldier + yearOfPlenty;
	}

	public boolean validate() {
		if (monopoly < 0 || monopoly > 2) return false;
		if (monument < 0 || monument > 5) return false;
		if (roadBuilding < 0 || roadBuilding > 2) return false;
		if (soldier < 0 || soldier > 14) return false;
		if (yearOfPlenty < 0 || yearOfPlenty > 2) return false;
		return true;
	}

	/**
	 * @param type - the type to get the number of dev cards in this list
	 * @return the number of dev cards of the given type
	 */
	public int getAmount(DevCardType type) {
		if (type == null) {
			return 0;
		}
		switch (type) {
		case MONOPOLY:
			return getMonopoly();
		case MONUMENT:
			return getMonument();
		case ROAD_BUILD:
			return getRoadBuilding();
		case SOLDIER:
			return getSoldier();
		case YEAR_OF_PLENTY:
			return getYearOfPlenty();
		default:
				return 0;
		}
	}
}
