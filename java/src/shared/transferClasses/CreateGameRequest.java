package shared.transferClasses;

/**
 * This class is used to fulfill a create game request to the server
 * <li>boolean randomTiles</li>
 * <li>boolean randomNumbers</li>
 * <li>boolean randomPorts</li>
 * <li>String gameName</li>
 */
public class CreateGameRequest {
	private boolean randomTiles;
	private boolean randomNumbers;
	private boolean randomPorts;
	private String gameName;
	
	public CreateGameRequest(boolean randomTiles, boolean randomNumbers,
			boolean randomPorts, String name) {
		this.randomTiles = randomTiles;
		this.randomNumbers = randomNumbers;
		this.randomPorts = randomPorts;
		this.gameName = name;
	}

	public boolean isRandomTiles() {
		return randomTiles;
	}

	public void setRandomTiles(boolean randomTiles) {
		this.randomTiles = randomTiles;
	}

	public boolean isRandomNumbers() {
		return randomNumbers;
	}

	public void setRandomNumbers(boolean randomNumbers) {
		this.randomNumbers = randomNumbers;
	}

	public boolean isRandomPorts() {
		return randomPorts;
	}

	public void setRandomPorts(boolean randomPorts) {
		this.randomPorts = randomPorts;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String name) {
		this.gameName = name;
	}	
}