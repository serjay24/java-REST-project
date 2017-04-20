/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package LibrayObjects;

/**
 *
 * @author Serjay
 */
public class Book {
    public String title;
    public String author;
    public int published_year;
    public String country;

    public Book(String title, String author, int published_year, String country) {
        this.title = title;
        this.author = author;
        this.published_year = published_year;
        this.country = country;
    }
}
