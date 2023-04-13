package org.toyota.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.toyota.domain.vehicle.VehicleDefect;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VehicleDTO
{
    private Long vehicle_id;
    private String make;
    private String model;
    Set<VehicleDefect> vehicle_defects;


}
