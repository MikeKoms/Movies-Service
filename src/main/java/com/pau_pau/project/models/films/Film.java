package com.pau_pau.project.models.films;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.pau_pau.project.models.accounts.Account;
import com.pau_pau.project.models.accounts.Role;
import com.pau_pau.project.models.directors.Director;
import com.pau_pau.project.models.states.FilmState;
import com.pau_pau.project.models.states.FilmStatus;
import com.pau_pau.project.models.states.concretes.ApprovedFilmState;
import com.pau_pau.project.models.states.concretes.NewlyFilmState;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.naming.NoPermissionException;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Table(name = "films")
public class Film {

    public static Film fromFilmDTOModel(FilmDTO filmDTO) {
        Film film = new Film();
        film.id = filmDTO.getId();
        film.title = filmDTO.getTitle();
        film.genre = filmDTO.getGenre();
        film.year = filmDTO.getYear();
        film.country = filmDTO.getCountry();
        /* после создания модели из DTO сет директоров БУДЕТ ПУСТОЙ!!! */
        film.directorsId.addAll(filmDTO.getDirectorsId());
        film.genre = filmDTO.getGenre();
        film.release = filmDTO.getRelease();
        film.setCreationDate(filmDTO.getCreationDate());
        film.actors = filmDTO.getActors();
        film.description = filmDTO.getDescription();
        film.poster = filmDTO.getPoster();
        return film;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column
    private String title;

    @Column
    private Date year;

    @Column
    private String country;

    @Column
    @ManyToMany
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinTable(
            name = "films_directors",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "director_id")
    )
    private Set<Director> directors = new HashSet<>();

    @Transient
    private Set<Integer> directorsId = new HashSet<>();

    @Column
    private String genre;

    @Column(name = "release")
    private Date release;

    @Column
    private String poster;

    @Column
    private String actors;

    @Column
    private String description;

    @Column
    @Generated(GenerationTime.INSERT)
    private Date creationDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "state_id", referencedColumnName = "id")
    private FilmState state;

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

    public Set<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(Set<Director> directors) {
        this.directors = directors;
    }

    public Set<Integer> getDirectorsId() {
        return directorsId;
    }

    public void setDirectorsId(Set<Integer> directorsId) {
        this.directorsId = directorsId;
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

    public void setRelease(Date release) {
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    public FilmState getState() {
        return state;
    }

    public void setState(FilmState state) {
        this.state = state;
    }

    public boolean isApproved() {
        return state.getStatusName().equals(FilmStatus.APPROVED);
    }

    public void initFilmState(Account account) throws NoPermissionException {
        Role permissionsLevel = account.getPermissionsLevel();

        if (permissionsLevel.equals(Role.ADMIN)) {
            state = new ApprovedFilmState(account);
            return;
        }

        if (permissionsLevel.equals(Role.EDITOR)) {
            state = new NewlyFilmState(account);
        }

        throw new NoPermissionException("Denied");
    }

    @Override
    public boolean equals(Object other){
        if (this == other) return true;
        if (other == null) return false;
        if(this.getClass() != other.getClass()) return false;
        Film otherObj = (Film) other;

        //TODO are you sure that this is right (above)? If you can improve, do it
        boolean res = true;
        res = res && this.country.equals(otherObj.country);
        res = res && (this.genre.equals(otherObj.genre));
        res = res && (this.title.equals(otherObj.title));
        //TODO need to fix different hours
//        res = res && (this.release.equals(otherObj.release));
//        System.out.println(this.release + " \n" + otherObj.release);
//        System.out.println("res4 = " + res);
//        res = res && (this.year.equals(otherObj.year));
//        System.out.println(this.year + "\n" + otherObj.year);
//        System.out.println("res6 = " + res);
//        res = res && (this.directors.equals(otherObj.directors));
//        System.out.println("res7 = " + res);
        return res;
    }

    //TODO need improvement?
    @Override
    public int hashCode()
    {
        return 76+133*id;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
