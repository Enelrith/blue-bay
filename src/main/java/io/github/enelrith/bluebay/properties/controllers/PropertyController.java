package io.github.enelrith.bluebay.properties.controllers;

import io.github.enelrith.bluebay.properties.dto.*;
import io.github.enelrith.bluebay.properties.services.PropertyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/properties")
public class PropertyController {
    private final PropertyService propertyService;

    @PostMapping
    public ResponseEntity<AddPropertyResponse> addProperty(@Valid @RequestBody AddPropertyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.addProperty(request));
    }

    @GetMapping("/{amaNumber}")
    public ResponseEntity<GetPropertyResponse> getProperty(@Valid @PathVariable(name = "amaNumber") GetPropertyRequest request) {
        return ResponseEntity.ok(propertyService.getProperty(request));
    }

    @GetMapping
    public ResponseEntity<List<GetPropertyResponse>> getAllProperties() {
        return ResponseEntity.ok(propertyService.getAllProperties());
    }

    @DeleteMapping("/{amaNumber}")
    public ResponseEntity<Void> deleteProperty(@PathVariable String amaNumber) {
        propertyService.deleteProperty(amaNumber);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{amaNumber}")
    public ResponseEntity<UpdatePropertyResponse> updateProperty(@PathVariable String amaNumber,
                                                                 @Valid @RequestBody UpdatePropertyRequest request) {

        return ResponseEntity.ok(propertyService.updateProperty(amaNumber, request));
    }
}
