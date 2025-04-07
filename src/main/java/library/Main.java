package library;

import library.model.Book;
import library.model.Library;
import library.model.Reader;
import library.service.LibraryService;
import library.util.JsonUtil;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        LibraryService service = new LibraryService(library);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Додати книгу");
            System.out.println("2. Додати читача");
            System.out.println("3. Взяти книгу");
            System.out.println("4. Повернути книгу");
            System.out.println("5. Зберегти в файл");
            System.out.println("6. Завантажити з файлу");
            System.out.println("7. Вивести інформацію");
            System.out.println("8. Сортувати книги за назвою");
            System.out.println("9. Сортувати книги за автором");
            System.out.println("10. Сортувати читачів за ім’ям");
            System.out.println("11. Вийти");
            System.out.print("Ваш вибір: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1" -> {
                    System.out.print("Назва: ");
                    String title = scanner.nextLine();
                    System.out.print("Автор: ");
                    String author = scanner.nextLine();
                    library.addBook(new Book(title, author));
                }
                case "2" -> {
                    System.out.print("Ім’я: ");
                    String name = scanner.nextLine();
                    library.addReader(new Reader(name));
                }
                case "3" -> {
                    System.out.print("Ім’я читача: ");
                    String name = scanner.nextLine();
                    System.out.print("Назва книги: ");
                    String title = scanner.nextLine();
                    boolean result = service.borrowBook(name, title);
                    System.out.println(result ? "Успішно взято!" : "Не вдалося.");
                }
                case "4" -> {
                    System.out.print("Ім’я читача: ");
                    String name = scanner.nextLine();
                    System.out.print("Назва книги: ");
                    String title = scanner.nextLine();
                    boolean result = service.returnBook(name, title);
                    System.out.println(result ? "Книгу повернуто!" : "Не вдалося.");
                }
                case "5" -> {
                    try {
                        JsonUtil.exportToJson(library, "library.json");
                        System.out.println("Збережено.");
                    } catch (Exception e) {
                        System.out.println("Помилка збереження.");
                    }
                }
                case "6" -> {
                    try {
                        Library loaded = JsonUtil.importFromJson("library.json");
                        library.getBooks().clear();
                        library.getReaders().clear();
                        library.getBooks().addAll(loaded.getBooks());
                        library.getReaders().addAll(loaded.getReaders());
                        System.out.println("Завантажено.");
                    } catch (Exception e) {
                        System.out.println("Помилка завантаження.");
                    }
                }
                case "7" -> library.printLibraryInfo();
                case "8" -> library.sortBooksByTitle();
                case "9" -> library.sortBooksByAuthor();
                case "10" -> library.sortReadersByName();
                case "11" -> {
                    System.out.println("Вихід...");
                    return;
                }
                default -> System.out.println("Невірна опція.");
            }
        }
    }
}
