package com.kisssusha.ksplugin;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class WeatherApi {

    public WeatherReport weatherReport;
    public String location = "Saint Petersburg";

    public WeatherApi() {
    }

    public WeatherReport timelineRequestHttpClient() throws Exception {
        //set up the end point
        weatherReport = new WeatherReport();
        String apiEndPoint = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

        String unitGroup = "metric";
        String apiKey = "PGZZKBQH6G3EUWJXBLGYKDNZC";

        URIBuilder builder = new URIBuilder(apiEndPoint + URLEncoder.encode(location, StandardCharsets.UTF_8));

        builder.setParameter("unitGroup", unitGroup)
                .setParameter("key", apiKey);


        HttpGet get = new HttpGet(builder.build());

        CloseableHttpClient httpclient = HttpClients.createDefault();

        CloseableHttpResponse response = httpclient.execute(get);

        String rawResult = null;

        try {
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                return null;
            }

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                rawResult = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            }


        } finally {
            response.close();
        }

        return parseTimelineJson(rawResult);
    }

    private WeatherReport parseTimelineJson(String rawResult) {

        JSONObject timelineResponse = new JSONObject(rawResult);

        ZoneId zoneId = ZoneId.of(timelineResponse.getString("timezone"));

        weatherReport.setLocation(String.format("Weather data for: %s%n", timelineResponse.getString("resolvedAddress")));

        JSONArray values = timelineResponse.getJSONArray("days");

        JSONObject dayFirstValue = values.getJSONObject(0);

        ZonedDateTime firstDatetime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(dayFirstValue.getLong("datetimeEpoch")), zoneId);

        weatherReport.setCurrentWeather(String.format("%s\t%.1f\t%.1f\t%.1f\t%s%n", firstDatetime.format(DateTimeFormatter.ISO_LOCAL_DATE),
                dayFirstValue.getDouble("tempmax"),
                dayFirstValue.getDouble("tempmin"),
                dayFirstValue.getDouble("precip"),
                dayFirstValue.getString("source")));

        weatherReport.addToPredictWeatherList("Date\tMaxTemp\tMinTemp\tPrecip\tSource\n");

        for (int i = 1; i < values.length(); i++) {
            JSONObject dayValue = values.getJSONObject(i);
            ZonedDateTime datetime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(dayValue.getLong("datetimeEpoch")), zoneId);

            double maxtemp = dayValue.getDouble("tempmax");
            double mintemp = dayValue.getDouble("tempmin");
            double pop = dayValue.getDouble("precip");
            String source = dayValue.getString("source");

            weatherReport.addToPredictWeatherList(String.format("%s\t%.1f\t%.1f\t%.1f\t%s%n", datetime.format(DateTimeFormatter.ISO_LOCAL_DATE), maxtemp, mintemp, pop, source));
        }
        return weatherReport;
    }
}