package com.hangout.experiment;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/users")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class UserResource {
    private static final Logger log = LoggerFactory.getLogger(UserResource.class);

    @GET
    public Uni<List<Users>> get() {
        log.info("Get all users...");
        return Users.listAll(Sort.by("id"));
    }

    @GET
    @Path("/{id}")
    public Uni<Users> getById(Long id) {
        log.info("Get by id: {}", id);
        return Users.findById(id);
    }

    @POST
    public Uni<Response> create(Users user) {
        log.info("Create: {}", user);
        return Panache.<Users>withTransaction(user::persist)
                .onItem().transform(insertedUser -> Response.created(URI.create("/users/" + insertedUser.id)).build());
    }

}
