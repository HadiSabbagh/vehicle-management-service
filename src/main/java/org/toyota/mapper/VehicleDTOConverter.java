package org.toyota.mapper;

import org.springframework.stereotype.Component;
import org.toyota.domain.vehicle.Vehicle;
import org.toyota.dto.VehicleDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is responsible for converting back and forth between the Vehicle and VehicleDTO.
 */
@Component
public class VehicleDTOConverter
{
    public VehicleDTO vehicle_to_DTO(Vehicle vehicle)
    {

        return new VehicleDTO(
                vehicle.getVehicle_id(),
                vehicle.getMake(),
                vehicle.getModel(),
                vehicle.getVehicle_defects()
        );
    }

    public Vehicle DTO_to_vehicle(VehicleDTO vehicleDTO)
    {

        return new Vehicle(
                vehicleDTO.getVehicle_id(),
                vehicleDTO.getMake(),
                vehicleDTO.getModel(),
                vehicleDTO.getVehicle_defects()
        );
    }


    public List<VehicleDTO> vehicles_To_DTOs(List<Vehicle> vehicles)
    {
        return vehicles.stream().map(this::vehicle_to_DTO).collect(Collectors.toList());
    }

    public List<Vehicle> DTOs_To_vehicles(List<VehicleDTO> vehicleDTOS)
    {
        return vehicleDTOS.stream().map(this::DTO_to_vehicle).collect(Collectors.toList());
    }
}
