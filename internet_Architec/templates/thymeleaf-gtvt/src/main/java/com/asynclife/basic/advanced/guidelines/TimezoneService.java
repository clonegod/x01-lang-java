package com.asynclife.basic.advanced.guidelines;

import java.io.IOException;
import java.util.TimeZone;

public interface TimezoneService {

    TimeZone getTimeZone(final double lat, final double lon) throws IOException;
}
