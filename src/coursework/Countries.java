package coursework;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Countries implements Serializable {
    private ArrayList<Country> countriesList;

    public ArrayList<Country> getCountriesList() {
        return countriesList;
    }
    public void setCountriesList(ArrayList<Country> countriesList){
        this.countriesList = countriesList;
    }
    public Countries() {
        countriesList = new ArrayList<>();
    }

    /**
     * Добавить страну в список
     * @param country объект страны
     */
    public void add(Country country){
        countriesList.add(country);
    }

    /**
     * Удалить страну из списка
     * @param country объект страны
     */
    public void remove(Country country){
        countriesList.remove(country);
    }

    /**
     * Удаление страны из списка по номеру
     * @param number порядковый номер страны
     */
    public void remove(int number) throws IllegalArgumentException {
        if (number < countriesList.size() && number >= 0){
            countriesList.remove(number);
        } else {
            throw new IllegalArgumentException("Entered incorrect index of country to remove");
        }
    }

    @Override
    public String toString() {
        return "Countries: " + countriesList;
    }
}
