package io.github.enelrith.bluebay.amenities.entities;

import io.github.enelrith.bluebay.properties.entities.PropertyAmenity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "amenities")
public class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @OneToMany(mappedBy = "amenity", fetch = FetchType.LAZY)
    private Set<PropertyAmenity> propertyAmenities;
}
