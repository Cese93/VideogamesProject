package com.example.erik.videogamesproject.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Marco on 18/10/2016.
 */

public class Console extends Product implements Serializable {

    private String developer;
    private String description;
    private Date releaseDate;

    public Console () {
        super();
    }

    public String getDeveloper () {
        return developer;
    }

    public void setDeveloper ( String developer ) {
        this.developer = developer;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription ( String description ) {
        this.description = description;
    }

    public Date getReleaseDate () {
        return releaseDate;
    }

    public void setReleaseDate ( Date releaseDate ) {
        this.releaseDate = releaseDate;
    }
}
