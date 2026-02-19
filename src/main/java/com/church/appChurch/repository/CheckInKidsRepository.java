package com.church.appChurch.repository;

import com.church.appChurch.enums.StatusCheckIn;
import com.church.appChurch.model.CheckInKids;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckInKidsRepository extends JpaRepository<CheckInKids,Long> {

    @Query("")
    List<CheckInKids> findByIgrejaIdAndStatusOrderByDataEntradaDesc(Long igrejaId, StatusCheckIn status);
}
