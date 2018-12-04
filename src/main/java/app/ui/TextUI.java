package app.ui;

import app.domain.Tag;
import app.io.IO;
import bookmarks.Bookmark;
import bookmarks.BookBookmark;
import bookmarks.BlogBookmark;
import bookmarks.OtherBookmark;
import java.util.*;

public class TextUI {

    private final IO io;
    private final String defaultSuccessMessage = "Your bookmark has been read!";

    public TextUI(IO io) {
        this.io = io;
    }

    public void printWelcomeMessage() {
        io.println("Welcome!");
    }

    public void printGoodbyeMessage() {
        io.println("Goodbye");
    }

    public void printUnrecognizedOption() {
        io.println("Unrecognized option");
    }

    public String getMenuCommand() {
        io.println("\nSelect one of the following options \n1. Add a new bookmark by typing \n2. List all existing bookmarks\n3. Search a specific bookmark"
                + "\n4. Edit a bookmark\n5. Delete a bookmark\n0. Exit the application");
        return io.nextLine();
    }

    public String askForField() {
        io.println("Which field would you like to search?");
        io.println("Give field (T = Title) (D = Description)");
        String command = io.nextLine();
        switch (command) {
            case ("T"):
                return "title";
            case ("D"):
                return "description";
            default:
                return "";
        }
    }

    public String askForSearch() {
        io.println("Give a search term: ");
        return io.nextLine();
    }

    public Bookmark askForBookmark() {
        io.println("Add a new bookmark.");
        io.println("Give type (B = Book) (BG = Blog), (O = Other): ");
        String type = io.nextLine();

        Bookmark bookmark = null;

        if (type.equals("B")) {
            bookmark = askForBookBookmarkInfo();
        } else if (type.equals("BG")) {
            bookmark = askForBlogBookmarkInfo();
        } else if (type.equals("O")) {
            bookmark = askForOtherBookmarkInfo();
        }

        if (bookmark != null) {
            io.println(defaultSuccessMessage);
            return bookmark;
        }

        io.println("Invalid choice");
        return null;

    }
    
    public String askForListingMethod() {
        io.println("Choose listing method:");
        io.println("(T = Title)(CD = Creation time Descending)(CA = Creation time Ascending)");
        return io.nextLine();
    }

    public void printBookmarkList(List<Bookmark> bookmarks) {
        if (bookmarks.isEmpty()) {
            io.println("There are currently no bookmarks in memory. Add a new bookmark with command 1");
        }
        for (Bookmark bmark : bookmarks) {
            io.println(bmark.toString());

            System.out.println("==================================================");
        }
    }
    
    private Bookmark askForOtherBookmarkInfo() {
        OtherBookmark bm = new OtherBookmark();
        String url = askForInput("Url: ");
        bm.setUrl(url);
        askForGeneralBookmarkInfo(bm);
        return bm;
    }

    private Bookmark askForBookBookmarkInfo() {
        BookBookmark bm = new BookBookmark();
        String isbn = askForInput("ISBN: ");
        bm.setISBN(isbn);

        String title = askForInput("Title: ");
        bm.setTitle(title);

        String author = askForInput("Author: ");
        bm.setAuthor(author);

        List<Tag> tagsList = askForTags();
        bm.setTags(tagsList);

        String description = askForInput("Description: ");
        bm.setDescription(description);

        return bm;
    }

    private Bookmark askForBlogBookmarkInfo() {
        BlogBookmark bm = new BlogBookmark();
        String url = askForInput("Url: ");
        bm.setUrl(url);
        askForGeneralBookmarkInfo(bm);
        return bm;
    }

    private Bookmark askForGeneralBookmarkInfo(Bookmark bookmark) {
        String title = askForInput("Title: ");
        bookmark.setTitle(title);

        List<Tag> tagsList = askForTags();
        bookmark.setTags(tagsList);

        String description = askForInput("Description: ");
        bookmark.setDescription(description);

        return bookmark;
    }

    public List<Tag> askForTags() {
        io.println("Tags (separated by \",\"): ");
        String input = io.nextLine();

        String[] tags = input.split(",");
        List<Tag> result = new ArrayList();
        for (int i = 0; i < tags.length; i++) {
            // pitäisikö tässä vaiheessa katsoa, että ei lisätä uutta tagia jos samanniminen on?
            result.add(new Tag(tags[i].trim()));
        }

        return result;
    }

    public long askForEntryToEdit(List<Bookmark> bookmarks) {
        System.out.println("Select an entry to edit/remove by typing its ID: ");
        for (Bookmark bookmark : bookmarks) {
            io.println(bookmark.shortPrint());
        }
        return (long) io.nextInt();
    }

    public String askForEditField(String bookmark) {
        String[] data = bookmark.split(" ");
        String ask = "Select field to edit:";
        System.out.println(data[3]);
        if (data[3].contains("Book")) {
            ask += "\nA = Author  \nT = title\nD = description \nX = tags";
        }
        if (data[3].contains("Blogpost")) {
            ask += "\nT = title\nU = url \nD = description \nX = tags ";
        }
        if (data[3].contains("Other")) {
            ask += "\nT = title\nU = url \nD = description \nX = tags";
        }
        System.out.println(ask);
        String field = io.nextLine();
        while (true) {
            if (field.equals("A")) {
                return "author";
            } else if (field.equals("T")) {
                return "title";
            } else if (field.equals("U")) {
                return "url";
            } else if (field.equals("D")) {
                return "description";
            } else if (field.equals("X")) {
                return "tags";
            } else {
                field = io.nextLine();
            }
        }
    }

    public String askForNewField(String field) {
        System.out.println("Give new entry for " + field);
        return io.nextLine();
    }

    private String askForInput(String prompt) {
        io.println(prompt);
        return io.nextLine();
    }

    public Long askForBookmarkToDelete() {
        io.println("Give ID of the bookmark you want to delete: ");
        Long id;
        try {
            id = Long.parseLong(io.nextLine());
        } catch (NumberFormatException e) {
            return 0L;
        }
        return id;
    }
}
