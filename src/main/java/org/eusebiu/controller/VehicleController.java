package org.eusebiu.controller;

import org.eusebiu.dto.CarResponse;
import org.eusebiu.models.Vehicle;
import org.eusebiu.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/vehicles")
public class VehicleController {

    // 1. Aducem teava catre baza de date!
    @Autowired
    private VehicleRepository vehicleRepository;

    // API 7: GET ALL CARS (DIN BAZA DE DATE!)
    @GetMapping
    public ResponseEntity<List<CarResponse>> getAllVehicles() {

        // 2. Scoatem absolut TOATE masinile din tabelul 'vehicles'
        List<Vehicle> masiniDinBazaDeDate = vehicleRepository.findAll();

        // 3. Pregatim lista pentru frontend (cutiile DTO)
        List<CarResponse> masiniPentruFrontend = new ArrayList<>();

        // 4. Luam fiecare masina din baza de date si o bagam in "cutia" ei de DTO
        for (Vehicle v : masiniDinBazaDeDate) {
            String fullName = v.getBrand() + " " + v.getModel(); // Le lipim (ex: Toyota + Corolla)

            CarResponse dto = new CarResponse(
                    v.getId(),
                    fullName,
                    v.getFabrYear(),
                    v.getType(),
                    v.getLocation(),
                    v.getRating(),
                    v.getPricePerDay(),
                    v.getImageUrl()
            );
            masiniPentruFrontend.add(dto);
        }

        // 5. Trimitem lista! Daca nu ai nicio masina in baza de date, va trimite o lista goala [].
        return ResponseEntity.ok(masiniPentruFrontend);
    }
}