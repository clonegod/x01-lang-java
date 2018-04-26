package com.asynclife.basic.advanced.guidelines;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TimeZone;

public class TimezoneServiceImpl implements TimezoneService {

    @Override
    public TimeZone getTimeZone(final double lat, final double lon) throws IOException {
        final URL url = new URL(String.format(
                "http://api.geonames.org/timezone?lat=%.2f&lng=%.2f&username=demo",
                lat, lon
        ));
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setConnectTimeout(1000);
        connection.setReadTimeout(1000);
        connection.connect();

        int status = connection.getResponseCode();
        if (status == 200) {
            // Do something here
        }

        return TimeZone.getDefault();
    }
}
