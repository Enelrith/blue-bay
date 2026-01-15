package io.github.enelrith.bluebay.properties.controllers;

import io.github.enelrith.bluebay.properties.dto.*;
import io.github.enelrith.bluebay.properties.services.PropertyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/properties")
@Tag(name = "Properties", description = "Handles all operations regarding properties")
public class PropertyController {
    private final PropertyService propertyService;

    /**
     * Endpoint for adding a new property
     * @param request Data about the new property
     * @return 200 OK and the new property's data
     */
    @Operation(summary = "Add a new property")
    @ApiResponse(
            responseCode = "201",
            description = "Property created",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AddPropertyResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "409",
            description = "The property already exists"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid data in the request"
    )
    @PostMapping
    public ResponseEntity<AddPropertyResponse> addProperty(@Valid @RequestBody AddPropertyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.addProperty(request));
    }

    /**
     * Endpoint for getting a specific property by its AMA number
     * @param request Contains the AMA number
     * @return 200 OK and the requested property's data
     */
    @Operation(summary = "Get a property")
    @ApiResponse(
            responseCode = "200",
            description = "Property found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GetPropertyResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Property not found"
    )
    @GetMapping("/{amaNumber}")
    public ResponseEntity<GetPropertyResponse> getProperty(@Valid @PathVariable(name = "amaNumber") GetPropertyRequest request) {
        return ResponseEntity.ok(propertyService.getProperty(request));
    }

    /**
     * Endpoint for getting all existing properties
     * @return 200 OK and the data of all the properties
     */
    @Operation(summary = "Get all properties")
    @ApiResponse(
            responseCode = "200",
            description = "At least one property found",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = GetPropertyResponse.class)
                    )

            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "No properties found"
    )
    @GetMapping
    public ResponseEntity<List<GetPropertyResponse>> getAllProperties() {
        return ResponseEntity.ok(propertyService.getAllProperties());
    }

    /**
     * Endpoint for getting all properties that fit specific conditions
     * @param request Contains the conditions
     * @return 200 OK
     */
    @Operation(summary = "Get all properties that have specific conditions")
    @ApiResponse(
            responseCode = "200",
            description = "No errors during the request",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(
                            schema = @Schema(implementation = GetPropertyResponse.class)
                    )
            )
    )
    @GetMapping("/specs")
    public ResponseEntity<List<GetPropertyResponse>> getAllSpecs(@ModelAttribute GetPropertyBySpecificationsRequest request) {
        return ResponseEntity.ok(propertyService.getPropertiesBySpecifications(request));
    }

    /**
     * Endpoint for deleting a property by its AMA number
     * @param amaNumber Property's AMA number
     * @return 204 NO CONTENT
     */
    @Operation(summary = "Delete a property")
    @ApiResponse(
            responseCode = "204",
            description = "Property deleted"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Property not found"
    )
    @DeleteMapping("/{amaNumber}")
    public ResponseEntity<Void> deleteProperty(@PathVariable String amaNumber) {
        propertyService.deleteProperty(amaNumber);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint for updating a property
     * @param amaNumber The property's AMA number
     * @param request Updated property data
     * @return 200 OK and the updated property
     */
    @Operation(summary = "Update a property")
    @ApiResponse(
            responseCode = "200",
            description = "Property updated",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UpdatePropertyResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Property not found or invalid data in the request"
    )
    @PatchMapping("/{amaNumber}")
    public ResponseEntity<UpdatePropertyResponse> updateProperty(@PathVariable String amaNumber,
                                                                 @Valid @RequestBody UpdatePropertyRequest request) {

        return ResponseEntity.ok(propertyService.updateProperty(amaNumber, request));
    }


    /**
     * Endpoint for adding an amenity to a property
     * @param propertyId Property's id
     * @param amenityId Amenity's id
     * @param request Amenity name and quantity
     * @return 200 OK and the property id with the added amenity's name and quantity
     */
    @Operation(summary = "Add amenity to property")
    @ApiResponse(
            responseCode = "200",
            description = "Amenity added",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AddPropertyAmenityResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Property not found, amenity not found or invalid data in the request"
    )
    @PostMapping("/{propertyId}/amenities/{amenityId}")
    public ResponseEntity<AddPropertyAmenityResponse> addAmenityToProperty(@PathVariable Integer propertyId,
                                                                           @PathVariable Integer amenityId,
                                                                           @Valid @RequestBody AddPropertyAmenityRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.addAmenityToProduct(propertyId, amenityId, request));
    }

    /**
     * Endpoint for updating the quantity of a property's amenity
     * @param id The property's amenity id
     * @param request Updated quantity
     * @return 200 OK and the updated quantity with the property id and amenity id
     */
    @Operation(summary = "Update the quantity of a property's amenity")
    @ApiResponse(
            responseCode = "200",
            description = "Quantity updated",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UpdatePropertyAmenityQuantityResponse.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Property amenity does not exist"
    )
    @PutMapping("/amenities/{id}")
    public ResponseEntity<UpdatePropertyAmenityQuantityResponse> updateAmenityQuantity(@PathVariable Integer id,
                                                                                       @RequestBody UpdatePropertyAmenityQuantityRequest request) {
        return ResponseEntity.ok(propertyService.updateAmenityQuantity(id, request));
    }

    /**
     * Endpoint for deleting a property's amenity
     * @param id Property's amenity id
     * @return 204 NO CONTENT
     */
    @Operation(summary = "Delete a property's amenity")
    @ApiResponse(
            responseCode = "204",
            description = "Property amenity deleted"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Property amenity not found"
    )
    @DeleteMapping("/amenities/{id}")
    public ResponseEntity<Void> deletePropertyAmenity(@PathVariable Integer id) {
        propertyService.deletePropertyAmenity(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * End point for adding an image to a property
     * @param id Property id
     * @param request Image name
     * @param baseUrl Website's base url
     * @param propertyImagesDirectory Directory where the images are stored
     * @return 201 CREATED and a link to the image
     */
    @Operation(summary = "Add an image to a property")
    @ApiResponse(
            responseCode = "201",
            description = "Image added to property",
            content = @Content(
                    mediaType = "application/json"
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Property or image not found"
    )
    @ApiResponse(
            responseCode = "409",
            description = "The property already has this image"
    )
    @PostMapping("/{id}/images")
    public ResponseEntity<String> addPropertyImage(@PathVariable Integer id,
                                                   @Valid @RequestBody AddPropertyImageRequest request,
                                                   @Value("${websiteUrl}") String baseUrl,
                                                   @Value("${files.propertyImagesPath}") String propertyImagesDirectory) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.addPropertyImage(id, request, baseUrl, propertyImagesDirectory));
    }
}
