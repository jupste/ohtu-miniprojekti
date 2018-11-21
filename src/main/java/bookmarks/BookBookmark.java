/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookmarks;

import java.util.ArrayList;
import javax.persistence.*;

/**
 * Class that is used to store bookmarks of books.
 *
 * @author jussiste
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class BookBookmark extends AbstractBookmark {
    private String ISBN;
    public String getISBN(){
        return ISBN;
    }
    public void setISBN(String ISBN){
        this.ISBN=ISBN;
    }
    // Hibernate requires a constructor with no parameters
    public BookBookmark(){
        tags = new ArrayList<String>();
        relatedCourses = new ArrayList<String>();
        prerequisiteCourses = new ArrayList<String>();
    }

    public BookBookmark(String author, String title, String ISBN, ArrayList<String> tags, ArrayList<String> prerequisiteCourses, ArrayList<String> releatedCourses, String description, String comment) {
        super.author = author;
        super.title = title;
        this.ISBN = ISBN;
        super.tags = tags;
        super.prerequisiteCourses = prerequisiteCourses;
        super.relatedCourses = releatedCourses;
        super.description = description;
        super.comment = comment;
    }

    @Override
    public String toString() {
        return   "Kirjoittaja: " + author + "\n"
               + " Otsikko: " + title +  "\n"
               + " Tyyppi: Kirja" +"\n"
               + " ISBN: " + ISBN + "\n"
               + " Tagit: "  + tagsStr() + "\n"
               + " Esitietokurssit: " + preqCoursesStr() + "\n"
               + " Samankaltaisia kursseja: " + relatedCoursesStr() + "\n"
               + " Kuvaus: " + description + "\n"
               + " Kommentti: " + comment;
    }

}
