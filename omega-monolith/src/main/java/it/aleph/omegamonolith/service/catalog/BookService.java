package it.aleph.omegamonolith.service.catalog;

import it.aleph.omegamonolith.dto.catalog.book.AssociateBookDto;
import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import it.aleph.omegamonolith.dto.catalog.book.CreateBookDto;

import java.util.List;

public interface BookService {

    CreateBookDto addBook(CreateBookDto createBookDto);
    BookDto updateBook(Long id, BookDto updateBookDto);
    BookDto updateBookStatus(Long id, Boolean status);
    BookDto getBookById(Long id);
    void removeBookById(Long id);
    BookDto associateBook(Long id,  AssociateBookDto associateBookDto);
    List<BookDto> filteredBookSearch(Integer pageSize, Integer pageNum, Long authorId, Long tagId, String title, String address);

    void addBooks(List<BookDto> listBookDto);
}
