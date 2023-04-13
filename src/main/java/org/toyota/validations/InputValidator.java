package org.toyota.validations;

import org.toyota.dto.VehicleDTO;

import java.util.List;

public interface InputValidator
{
    List<String> validateVehicleInput(VehicleDTO vehicleDTO);

}
