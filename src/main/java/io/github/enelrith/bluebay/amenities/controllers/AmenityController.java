package io.github.enelrith.bluebay.amenities.controllers;

import io.github.enelrith.bluebay.amenities.dto.AddAmenityRequest;
import io.github.enelrith.bluebay.amenities.services.AmenityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the /amenities endpoint
 */
@RestController
@AllArgsConstructor
@RequestMapping("/amenities")
@Tag(name = "Amenities", description = "Handles all operations regarding amenities")
public class AmenityController {
    private final AmenityService amenityService;

    /**
     * Adds an amenity to the database
     * @param request The new amenity's name
     * @return 201 Created
     */
    @Operation(summary = "Add a new amenity")
    @ApiResponse(responseCode = "201")
    @ApiResponse(responseCode = "409", description = "If an amenity with the same name already exists")
    @ApiResponse(responseCode = "400", description = "If the request includes invalid data")
    @PostMapping
    public ResponseEntity<Void> addAmenity(@RequestBody AddAmenityRequest request) {
        amenityService.addAmenity(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
