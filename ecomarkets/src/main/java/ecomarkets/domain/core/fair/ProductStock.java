package ecomarkets.domain.core.fair;

import com.google.errorprone.annotations.Immutable;
import ecomarkets.domain.core.farmer.FarmerId;
import ecomarkets.domain.core.product.ProductId;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
@Immutable
public class ProductStock extends PanacheEntity{
    private FairId fairId;
    private FarmerId farmerId;
    private ProductId productId;
    private Integer amount;

    private ProductStock(){}

    public static ProductStock of(FairId fairId,
    FarmerId farmerId,
    ProductId productId,
    Integer amount){
        ProductStock result = new ProductStock();
        result.farmerId = farmerId;
        result.productId = productId;
        result.amount = amount;
        result.fairId = fairId;
        return result;
    }

    public ProductId getProductId(){
        return this.productId;
    }

    public Integer getAmount(){
        return this.amount;
    }
    
    public FarmerId getFarmerId(){
        return this.farmerId;
    }

    public FairId getFairId(){
        return this.fairId;
    }

}
