package shared.transferClasses;

public class AddAIRequest {
	
	private String AIType;
	
	public AddAIRequest(String type){
		AIType = type;
	}

	public String getAIType() {
		return AIType;
	}

	public void setAIType(String aIType) {
		AIType = aIType;
	}

}
