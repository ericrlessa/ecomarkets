package ecomarkets.rs;

import java.util.List;

import ecomarkets.core.domain.core.partner.Partner;
import io.quarkus.panache.common.Sort;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/partner")
public class PartnerResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Partner> getPartners() {
        return Partner.listAll(Sort.ascending("name"));
    }
    
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Partner getPartner(@PathParam ("id") Long id) {
        return Partner.findById(id);
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Partner partner) {
        partner.persist();
        
        return Response
        .status(Response.Status.CREATED)
        .entity(partner)
        .build();
    }

}
