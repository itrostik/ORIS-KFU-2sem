package ru.kpfu.itis.sergeev.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;


@RestController
public class WeatherController {
    @GetMapping("/weather")
    public String weather() {
        try (InputStream stream = new URI("https://api.openweathermap.org/data/2.5/weather?q=Kazan&appid=0c9d371ebde9e8af7cf225e608128f8d").toURL().openStream()) {
            BufferedReader bfr = new BufferedReader(new InputStreamReader(stream));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bfr.readLine()) != null) {
                content.append(line);
            }
            JSONObject jsonObject = new JSONObject(content.toString());
            double temp = jsonObject.getJSONObject("main").getDouble("temp");
            double humidity = jsonObject.getJSONObject("main").getDouble("humidity");
            String description = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
            return "Temperature: " + Math.round(temp - 273) + "°C<br>" + "Humidity: " + humidity + "%<br> " + "Description: " + description;
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
