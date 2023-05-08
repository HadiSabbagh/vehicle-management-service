package org.toyota.resources;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.toyota.dto.VehicleDTO;
import org.toyota.mapper.ResponseMapper;
import org.toyota.services.VehicleManagement;
import org.toyota.services.VehicleManagementImpl;


/**
 * This controller is responsible for allowing access to VehicleManagement Service.
 */
@Validated
@RestController
@RequestMapping("/vehicle-management")
@PreAuthorize("hasRole('ROLE_OPERATOR')")
public class VehicleManagementController
{
    @Autowired
    VehicleManagementImpl vehicleManagement;

    @PostMapping("/add-vehicle")
    public ResponseEntity<?> addVehicle(@Valid @RequestBody VehicleDTO vehicleDTO)
    {
        return new ResponseMapper().MapResponse(vehicleManagement.save(vehicleDTO));
    }

    @PutMapping("/update-vehicle/{id}")
    public ResponseEntity<?> updateVehicle(@PathVariable("id") Long vehicleId, @Valid @RequestBody VehicleDTO vehicleDTO)
    {
        vehicleDTO.setVehicle_id(vehicleId);
        return new ResponseMapper().MapResponse(vehicleManagement.update(vehicleDTO));
    }

    @GetMapping("/list-vehicles")
    public ResponseEntity<?> listVehicles()
    {
        return new ResponseMapper().ResponseList(vehicleManagement.listVehicles());
    }


}
