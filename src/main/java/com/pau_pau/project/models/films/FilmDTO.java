package com.pau_pau.project.models.films;

import com.pau_pau.project.models.directors.Director;
import com.pau_pau.project.models.directors.DirectorDTO;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class FilmDTO {

    public static FilmDTO fromFilmModel(Film film) {
        FilmDTO filmDTO = new FilmDTO();
        filmDTO.id = film.getId();
        filmDTO.title = film.getTitle();
        filmDTO.year = film.getYear();
        filmDTO.country = film.getCountry();
        for (Director director : film.getDirectors()) {
            filmDTO.directors.add(DirectorDTO.fromDirectorModel(director));
        }
        filmDTO.genre = film.getGenre();

        filmDTO.release = film.getRelease();
        filmDTO.actors = film.getActors();
        filmDTO.description = film.getDescription();
        filmDTO.poster = film.getPoster();
        return filmDTO;
    }

    @ApiModelProperty(readOnly = true)
    private int id;

    private String title;

    private Date year;

    private String country;

    private Set<DirectorDTO> directors = new HashSet<>();

    private String genre;

    private Date release;

    private String poster;

    private String actors;

    private String description;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Set<DirectorDTO> getDirectors() {
        return directors;
    }

    public void setDirectors(Set<DirectorDTO> directors) {
        this.directors = directors;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getRelease() {
        return release;
    }

    public void setRelease(Timestamp release) {
        this.release = release;
    }


    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
