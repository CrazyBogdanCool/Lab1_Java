package library.service;

import library.model.Book;
import library.model.Library;
import library.model.Reader;

import java.util.Optional;

public class LibraryService {
    private final Library library;

    public LibraryService(Library library) {
        this.library = library;
    }

    public boolean borrowBook(String readerName, String bookTitle) {
        Optional<Reader> reader = library.getReaders().stream().filter(r -> r.getName().equals(readerName)).findFirst();
        Optional<Book> book = library.getBooks().stream().filter(b -> b.getTitle().equals(bookTitle) && b.isAvailable()).findFirst();
        if (reader.isPresent() && book.isPresent()) {
            reader.get().borrowBook(book.get());
            return true;
        }
        return false;
    }

    public boolean returnBook(String readerName, String bookTitle) {
        Optional<Reader> reader = library.getReaders().stream().filter(r -> r.getName().equals(readerName)).findFirst();
        if (reader.isPresent()) {
            Optional<Book> book = reader.get().getBorrowedBooks().stream().filter(b -> b.getTitle().equals(bookTitle)).findFirst();
            book.ifPresent(reader.get()::returnBook);
            return book.isPresent();
        }
        return false;
    }
}
