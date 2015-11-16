package shared.json;

import java.lang.reflect.Type;

import shared.definitions.HexType;
import client.model.Hex;
import client.model.HexLocation;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class HexTypeAdapter implements JsonSerializer<Hex>, JsonDeserializer<Hex> {

	@Override
	public JsonElement serialize(Hex hex, Type type,
			JsonSerializationContext jsc) {
		JsonObject jsonHex = new JsonObject();
		jsonHex.add("location", jsc.serialize(hex.getLocation()));
		if (hex.getType() != null && hex.getType() != HexType.WATER && hex.getType() != HexType.DESERT) {
			jsonHex.add("resource", jsc.serialize(hex.getType()));
			jsonHex.add("number", jsc.serialize(hex.getChitNumber()));
		}
		return jsonHex;
	}

	@Override
	public Hex deserialize(JsonElement element, Type type,
			JsonDeserializationContext jdc) throws JsonParseException {
		JsonObject jsonHex = element.getAsJsonObject();
		Hex hex = new Hex(null);
		hex.setLocation((HexLocation) jdc.deserialize(jsonHex.get("location"), HexLocation.class));
		
		JsonElement resource = jsonHex.get("resource");
		if (resource != null && !(resource instanceof JsonNull) && HexType.fromString(resource.getAsString()) != null) {
			hex.setType(HexType.fromString(resource.getAsString()));
			hex.setChitNumber(jsonHex.get("number").getAsInt());
		}
		else {
			hex.setType(HexType.DESERT);
			hex.setChitNumber(-1);
		}
		return hex;
	}
}
