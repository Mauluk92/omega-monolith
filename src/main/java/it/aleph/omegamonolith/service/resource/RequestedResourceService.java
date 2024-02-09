package it.aleph.omegamonolith.service.resource;

import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import it.aleph.omegamonolith.dto.resource.request.RequestedResourceOperationDto;

public interface RequestedResourceService {

    void insertOperation(BookDto bookDto);
}
