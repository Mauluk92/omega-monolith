package it.aleph.omegamonolith.service.catalog;

import it.aleph.omegamonolith.dto.catalog.AssociateBookDto;
import it.aleph.omegamonolith.dto.catalog.BookDto;
import it.aleph.omegamonolith.dto.catalog.CreateBookDto;

import java.util.List;

public interface BookService {

    BookDto addBook(CreateBookDto createBookDto);
    BookDto updateBook(Long id, BookDto updateBookDto);
    BookDto updateBookStatus(Long id, Boolean status);
    BookDto getBookById(Long id);
    void removeBookById(Long id);
    BookDto associateBook(Long id,  AssociateBookDto associateBookDto);
    List<BookDto> filteredBookSearch(Integer pageSize, Integer pageNum, Long authorId, Long tagId, String title);
}
