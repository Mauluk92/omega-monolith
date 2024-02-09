package it.aleph.omegamonolith.config.resource.batch.writer;

import it.aleph.omegamonolith.dao.resource.RequestedResourceRepository;
import it.aleph.omegamonolith.model.resource.request.RequestedResourceOperation;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestResourceWriter implements ItemWriter<RequestedResourceOperation> {

    private final RequestedResourceRepository repository;

    @Override
    public void write(@Nonnull Chunk<? extends RequestedResourceOperation> chunk) throws Exception {
        for (RequestedResourceOperation resource : chunk) {
            if (repository.findAllByResource(resource.getAddress(), PageRequest.of(0, 10)).hasNext()) {
                resource.setValid(false);
            }
            repository.save(resource);
        }

    }
}
