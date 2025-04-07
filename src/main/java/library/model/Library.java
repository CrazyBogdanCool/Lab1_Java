package library.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Library {
    private List<Book> books = new ArrayList<>();
    private List<Reader> readers = new ArrayList<>();

    public List<Book> getBooks() {
        return books;
    }

    public List<Reader> getReaders() {
        return readers;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addReader(Reader reader) {
        readers.add(reader);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public void removeReader(Reader reader) {
        readers.remove(reader);
    }

    public void printLibraryInfo() {
        System.out.println("Книги в бібліотеці:");
        books.forEach(System.out::println);
        System.out.println("Читачі:");
        readers.forEach(reader -> {
            System.out.println("- " + reader.getName() + ", взяв: " + reader.getBorrowedBooks().size() + " книг(и)");
        });
    }

    public void sortBooksByTitle() {
        books.sort(Comparator.comparing(Book::getTitle));
    }

    public void sortBooksByAuthor() {
        books.sort(Comparator.comparing(Book::getAuthor));
    }

    public void sortReadersByName() {
        readers.sort(Comparator.comparing(Reader::getName));
    }
}