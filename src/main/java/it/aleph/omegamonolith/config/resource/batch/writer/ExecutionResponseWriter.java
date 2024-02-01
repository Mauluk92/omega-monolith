package it.aleph.omegamonolith.config.resource.batch.writer;

import it.aleph.omegamonolith.dao.catalog.BookRepository;
import it.aleph.omegamonolith.dao.resource.ResourceOperationRepository;
import it.aleph.omegamonolith.dto.resource.response.ResourceOperationDto;
import it.aleph.omegamonolith.mapper.catalog.BookDtoMapper;
import it.aleph.omegamonolith.model.catalog.Book;
import it.aleph.omegamonolith.model.resource.response.OperationStatus;
import it.aleph.omegamonolith.model.resource.response.ResourceOperation;
import it.aleph.omegamonolith.service.catalog.BookService;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ExecutionResponseWriter implements ItemWriter<ResourceOperation> {

    private final ResourceOperationRepository resourceRepository;
    private final BookService bookService;
    private final BookDtoMapper mapper;


    @Override
    public void write(@Nonnull Chunk<? extends ResourceOperation> chunk) throws Exception {
        for(ResourceOperation resourceOperation : chunk){
            if(!resourceOperation.getOperationStatus().equals(OperationStatus.REJECTED)){
                Book book = mapper.toEntity(resourceOperation.getResource());
                switch (resourceOperation.getTypeOperation()){
                    case INSERTION -> {
                        bookService.addBook(mapper.toCreateDto(book));
                    }
                    case REMOVAL -> {
                        bookService.removeBookById(book.getId());
                    }
                    case UPDATE -> {
                        bookService.updateBook(book.getId(), mapper.toDto(book));
                    }
                }
            }
            resourceRepository.save(resourceOperation);
        }
    }
}
