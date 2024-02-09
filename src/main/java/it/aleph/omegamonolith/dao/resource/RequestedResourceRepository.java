package it.aleph.omegamonolith.dao.resource;

import it.aleph.omegamonolith.model.resource.request.RequestedResourceOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface RequestedResourceRepository extends JpaRepository<RequestedResourceOperation, Long>, PagingAndSortingRepository<RequestedResourceOperation, Long> {


    @Query(nativeQuery = true, value = "SELECT * FROM request_operation WHERE (address = ? AND valid)")
    Page<RequestedResourceOperation> findAllByResource(String address, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM request_operation WHERE (NOT executed AND valid)")
    Page<RequestedResourceOperation> findAllByNotExecuted(Pageable pageable);

}
