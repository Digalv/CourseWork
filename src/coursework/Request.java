package coursework;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;

/**
 * Класс для обработки запросов с клиента
 */
public class Request {
    public static String start() throws IOException {
        return new BufferedReader(new FileReader(new File("./src/database/DataBase.json"))).readLine();
    }
    public static String add(String countryJSON) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Country country = objectMapper.readValue(countryJSON, Country.class);
        if(Country.correctCountry(country)){
            Main.getCountries().add(country);
            DataBase.SaveJSON(Main.getCountries().getCountriesList());
            return "{\"Correct\": \"1\"}";
        }
        else{
            return "{\"Incorrect\": \"1\"}";
        }
    }
    public static String delete(String ListJSON) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Main.getCountries().setCountriesList(objectMapper.readValue(ListJSON, ArrayList.class));
        DataBase.SaveJSON(Main.getCountries().getCountriesList());
        return "{\"Correct\": \"1\"}";
    }
    public static String edit(String ListJSON) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Main.getCountries().setCountriesList(objectMapper.readValue(ListJSON, ArrayList.class));
        DataBase.SaveJSON(Main.getCountries().getCountriesList());
        return "{\"Correct\": \"1\"}";
    }
}
