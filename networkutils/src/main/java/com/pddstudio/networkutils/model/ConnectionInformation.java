/*
 * Copyright 2016 Patrick J
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.pddstudio.networkutils.model;

import com.google.gson.annotations.SerializedName;

/**
 * This class holds several information about the current user's connection.
 */
public class ConnectionInformation {

    private String as;
    private String city;
    private String country;
    private String countryCode;
    private String isp;
    @SerializedName("lat") private String latitude;
    @SerializedName("lon") private String longitude;
    @SerializedName("org") private String organization;
    private String query;
    private String region;
    private String regionName;
    private String status;
    private String timezone;
    private String zip;

    /**
     * Empty default constructor.
     */
    public ConnectionInformation() {}

    /**
     * Returns the Aerospace Standard of the current connection's provider.
     * @return The Aerospace Standard of the current connection's provider.
     */
    public String getAs() {
        return as;
    }

    /**
     * Returns the current City, based on the connection's provider location.
     * @return The current City, based on the connection's provider location.
     */
    public String getCity() {
        return city;
    }

    /**
     * Returns the current Country.
     * @return The current Country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Returns the current Country's country code.
     * @return The current Country's country code.
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Returns the current ISP.
     * @returnn The current ISP.
     */
    public String getIsp() {
        return isp;
    }

    /**
     * Returns the current Latitude, based on the IP-Address/ISP resolve location.
     * @return The current Latitude, based on the IP-Address/ISP resolve location.
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Returns the current Longitude, based on the IP-Address/ISP resolve location.
     * @return The current Longitude, based on the IP-Address/ISP resolve location.
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Returns the name of the current ISP.
     * @return the name of the current ISP.
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * Returns the name of the region based on the IP-Address/ISP resolve location (Short form).
     * @return The name of the region based on the IP-Address/ISP resolve location (Short form).
     */
    public String getRegion() {
        return region;
    }

    /**
     * Returns the name of the region based on the IP-Address/ISP resolve location (Long form).
     * @return The name of the region based on the IP-Address/ISP resolve location (Long form).
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * Returns the current timezone based on the resolved location.
     * @return The current timezone based on the resolved location.
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * Returns the zip code based on the resolved location.
     * @return The zip code based on the resolved location.
     */
    public String getZip() {
        return zip;
    }
}
