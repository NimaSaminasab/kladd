package com.example.payex;


import com.sun.tools.javac.Main;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication


public class PayExApplication implements ApplicationContextAware {

    private static ApplicationContext ctx;


    @Autowired
    TvShowController tvShowController;


    public static void main(String[] args) {

        SpringApplication.run(PayExApplication.class, args);
        List<String> TvShowList = readFromFile();

        for (int i = 0; i < TvShowList.size(); i++) {
            readFromApi2(TvShowList.get(i));
        }


    }

    private static List<String> readFromFile() {
        List<String> TvShowList = new ArrayList<>();
        String filePath = "C:/Users/nimas/Desktop/readThis.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                TvShowList.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return TvShowList;
    }


    public static void readFromApi2(String Tvshow) {
        ObjectMapper objectMapper = new ObjectMapper();
        String url2 = "https://api.tvmaze.com/singlesearch/shows?q=" + Tvshow;

        List<Genres> genresList = new ArrayList<>();
        try {
            URL url = new URL(url2);
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
                String nameOfGenre = "";
                String inLowercase = "";


                for (int i = 0; i < jsonArray.size(); i++) {
                    inLowercase = (String) jsonArray.get(i);
                    nameOfGenre = "" + inLowercase.toUpperCase();

                    genresList.add(Genres.valueOf(nameOfGenre));
                }
                for (int i = 0; i < genresList.size(); i++) {
                    System.out.println(genresList.get(i));
                }
                int id = jsonNode.get("id").asInt();
                String name = jsonNode.get("name").asText();

                Double rating = jsonNode.get("rating").get("average").asDouble();
                String summary = jsonNode.get("summary").asText();

                String finalSummary = removeTags(summary);


                TvShow tvShow = new TvShow(id, name, genresList, rating, 60, 54, finalSummary, "Link");
                ctx.getBean(TvShowController.class).createTvShow(tvShow);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Summery from Api contains tags like <p> </p> <b> etc. This method removes them.
    private static String removeTags(String summary) {
        StringBuilder correctedSummary = new StringBuilder();
        for (int i = 0; i < summary.length(); i++) {
            if (summary.charAt(i) == '<') {
                for (int j = i; j < i + 4; j++) {
                    if (j < summary.length()) {
                        if (summary.charAt(j) == '>') {
                            i = j;
                        }
                    }
                }
            } else
                correctedSummary.append(summary.charAt(i));
        }

        return correctedSummary.toString();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        PayExApplication.ctx = applicationContext;

    }
}


