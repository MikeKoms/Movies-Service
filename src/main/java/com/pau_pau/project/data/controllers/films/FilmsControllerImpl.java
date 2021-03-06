package com.pau_pau.project.data.controllers.films;

import com.pau_pau.project.data.controllers.ControllerConstants;
import com.pau_pau.project.data.services.accounts.AccountServiceImpl;
import com.pau_pau.project.data.services.films.FilmsServiceImpl;
import com.pau_pau.project.models.films.Film;
import com.pau_pau.project.models.films.FilmDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.management.InstanceNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "Movies", value = "Films", description = "Api for operations with films")
@RestController
@CrossOrigin
@RequestMapping(ControllerConstants.FILMS_URL)
public class FilmsControllerImpl implements FilmsController {

    @Autowired
    private FilmsServiceImpl filmsService;

    @Autowired
    private AccountServiceImpl accountService;

    /* ================================
                 GET METHODS
     ================================== */

    @Override
    public List<FilmDTO> getFilms(String title, Date year, String country, String genre, Date releaseDate) {
        if (year == null) {
            year = DEFAULT_FILM_YEAR.getTime();
        }
        if (releaseDate == null) {
            releaseDate = DEFAULT_FILM_RELEASE_DATE.getTime();
        }
        List<Film> films = filmsService.findFilms(title, year, country, genre, releaseDate);
        return films.stream().map(FilmDTO::fromFilmModel).collect(Collectors.toList());
    }

//    @Override
//    public List<FilmDTO> getActiveRequests() {
//        List<Film> films = filmsService.findActiveRequests();
//        return films.stream().map(FilmDTO::fromFilmModel).collect(Collectors.toList());
//    }

    @Override
    public FilmDTO getFilmById(int filmId) {
        try {
            Film film = filmsService.findFilmById(filmId);
            accountService.addFilmToHistory(film);
            return FilmDTO.fromFilmModel(film);
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    /* ================================
                 POST METHODS
     ================================== */

    @Override
    public FilmDTO addFilm(FilmDTO film) throws Exception {
        return FilmDTO.fromFilmModel(filmsService.addFilm(film));
    }

    /* ================================
                 PUT METHODS
     ================================== */

    @Override
    public FilmDTO updateFilm(int filmId, FilmDTO film) {
        try {
            return FilmDTO.fromFilmModel(filmsService.updateFilm(filmId, film));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    /* ================================7
                 DELETE METHODS
     ================================== */

    @Override
    public FilmDTO deleteFilm(int filmId) {
        try {
            return FilmDTO.fromFilmModel(filmsService.deleteFilmById(filmId));
        } catch (InstanceNotFoundException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public FilmDTO getRandomFilm() {
        return FilmDTO.fromFilmModel(filmsService.getRandomFilm());
    }

    @Override
    public FilmDTO publishFilm(int filmId) throws Exception {
        return FilmDTO.fromFilmModel(filmsService.publishFilm(filmId));
    }

    @Override
    public FilmDTO rejectFilm(int filmId, String comment) throws Exception {
        return FilmDTO.fromFilmModel(filmsService.rejectFilm(filmId, comment));
    }
}
