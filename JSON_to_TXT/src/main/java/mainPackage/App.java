package mainPackage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;

public class App {
	static JSONParser parser = new JSONParser();

	//Komponenten für die Transformation
	static List<Object> specs;
	static Chainr chainr;
	static Object inputJSON;
	static Object transformedOutput;

	static final String CHAINR_LOCATION="/Users/Arseniy/Desktop/JSON_to_TXT/src/main/java/Resources/input.json";
	static final String INPUT_LOCATION="/Users/Arseniy/Desktop/JSON_to_TXT/src/main/java/Resources/input.json";
	static final String OUTPUT_LOCATION="/Users/Arseniy/Desktop/JSON_to_TXT/src/main/java/Resources/output";

	//Komponenten für Enum Output

	static JSONObject finalOutputObject;
	static Object inputJsonFile;
	static JSONArray inputJsonArray;
	static JSONObject currentObject;
	static String currentValueName;
	static JSONArray currentSelectedValue;

	//Komponenten für StructreType und RelationsshipType
	static JSONArray attributeRelation;
	static JSONArray inputFile;
	static JSONArray output;
	static JSONArray groupDefaultOutput;
	static JSONObject groupDefault;

	static JSONObject nonEnumObject;
	static JSONArray featuresList;
	static JSONObject primitiveDataType;
	static JSONObject Iterator;
	static String iteratorType;
	static String key;

	static JSONObject complexArray;
	static JSONObject finalOutput;
	static JSONObject complexArrayWrapper;
	
	public static ArrayList<EnumObject> enumList = new ArrayList<EnumObject>();
	public static ArrayList<StructuredTypeObject> structuredTypes = new ArrayList<StructuredTypeObject>();
	public static ArrayList<Reference> referenceList = new ArrayList<Reference>();

	//Finaler JSON String output
	static FileWriter file;
	
	//Placeholder
	static ArrayList<String> enums = new ArrayList<String>();
	static ArrayList<String> structs = new ArrayList<String>();
	static ArrayList<String> references = new ArrayList<String>();
	
	public static Reference getIncompleteReference(String type){
		for(int i = 0;i<referenceList.size();i++){
			Reference ref = referenceList.get(i);
			if(ref.getE1().getType().equals(type) && ref.getE2() == null){
				return ref;
			}
		}
		return null;
	}
	
	public static Reference getIncompleteReference(String type, String name){
		for(int i = 0;i<referenceList.size();i++){
			Reference ref = referenceList.get(i);
			if(ref.getE1().getType().equals(type) && ref.getE1().getName().equals(name) && ref.getE2() == null){
				return ref;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {

	    
		//input einlesen
		inputJSON = JsonUtils.classpathToObject("/Resources/input.json");

		//ergebnis der transformation
		inputJsonFile = parser.parse(JsonUtils.toJsonString(transformedOutput));

		//input array
		inputJsonArray = (JSONArray) inputJsonFile;

		//getArray für input
		inputFile = (JSONArray) parser.parse(new FileReader(INPUT_LOCATION));
		
		ArrayList<EnumObject> enumList = new ArrayList<EnumObject>();
		
		for (int i = 0; i < inputFile.size(); i++) {
			JSONObject jsonObject = (JSONObject) inputFile.get(i);
			//IF ENUM 
			if(jsonObject.get("type").equals("EnumerationExpression")){
				String name = (String) jsonObject.get("name");
				JSONArray literals = (JSONArray) jsonObject.get("literals");
				enumList.add(new EnumObject(name, literals));
			}else{
				//es ist kein Enum
				structuredTypes.add(new StructuredTypeObject(jsonObject));
				
			}
		}
	
	
		String result = "";
		
		for(int i = 0;i<enumList.size();i++){
			result += enumList.get(i).toString();
		}
		
		for(int i = 0; i<structuredTypes.size();i++){
			result += structuredTypes.get(i).toString();
		}
		
		for(int i = 0; i<references.size();i++){
			result += references.get(i).toString();
		}

		//System.out.println(result);
	}
}
