package it.aleph.omegamonolith.dao.catalog;

import it.aleph.omegamonolith.model.catalog.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
