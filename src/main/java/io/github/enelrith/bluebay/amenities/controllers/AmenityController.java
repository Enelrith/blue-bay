package io.github.enelrith.bluebay.amenities.controllers;

import io.github.enelrith.bluebay.amenities.dto.AddAmenityRequest;
import io.github.enelrith.bluebay.amenities.services.AmenityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/amenities")
public class AmenityController {
    private final AmenityService amenityService;

    @PostMapping
    public ResponseEntity<Void> addAmenity(@RequestBody AddAmenityRequest request) {
        amenityService.addAmenity(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
