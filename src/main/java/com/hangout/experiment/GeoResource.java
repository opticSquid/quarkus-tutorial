package com.hangout.experiment;

import static org.geolatte.geom.builder.DSL.g;
import static org.geolatte.geom.crs.CoordinateReferenceSystems.WGS84;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hangout.experiment.geo.Address;
import com.hangout.experiment.geo.Category;
import com.hangout.experiment.geo.food.Hotel;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/geo")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class GeoResource {
    private static final Logger log = LoggerFactory.getLogger(GeoResource.class);

    @GET
    public Uni<Response> create() {
        List<Hotel> entities = IntStream.range(0, 3)
                .mapToObj(i -> {
                    Hotel entity = createHotel(i);
                    log.debug("Hotel: {}", entity.getAddress().getGeolocation());
                    // Set entity properties if needed
                    return entity;
                })
                .collect(Collectors.toList());
        return Uni.createFrom().item(() -> {
            Hotel.persist(entities);
            log.info("Batch insert done");
            return Response.ok("Batch insert successful").build();
        });
    }

    private Hotel createHotel(int i) {
        Hotel hotel = new Hotel();
        hotel.setIsVegFoodAvailable(true);
        hotel.setPlacename("hotel" + i);
        hotel.setCategory(Category.FOOD);
        hotel.setSubcategory("hotel");
        hotel.setOwnerid(UUID.randomUUID());
        Address address = new Address();
        address.setGeolocation(generateRandomGeoLocation());
        address.setBuildingnameornumber("building" + i);
        address.setStreetname("street" + i);
        address.setTown("town" + i);
        address.setState("state" + i);
        address.setCountry("country" + i);
        hotel.setAddress(address);
        return hotel;
    }

    public Point<G2D> generateRandomGeoLocation() {
        // Generate random values for lon (0 to 180) and lat (-90 to 90)
        double lon = Math.random() * 180;
        double lat = Math.random() * 180 - 90;

        // Create and return a GeoLocation object using the constructor
        return new Point<G2D>(g(lon, lat), WGS84);
    }
}
