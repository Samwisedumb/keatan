package shared.json;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;

import client.model.Hex;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * The Converter is a library class that provides methods to serialize and deserialize
 * an instance of a class to and from a JSON String.
 * @author djoshuac
 */
public final class Converter {
	private static final Gson gson = new GsonBuilder().disableHtmlEscaping()
			.enableComplexMapKeySerialization()
			.registerTypeHierarchyAdapter(Hex.class, new HexTypeAdapter())
			.create();
	
	/**
	 * Deserializes JSON into an instance of a class of the given type.
	 * @pre json must be a valid JSON string for an object of Type type
	 * @param json - a JSON String for an object
	 * @param type - a java.lang.reflect.Type for a specific class. Usage example: "ArrayList.class"
	 * @return an instance of the given class type specified by the JSON String
	 * @post An instance of the class type specified by a JSON String is created
	 */
	public static <T> T fromJson(String json, Type type) {
		return gson.fromJson(json, type);
	}
	
	/**
	 * Deserializes JSON into an instance of a class of the given type.
	 * @pre reader must be a valid JSON string for an object of Type type
	 * @param reader - a Reader reading a valid JSON string
	 * @param type - a java.lang.reflect.Type for a specific class. Usage example: "ArrayList.class"
	 * @return an instance of the given class type specified by the JSON String
	 * @post An instance of the class type specified by a JSON String is created
	 */
	public static <T> T fromJson(Reader reader, Type type) {
		return gson.fromJson(reader, type);
	}
	
	/**
	 * Deserializes JSON into an instance of a class of the given type.
	 * @pre reader must be a valid JSON string for an object of Type type
	 * @param inputStream - an inputStream with a valid JSON string
	 * @param type - a java.lang.reflect.Type for a specific class. Usage example: "ArrayList.class"
	 * @return an instance of the given class type specified by the JSON String
	 * @post An instance of the class type specified by a JSON String is created
	 */
	public static <T> T fromJson(InputStream inputStream, Type type) {
		return gson.fromJson(new InputStreamReader(inputStream), type);
	}
	
	/**
	 * Serializes an Object to JSON.
	 * @pre none
	 * @param model - the model of a Settlers of Catan game
	 * @return a JSON String of the Model
	 * @post A JSON String of model is created
	 */
	public static String toJson(Object object) {
		return gson.toJson(object);
	}
}
