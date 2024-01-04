package ecomarkets.rs.product;

import ecomarkets.domain.core.product.Product;
import ecomarkets.domain.core.product.image.ImageRepository;
import ecomarkets.domain.core.product.image.ProductImage;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.apache.http.HttpStatus;
import org.jboss.resteasy.reactive.ResponseStatus;

@Path("/product/{id}/image")
public class ProductImageResource {

    @Inject
    private ImageRepository imageRepository;

    @PUT
    @ResponseStatus(HttpStatus.SC_OK)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional
    public void saveImage(@PathParam("id") Long productId,
                          ImageFormData file){
        Product product = Product.findById(productId);
        ProductImage pm = product.newImage(imageRepository.getBucketName(),
                                    file.fileName,
                                    file.mimeType);
        imageRepository.save(file.file.toPath(), pm);
    }

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public byte[] getImage(@PathParam("id") Long productId){
        Product product = Product.findById(productId);
        return imageRepository.find(product.productImage());
    }

    @Path("presignedGetUrl")
    @GET
    public String createPresignedGetUrl(@PathParam("id") Long productId){
        Product product = Product.findById(productId);
        return imageRepository.createPresignedGetUrl(product.productImage());
    }
}
