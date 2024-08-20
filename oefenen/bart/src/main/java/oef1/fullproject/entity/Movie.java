package oef1.fullproject.entity;

public class Movie {
    private String title;
    private String actors;
    private int year;

    public Movie(String title, String actors, int year) {
        this.title = title;
        this.actors = actors;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", actors='" + actors + '\'' +
                ", year=" + year +
                '}';
    }
}