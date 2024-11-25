package helpers.Transformer;
import Config.Config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonHelper {

    public static String[] parseJSONLoginPage(String filePath, int index) throws FileNotFoundException {
        JsonArray Data =  JsonParser.parseReader(new FileReader(filePath)).getAsJsonArray();
        JsonObject firstObject = Data.get(index).getAsJsonObject();
        return new String[] {firstObject.get("username").getAsString(),firstObject.get("password").getAsString(),firstObject.get("expectedResult").getAsString()};

    }

    public static String[] parseJSONCheckoutPage(String filePath, int index) throws FileNotFoundException {
        JsonArray Data =  JsonParser.parseReader(new FileReader(filePath)).getAsJsonArray();
        JsonObject requiredObject = Data.get(index).getAsJsonObject();
        return new String[] {requiredObject.get("firstname").getAsString(),requiredObject.get("lastname").getAsString(),requiredObject.get("postalCode").getAsString(),requiredObject.get("expectedResult").getAsString()};

    }

    public static String[] parseJSONProductPage(String filePath, int index) throws FileNotFoundException {
        JsonArray Data =  JsonParser.parseReader(new FileReader(filePath)).getAsJsonArray();
        JsonObject requiredObject = Data.get(index).getAsJsonObject();
        return new String[] {requiredObject.get("productName").getAsString(),requiredObject.get("productDescription").getAsString(),requiredObject.get("productImage").getAsString(),requiredObject.get("productPrice").getAsString()};

    }

    public static void writeProductNamesToJSON(List<String> productNames){
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(Config.testDataPath+Config.FileNames.PRODUCT_NAMES_STORED.getFileName());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            objectMapper.writeValue(writer, productNames);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static List<String> readProductNamesFromJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> productNames = new ArrayList<>();

        String filePath = Config.testDataPath + Config.FileNames.PRODUCT_NAMES_STORED.getFileName();
        File file = new File(filePath);

        if(!file.exists()){
            System.err.println("File not found: "+filePath);
            return productNames;
        }
        try {
            productNames = objectMapper.readValue(file,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
        } catch (JsonParseException e){
            System.err.println("Error parsing JSON: "+e.getMessage());
        }catch(IOException e){
            System.err.println("IO Error: "+e.getMessage());
        }catch(Exception e){
            System.err.println("unexpected error: "+e.getMessage());
        }
        return productNames;
    }


    public static String[] parseMockData(String filePath, int index) throws FileNotFoundException {
        JsonArray Data =  JsonParser.parseReader(new FileReader(filePath)).getAsJsonArray();
        JsonObject requiredObject = Data.get(index).getAsJsonObject();
        return new String[] {requiredObject.get("sessionName").getAsString(),requiredObject.get("SessionValue").getAsString()};

    }






}
