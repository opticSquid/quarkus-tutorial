package com.hangout.experiment.geo.food;

import com.hangout.experiment.geo.PlatformVendorCommon;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Hotel extends PlatformVendorCommon {
    private Boolean isVegFoodAvailable;

}
