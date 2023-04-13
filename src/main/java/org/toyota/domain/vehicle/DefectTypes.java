package org.toyota.domain.vehicle;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public enum DefectTypes
{
    @JsonProperty("DAMAGE") DEFECT_TYPES_DAMAGE,
    @JsonProperty("WHEELS") DEFECT_TYPES_WHEELS,
    @JsonProperty("MOTOR") DEFECT_TYPES_MOTOR;

    private Set<DefectTypes> name;

    public Set<DefectTypes> getName()
    {
        return name;
    }

    public void setName(Set<DefectTypes> name)
    {
        this.name = name;
    }
}
