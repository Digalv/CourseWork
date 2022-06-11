package coursework;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

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
    public static String delete(String listJSON) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Main.getCountries().setCountriesList(objectMapper.readValue(listJSON, ArrayList.class));
        DataBase.SaveJSON(Main.getCountries().getCountriesList());
        return "{\"Correct\": \"1\"}";
    }

    public static String edit(String string) throws IOException {
        String[] response = string.split(",\\{");
        int id = Integer.parseInt(response[0]);
        ObjectMapper objectMapper = new ObjectMapper();
        Country country = objectMapper.readValue("{" + response[1], Country.class);
        if(Country.correctCountry(country)){
            System.out.println("correct");
            Main.getCountries().getCountriesList().set(id, country);

            DataBase.SaveJSON(Main.getCountries().getCountriesList());
            return "{\"Correct\": \"1\"}";
        }
        else{
            return "{\"Incorrect\": \"1\"}";
        }
    }
}
