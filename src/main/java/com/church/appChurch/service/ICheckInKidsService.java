package com.church.appChurch.service;

import com.church.appChurch.model.dto.CheckInKidsRequestDTO;
import com.church.appChurch.model.dto.CheckInKidsResponseDTO;

import java.util.List;

public interface ICheckInKidsService {

    public CheckInKidsResponseDTO realizarCheckIn(Long igrejaId, CheckInKidsRequestDTO dto);
    public void realizarCheckOut(Long checkInKidsId);
    public List<CheckInKidsResponseDTO> listarAtivos(Long igrejaId);


}
