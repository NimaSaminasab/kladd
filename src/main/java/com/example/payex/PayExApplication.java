package com.example.payex;


import com.sun.tools.javac.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.example.payex.Genres;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication


public class PayExApplication {


    public static void main(String[] args) {

        SpringApplication.run(PayExApplication.class, args);
        TvShowController tvShowController = new TvShowController();
        readFromApi2(tvShowController);

    }


    public static void readFromApi2(TvShowController tvShowController) {
        ObjectMapper objectMapper = new ObjectMapper();

        List<Genres> genresList = new ArrayList<>();
        try {
            URL url = new URL("https://api.tvmaze.com/singlesearch/shows?q=breaking%20bad");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("Http Response" + responseCode);
            } else {
                JSONParser jsonParser = new JSONParser();
                StringBuilder infoString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    infoString.append(scanner.nextLine());
                }
                scanner.close();
                JsonNode jsonNode = objectMapper.readTree(String.valueOf(infoString));
                JSONObject jsonObject = (JSONObject) jsonParser.parse(String.valueOf(infoString));
                JSONArray jsonArray = (JSONArray) jsonObject.get("genres");


                int id = jsonNode.get("id").asInt();
                String name = jsonNode.get("name").asText();

                Double rating = jsonNode.get("rating").get("average").asDouble();
                String summary = jsonNode.get("summary").asText();

                genresList.add(Genres.ACTION);
                genresList.add(Genres.ADVENTURE);
                genresList.add(Genres.ESPOINAGE);

                TvShow tvShow = new TvShow(id, name, genresList, rating, 60, 54, summary, "Link");

                tvShowController.createTvShow(tvShow);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


