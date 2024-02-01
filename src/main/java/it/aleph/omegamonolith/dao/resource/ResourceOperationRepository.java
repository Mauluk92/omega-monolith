package it.aleph.omegamonolith.dao.resource;

import it.aleph.omegamonolith.model.resource.response.ResourceOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceOperationRepository extends JpaRepository<ResourceOperation, Long> {
}
