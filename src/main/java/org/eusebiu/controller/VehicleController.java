package org.eusebiu.controller;

import org.eusebiu.dto.CarResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/vehicles")
public class VehicleController {

    // API 7: GET ALL CARS (PENTRU BROWSE CARS)
    // Link: GET http://localhost:8080/api/vehicles
    @GetMapping
    public ResponseEntity<List<CarResponse>> getAllVehicles() {
        // Pe viitor, aici vom chema VehicleService sa ne dea masinile din baza de date
        // ex: List<Vehicle> masini = vehicleRepository.findAll();
        List<CarResponse> cars = Arrays.asList(
                new CarResponse(1L, "Toyota Corolla", 2020, "Sedan", "Downtown", 4.8, 35.00),
                new CarResponse(2L, "Honda Civic", 2019, "Sedan", "Uptown", 4.5, 30.00),
                new CarResponse(3L, "BMW X5", 2021, "SUV", "Midtown", 4.9, 75.00)
        );
        return ResponseEntity.ok(cars);
    }
}