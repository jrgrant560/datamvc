package com.datamvc.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.datamvc.models.City;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Value;
import java.sql.Statement;
import java.sql.ResultSet;

@Controller
@RequestMapping({ "/", "/home" })
public class CitiesController {

    @GetMapping("/cities")
    public String getAllCities(Model model) {

        List<City> cities = new ArrayList<City>(); //ArrayList of all City objects from the db

        Connection con;
        try {
            // connects to the database using the given parameters
            con = DriverManager.getConnection(url, username, password);

            Statement stmt = con.createStatement(); //object that executes a static SQL statement
            ResultSet rs = stmt.executeQuery("SELECT * FROM city"); // run this SQL query
            //ResultSet rsFirst100 = stmt.executeQuery("SELECT * FROM city WHERE city_id < 100"); // run this SQL query

            // loop that runs through each row in the table from the SQL query
            while (rs.next()) {

                // create a new city object from model
                City newCity = new City();

                // get the values from each column of the current row and add them to the new city
                newCity.setCity_id(rs.getInt("city_id"));
                newCity.setCity(rs.getString("city"));
                newCity.setCountry_id(rs.getInt("country_id"));
                newCity.setLast_update(rs.getTimestamp("last_update"));

                // add the new city to the citys ArrayList
                cities.add(newCity);
            }

            //closes the ResultSet; required for multiple SQL ResultSets running on the same backend
            try { rs.close(); } catch (Exception ignore) { }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        model.addAttribute("cities", cities); // ??? NOT QUITE SURE WHAT THIS IS DOING; it breaks thymeleaf on the html page if I take it out
        return "cities";
    }

    // 'url' data item
    @Value("${spring.datasource.url}")
    private String url;

    // 'username' data item
    @Value("${spring.datasource.username}")
    private String username;

    // 'password' data item
    @Value("${spring.datasource.password}")
    private String password;

}