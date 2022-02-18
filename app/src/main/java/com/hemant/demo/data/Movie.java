package com.hemant.demo.data;

public class Movie {

    private final String title;
    private final String poster;
    private final String overview;
    private final Double rating;

    public Movie(String title , String poster , String overview , Double rating){
        this.title = title;
        this.poster = poster;
        this.overview = overview;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getOverview() {
        return overview;
    }

    public Double getRating() {
        return rating;
    }
}