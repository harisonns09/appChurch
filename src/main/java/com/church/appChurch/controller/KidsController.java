package com.church.appChurch.controller;

import com.church.appChurch.model.dto.CheckInKidsRequestDTO;
import com.church.appChurch.model.dto.CheckInKidsResponseDTO;
import com.church.appChurch.service.ICheckInKidsService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/igrejas/{igrejaId}/kids")
@CrossOrigin(origins = "*")
public class KidsController {

    @Autowired
    private ICheckInKidsService checkInKidsService;

    @PostMapping("/checkin")
    public ResponseEntity<CheckInKidsResponseDTO> checkin(@PathVariable Long igrejaId, @RequestBody CheckInKidsRequestDTO dto){
        return ResponseEntity.ok(checkInKidsService.realizarCheckIn(igrejaId, dto));
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<CheckInKidsResponseDTO>> listarAtivos(@PathVariable Long igrejaId){
        return ResponseEntity.ok(checkInKidsService.listarAtivos(igrejaId));
    }

    @PostMapping("/checkout/{checkInId}")
    public ResponseEntity<Void> checkout(@PathVariable Long checkInId){
        checkInKidsService.realizarCheckOut(checkInId);
        return ResponseEntity.ok().build();
    }
}
