package coursework;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Country implements Serializable {
    private String title;
    private String capital;
    private int population;
    private double square;
    private String continent;
    private String language;

    public Country(String title, String capital, int population, double square, String continent, String language) {
        this.setTitle(title);
        this.setCapital(capital);
        this.setPopulation(population);
        this.setSquare(square);
        this.continent = continent;
        this.language = language;
    }

    public Country(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(Utilities.onlyLetterInString(title) && Utilities.correctWordCase(title)){
            this.title = Utilities.removeWhitespace(title);
        }
        else{
            this.title = "";
        }
    }


    public String getCapital() {

        return capital;
    }

    public void setCapital(String capital) {

        if(Utilities.onlyLetterInString(capital) && Utilities.correctWordCase(capital)){
            this.capital = Utilities.removeWhitespace(capital);
        }
        else{
            this.capital = "";
        }
    }

    public int getPopulation() {

        return population;
    }

    public void setPopulation(int population) {

        this.population = Utilities.positiveNumber(population);
    }

    public double getSquare() {

        return square;
    }

    public void setSquare(double square) {

        this.square = Utilities.positiveNumber(square);
    }

    public String getContinent(){
        return this.continent;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        return "Country{" +
                "title='" + this.getTitle() + '\'' +
                ", capital='" + this.getCapital() + '\'' +
                ", population=" + this.getPopulation() + '\''+
                ", square=" + this.getSquare() + '\'' +
                ", continent=" + this.getContinent() +
                '}';
    }

    public static boolean correctCountry(Country country) throws IOException {
        Country result = new Country(country.title, country.capital, country.population, country.square, country.continent, country.language);
        return (!result.getTitle().equals("")) && (!result.getCapital().equals("")) && (result.getPopulation() != -1) && (result.getSquare() != -1);
    }
}
