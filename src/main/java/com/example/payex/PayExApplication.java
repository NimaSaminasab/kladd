package com.example.payex;

import org.apache.tomcat.util.json.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.util.Scanner;

@SpringBootApplication
public class PayExApplication {

    public static void main(String[] args) {

        SpringApplication.run(PayExApplication.class, args);
        readFromApi();

    }

    public static void readFromApi(){
        try {
            URL url = new URL("https://api.tvmaze.com/episodes/1");
            HttpsURLConnection connection = (HttpsURLConnection ) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();

            if(responseCode !=200)
                throw new RuntimeException("HttpResponse "+ responseCode) ;
            else{
                StringBuilder informationString = new StringBuilder() ;
                Scanner scanner = new Scanner(url.openStream() );

                while (scanner.hasNext()){
                    informationString.append(scanner.nextLine() );
                }
                scanner.close();
                String jsonString = informationString + "" ;
                //System.out.println(informationString);

                JSONObject obj = new JSONObject();


                JSONParser parser = new JSONParser(String.valueOf(informationString)) ;
                JSONArray dataObject = (JSONArray) parser.parse();

                System.out.println(dataObject.get(0));
                //JsonNode node = Json.parse(informationString);
            }



        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
