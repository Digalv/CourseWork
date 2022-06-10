package coursework;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    public static void SaveJSON(List<Country> countries) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("./src/database/DataBase.json"), countries);
    }

    public static ArrayList<Country> LoadCountriesJSON() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File("./src/database/DataBase.json"), ArrayList.class);
    }
}
