package org.toyota.validations;


import org.toyota.domain.vehicle.Vehicle;

import java.util.List;

public interface DatabaseValidator
{

    List<String> validateVehicle(Vehicle vehicle);


}
