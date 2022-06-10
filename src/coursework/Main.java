package coursework;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class Main {

    private static Countries countries = new Countries();

    public static Countries getCountries() {
        return countries;
    }

    public static void setCountries(Countries countries) {
        Main.countries = countries;
    }

    public static void main(String[] args) throws IOException {
        try {
            countries.setCountriesList(DataBase.LoadCountriesJSON());
        }
        catch (Exception e){
            countries = new Countries();
        }
        simplestServerExample();
    }



    public static void simplestServerExample() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
        server.createContext("/back", new SimplestServerHttpHandler());
        server.start();
        System.out.println(System.lineSeparator() + "\033[1;32mServer started at:\tlocalhost:8001\u001B[0m");

    }
}
