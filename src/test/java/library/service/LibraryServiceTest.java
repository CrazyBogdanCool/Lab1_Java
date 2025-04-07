package library.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import library.model.Book;
import library.model.Library;
import library.model.Reader;
import library.util.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LibraryServiceTest {
    private Library library;
    private LibraryService service;

    @BeforeEach
    public void setup() {
        library = new Library();
        service = new LibraryService(library);
    }

    @Test
    public void testBorrowBook_Success() {
        Book book = new Book("Book1", "Author1");
        Reader reader = new Reader("Alice");
        library.addBook(book);
        library.addReader(reader);

        assertTrue(service.borrowBook("Alice", "Book1"));
        assertFalse(book.isAvailable());
        assertEquals(1, reader.getBorrowedBooks().size());
    }

    @Test
    public void testBorrowBook_ReaderNotFound() {
        library.addBook(new Book("Book1", "Author1"));
        assertFalse(service.borrowBook("Ghost", "Book1"));
    }

    @Test
    public void testBorrowBook_BookNotFound() {
        library.addReader(new Reader("Bob"));
        assertFalse(service.borrowBook("Bob", "MissingBook"));
    }

    @Test
    public void testBorrowBook_AlreadyBorrowed() {
        Book book = new Book("Book2", "Author2");
        Reader r1 = new Reader("Reader1");
        Reader r2 = new Reader("Reader2");
        library.addBook(book);
        library.addReader(r1);
        library.addReader(r2);

        service.borrowBook("Reader1", "Book2");
        assertFalse(service.borrowBook("Reader2", "Book2")); // вже взято
    }

    @Test
    public void testReturnBook_Success() {
        Book book = new Book("Book3", "Author3");
        Reader reader = new Reader("Alice");
        reader.borrowBook(book);
        library.addBook(book);
        library.addReader(reader);

        assertTrue(service.returnBook("Alice", "Book3"));
        assertTrue(book.isAvailable());
        assertEquals(0, reader.getBorrowedBooks().size());
    }

    @Test
    public void testReturnBook_ReaderNotFound() {
        assertFalse(service.returnBook("Nobody", "AnyBook"));
    }

    @Test
    public void testReturnBook_BookNotOwned() {
        Book book = new Book("Book4", "Author4");
        Reader reader = new Reader("ReaderX");
        library.addBook(book);
        library.addReader(reader);

        assertFalse(service.returnBook("ReaderX", "Book4"));
    }

    @Test
    public void testJsonExportImport_WithMock() throws Exception {
        Library mockLibrary = mock(Library.class);
        ObjectMapper mockMapper = mock(ObjectMapper.class);

        File temp = File.createTempFile("test", ".json");
        temp.deleteOnExit();

        // Експорт
        JsonUtil.exportToJson(new Library(), temp.getAbsolutePath());
        assertTrue(temp.exists());

        // Імпорт
        Library result = JsonUtil.importFromJson(temp.getAbsolutePath());
        assertNotNull(result);
    }

    @Test
    public void testJsonImport_FileNotFound() {
        assertThrows(IOException.class, () -> {
            JsonUtil.importFromJson("non_existing_file.json");
        });
    }
}