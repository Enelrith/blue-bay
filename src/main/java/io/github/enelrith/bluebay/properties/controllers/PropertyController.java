package io.github.enelrith.bluebay.properties.controllers;

import io.github.enelrith.bluebay.properties.dto.AddPropertyRequest;
import io.github.enelrith.bluebay.properties.dto.AddPropertyResponse;
import io.github.enelrith.bluebay.properties.services.PropertyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/properties")
public class PropertyController {
    private final PropertyService propertyService;

    @PostMapping("/add")
    public ResponseEntity<AddPropertyResponse> addProperty(@Valid @RequestBody AddPropertyRequest request) {
        return propertyService.addProperty(request);
    }

}
