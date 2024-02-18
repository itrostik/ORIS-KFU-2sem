package ru.kpfu.itis.sergeev.controller;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;


@RestController
@RequestMapping("/exchange")
public class ExchangeRateController {
    @GetMapping
    public String exchange() {
        String usd;
        String eur;
        try (InputStream stream = new URI("http://api.currencylayer.com/live?access_key=19ee0ebf14893805de96f9c87f2a3048&source=EUR&currencies=RUB").toURL().openStream()) {
            BufferedReader bfr = new BufferedReader(new InputStreamReader(stream));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bfr.readLine()) != null) {
                content.append(line);
            }
            JSONObject json = new JSONObject(content.toString());
            JSONObject quotes = json.getJSONObject("quotes");
            StringBuilder result = new StringBuilder();
            for (String key : quotes.keySet()) {
                double value = quotes.getDouble(key);
                result.append("EUR").append(": ").append(value).append(" RUB");
            }
            eur = result.toString();
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        try (InputStream stream = new URI("http://api.currencylayer.com/live?access_key=19ee0ebf14893805de96f9c87f2a3048&source=USD&currencies=RUB").toURL().openStream()) {
            BufferedReader bfr = new BufferedReader(new InputStreamReader(stream));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bfr.readLine()) != null) {
                content.append(line);
            }
            JSONObject json = new JSONObject(content.toString());
            JSONObject quotes = json.getJSONObject("quotes");
            StringBuilder result = new StringBuilder();
            for (String key : quotes.keySet()) {
                double value = quotes.getDouble(key);
                result.append("USD").append(": ").append(value).append(" RUB");
            }
            usd = result.toString();
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return usd + "<br>" + eur;
    }
}
