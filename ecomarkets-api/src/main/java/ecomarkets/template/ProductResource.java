package ecomarkets.template;

import ecomarkets.core.domain.core.product.Product;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/abc")
public class ProductResource {

    @Inject
    Template products;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        return products.data("vai", Product.findAll());
    }


}
