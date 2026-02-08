package com.church.appChurch.repository;

import com.church.appChurch.model.Igreja;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IgrejaRepository extends ListCrudRepository<Igreja, Long> {

    public Igreja save(Igreja igreja);

}
