package bm.erciyes.edu.tr.project.Model;

import java.util.Objects;

public class MovidleModel {
    public String no;
     public String title;
    public String year;
    public String genre;
    public String origin;
    public String director;
    public String star;
    public String imdb_link;



    public MovidleModel(String no, String title, String year, String genre, String origin, String director, String star, String imdb_link) {
        this.no = no;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.origin = origin;
        this.director = director;
        this.star = star;
        this.imdb_link = imdb_link;
    }

    @Override
    public String toString() {
        return "No: " + no + "\n"
                + "Title: " + title + "\n"
                + "Year: " + year + "\n"
                + "Genre: " + genre + "\n"
                + "Origin: " + origin + "\n"
                + "Director: " + director + "\n"
                + "Star: " + star + "\n"
                + "Link: " + imdb_link;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        MovidleModel movidleModel = (MovidleModel) obj;
        return Objects.equals(no, movidleModel.no) &&
                Objects.equals(title, movidleModel.title) &&
                Objects.equals(year, movidleModel.year) &&
                Objects.equals(genre, movidleModel.genre) &&
                Objects.equals(origin, movidleModel.origin) &&
                Objects.equals(director, movidleModel.director) &&
                Objects.equals(star, movidleModel.star) &&
                Objects.equals(imdb_link, movidleModel.imdb_link);
    }
    @Override
    public int hashCode() {
        return Objects.hash(no, title, year, genre, origin, director, star, imdb_link);
    }
    public int compareTo (String a,String b){
        return a.compareTo(b);
    }

}

