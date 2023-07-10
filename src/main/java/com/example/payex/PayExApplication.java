package com.example.payex;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sun.tools.javac.Main;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
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
            readTvShowFromApi(TvShowList.get(i));
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

    public static void readEpisodeDetailFromApi(String tvShowName) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JSONParser jsonParser = new JSONParser();

            URL url = new URL(tvShowName);

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Http Response" + responseCode);
            } else {
                StringBuilder infoString2 = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    infoString2.append(scanner.nextLine());
                }
                scanner.close();


                JsonNode jsonNode1 = objectMapper.readTree(String.valueOf(infoString2));
                JSONArray jsonArray1 = (JSONArray) jsonParser.parse(String.valueOf(infoString2));

                JSONObject jsonObject1 = (JSONObject) jsonArray1.get(0);
                String summary2 = (String) jsonObject1.get("summary");

                System.out.println(summary2);


            }
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readTvShowFromApi(String tvShowName) {
        try {
            String urlTvShow = "https://api.tvmaze.com/singlesearch/shows?q=" + tvShowName;

            List<Genres> genresList = new ArrayList<>();
            List<Showday> showdayList = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            JSONParser jsonParser = new JSONParser();

            URL url = new URL(urlTvShow);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Http Response" + responseCode);
            } else {
                StringBuilder infoString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    infoString.append(scanner.nextLine());
                }

                scanner.close();

                JsonNode jsonNode = objectMapper.readTree(String.valueOf(infoString));
                JSONObject jsonObject = (JSONObject) jsonParser.parse(String.valueOf(infoString));


                int id = jsonNode.get("id").asInt();

                String seasonDetails = "https://api.tvmaze.com/shows/" + id + "/episodes";
                System.out.println(seasonDetails);
                readEpisodeDetailFromApi(seasonDetails);


                JSONArray jsonArray = (JSONArray) jsonObject.get("genres");
                JSONObject scheduleObject = (JSONObject) jsonObject.get("schedule");
                JSONArray daysArray = (JSONArray) scheduleObject.get("days");


                String nameOfGenre = "";
                String inLowercase = "";


                for (int i = 0; i < jsonArray.size(); i++) {
                    inLowercase = (String) jsonArray.get(i);
                    nameOfGenre = "" + inLowercase.toUpperCase();
                    if (nameOfGenre.equals("SCIENCE-FICTION"))
                        nameOfGenre = "SCIENCE_FICTION";

                    genresList.add(Genres.valueOf(nameOfGenre));
                }

                String name = jsonNode.get("name").asText();
                String imdb = jsonNode.get("externals").get("imdb").asText();
                Double rating = jsonNode.get("rating").get("average").asDouble();
                String summary = jsonNode.get("summary").asText();
                String finalSummary = removeTags(summary);

                showdayList = days(daysArray);

                List<Season> seasonList = null;
                TvShow tvShow = new TvShow(id, name, seasonList, showdayList, genresList, rating, 60, 54, finalSummary, imdb);

                Season newSeason = new Season(1);
                newSeason.addEpisode(new Episode(1, newSeason, "First episode"));

                tvShow.addSeason(newSeason);

                ctx.getBean(TvShowController.class).createTvShow(tvShow);
            }


        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    public static List<Showday> days(JSONArray daysArray) {
        String day;
        List<Showday> showdays = new ArrayList<>();

        for (int i = 0; i < daysArray.size(); i++) {
            day = (String) daysArray.get(i);
            showdays.add(Showday.valueOf(day.toUpperCase()));
        }
        return showdays;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        PayExApplication.ctx = applicationContext;

    }
}


