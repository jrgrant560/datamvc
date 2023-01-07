package com.datamvc.models;

import java.sql.Timestamp;

public class City {
    private int city_id;
    private String city;
    private int country_id;
    private Timestamp last_update;

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public Timestamp getLast_update() {
    	return last_update;
    }
    
    public void setLast_update(Timestamp last_update) {
    	this.last_update = last_update;
    }
}
