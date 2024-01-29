package it.aleph.omegamonolith.dao.resource;

import it.aleph.omegamonolith.model.resource.request.RequestedResourceOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestedResourceRepository extends JpaRepository<RequestedResourceOperation, Long> {
}
