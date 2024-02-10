package ecomarkets.domain.core.fair;

import com.google.errorprone.annotations.Immutable;
import ecomarkets.domain.core.farmer.FarmerId;
import ecomarkets.domain.core.product.Product;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
@Immutable
public class ProductStock extends PanacheEntity{
    @ManyToOne
    private Fair fair;
    private FarmerId farmerId;
    @ManyToOne
    private Product product;
    private Integer amount;

    private ProductStock(){}

    public static ProductStock of(Fair fair,
    FarmerId farmerId,
    Product product,
    Integer amount){
        ProductStock result = new ProductStock();
        result.farmerId = farmerId;
        result.product = product;
        result.amount = amount;
        result.fair = fair;
        return result;
    }



    public Product getProduct(){
        return this.product;
    }

    public Integer getAmount(){
        return this.amount;
    }
    
    public FarmerId getFarmerId(){
        return this.farmerId;
    }

    public Fair getFair(){
        return this.fair;
    }

}
