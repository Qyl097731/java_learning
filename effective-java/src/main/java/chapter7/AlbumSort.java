package chapter7;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * @description
 * @date:2022/9/4 14:50
 * @author: qyl
 */
public class AlbumSort {
    public static void main(String[] args) {
        List<Album> albums = List.of(
                new Album("album1", new Artist("qyl", 20), 20),
                new Album("album2", new Artist("zhy", 30), 30));
        Map<Artist, Album> topHits = albums.stream().collect(Collectors.toMap(Album::getArtist, v -> v,
                BinaryOperator.maxBy(Comparator.comparing(Album::getSales))));
        topHits.forEach((key, value) -> System.out.println(value));
    }
}

class Album {
    String name;
    Artist artist;
    Integer sales;

    public Album(String name, Artist artist, Integer sales) {
        this.name = name;
        this.artist = artist;
        this.sales = sales;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    @Override
    public String toString() {
        return "Album{" +
                "name='" + name + '\'' +
                ", artist=" + artist +
                ", sales=" + sales +
                '}';
    }
}

class Artist {
    String name;
    Integer age;

    public Artist(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
