package com.datamvc.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.datamvc.models.Actor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Value;
import java.sql.Statement;
import java.sql.ResultSet;

@Controller
@RequestMapping({ "/", "/home" })
public class ActorsController {

    //HOW THIS WORKS:
        // 1) Creates an ArrayList of Objects based on the Model
        // 2) connects to the database using the url, username, password parameters, which are provided by the application.properties file
        // 3) runs an SQL query specified by a String
        // 4) loops through all rows returned from the database query
            // 4a) creates a new object using the Model
            // 4b) assigns the VALUES from the SQL query to the Model's properties
            // 4c) adds the new object to the ArrayList
        // 5) ??? model.addAttribute ???
    @GetMapping("/actors")
    public String getAllActors(Model model) {

        List<Actor> actors = new ArrayList<Actor>(); //ArrayList of all Actor objects from the db

        Connection con;
        try {
            // connects to the database using the given parameters
            con = DriverManager.getConnection(url, username, password);

            Statement stmt = con.createStatement(); //object that executes a static SQL statement
            ResultSet rs = stmt.executeQuery("SELECT * FROM actor"); // run this SQL query
            //ResultSet rsFirst100 = stmt.executeQuery("SELECT * FROM actor WHERE actor_id < 100"); // run this SQL query

            // loop that runs through each row in the table from the SQL query
            while (rs.next()) {

                // create a new Actor object from model
                Actor newActor = new Actor();

                // get the values from each column of the current row and add them to the new Actor
                newActor.setActor_id(rs.getInt("actor_id"));
                newActor.setFirst_name(rs.getString("first_name"));
                newActor.setLast_name(rs.getString("last_name"));
                newActor.setLast_update(rs.getTimestamp("last_update"));

                // add the new actor to the actors ArrayList
                actors.add(newActor);
            }
            
            //closes the ResultSet; required for multiple SQL ResultSets running on the same backend
            try { rs.close(); } catch (Exception ignore) { }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        model.addAttribute("actors", actors); // ??? NOT QUITE SURE WHAT THIS IS DOING; it breaks thymeleaf on the html page if I take it out
        return "actors";
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