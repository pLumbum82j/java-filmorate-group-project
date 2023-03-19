package ru.application.filmorate.impl;

import ru.application.filmorate.model.Film;
import ru.application.filmorate.model.enums.FilmSort;

import java.util.List;

public interface FilmStorage {
    List<Film> get();

    Film getById(Integer filmId);

    List<Film> getPopularMoviesByLikes(Integer count);

    List<Film> getPopularMoviesByLikes(Integer count,Integer genreId);
    
    List<Film> getPopularMoviesByLikes(Integer count,Short year);
    
    List<Film> getPopularMoviesByLikes(Integer count,Integer genreId,Short year);

    List<Film> getCommonMovies(Integer userId, Integer friendId);

    List<Film> getBy(int directorId, FilmSort sortBy);

    Film add(Film film);

    Film update(Film film);

    void addGenres(Film film);

    void addDirectors(Film film);

    void removeGenres(Film film);

    void removeDirectors(Film film);

    List<Film> getRecommendedFilms(Integer userId);

    void removeFilmById(Integer id);
}