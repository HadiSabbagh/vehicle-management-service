package org.toyota.domain.vehicle;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "Vehicles", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "model"
        })
})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "vehicle_id")
public class Vehicle
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "vehicle_id")
    private Long vehicle_id;

    @NotBlank(message = "Cannot be empty.")
    @NotNull(message = "Cannot be null.")
    @Column(name = "make")
    private String make;

    @NotBlank(message = "Cannot be empty.")
    @NotNull(message = "Cannot be null.")
    @Column(name = "model")
    @Size(max = 7, min = 7)
    private String model;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "vehicle")
    @Nullable
    private Set<VehicleDefect> vehicle_defects = new HashSet<>();


}
