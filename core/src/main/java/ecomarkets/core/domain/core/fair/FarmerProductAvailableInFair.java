package ecomarkets.core.domain.core.fair;

import com.google.errorprone.annotations.Immutable;

import ecomarkets.core.domain.core.farmer.FarmerId;
import ecomarkets.core.domain.core.product.ProductId;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedNativeQuery;

@Entity
@NamedNativeQuery(
        name = "FarmerProductAvailableInFair.amountProductAvailable",
        query = """
                    SELECT (stockSum - basketItemsSum) AS result
                      FROM (
                        SELECT COALESCE(SUM(item.amount), 0) AS basketItemsSum
                          FROM Basket_items item
                          JOIN Basket b ON item.basket_id = b.id
                         WHERE item.product_id = :productId 
                           AND b.fair_id = :fairId ) AS basketItemsSum,
                        (
                        SELECT COALESCE(SUM(amount), 0) AS stockSum
                          FROM FarmerProductAvailableInFair
                         WHERE product_id = :productId
                           and fair_id = :fairId ) AS stockSum
                """,
        resultClass = Double.class
)
// TODO: Add a unique constraint for the combination of fairId, farmerId, and productId fields.
@Immutable
public class FarmerProductAvailableInFair extends PanacheEntity {
    private FairId fairId;
    private FarmerId farmerId;
    private ProductId productId;
    private Integer amount;

    private FarmerProductAvailableInFair(){}

    public static FarmerProductAvailableInFair of(FairId fairId,
                                                  FarmerId farmerId,
                                                  ProductId productId,
                                                  Integer amount){
        FarmerProductAvailableInFair result = new FarmerProductAvailableInFair();
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
