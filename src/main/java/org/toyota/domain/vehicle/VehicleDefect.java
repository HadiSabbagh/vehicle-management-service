package org.toyota.domain.vehicle;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "VehicleDefects")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "defect_id")

public class VehicleDefect
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "defect_id")
    private Long defect_id;

    @NotNull(message = "Cannot be null.")
    @Column(name = "types")
    private Set<DefectTypes> types = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_id")
    @JsonIgnore
    private Vehicle vehicle;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "vehicle_defects")
    @JsonManagedReference

    private List<DefectLocation> defect_locations = new ArrayList<>();


    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    public Set<DefectTypes> getDefectTypes()
    {
        return types;
    }


}
