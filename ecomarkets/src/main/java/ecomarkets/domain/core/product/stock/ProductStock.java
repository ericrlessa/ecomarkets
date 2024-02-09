package ecomarkets.domain.core.product.stock;

import com.google.errorprone.annotations.Immutable;
import ecomarkets.domain.core.farmer.FarmerId;
import ecomarkets.domain.core.product.Product;
import ecomarkets.domain.core.product.ProductId;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Immutable
//TODO this solution should be refined based on the domain
@NamedNativeQuery(
        name = "ProductStock.available",
        query = """
                    SELECT (stockSum - basketItemsSum) AS result
                      FROM (
                        SELECT SUM(item.amount) AS basketItemsSum
                          FROM Basket_items item
                          JOIN Basket b ON item.basket_id = b.id
                         WHERE item.product_id = :productId 
                           AND reserved_date is null ) AS basketItemsSum,
                        (
                        SELECT SUM(amount) AS stockSum
                          FROM ProductStock
                         WHERE product_id = :productId ) AS stockSum
                """,
        resultClass = Double.class
)
//@NamedQueries({
//        @NamedQuery("ProductStock.availableStock")
//})
public class ProductStock extends PanacheEntity{
    private FarmerId farmerId;
    @ManyToOne
    private Product product;
    private Integer amount;
    private ValidityPeriod validityPeriod;
    private ProductStock(){}

    public static ProductStock of(FarmerId farmerId,
    Product product,
    Integer amount,
    ValidityPeriod validityPeriod){
        ProductStock result = new ProductStock();
        result.farmerId = farmerId;
        result.product = product;
        result.amount = amount;
        result.validityPeriod = validityPeriod;
        return result;
    }

    //TODO this solution should be refined based on the domain
    public static Double getAvailableStock(ProductId productId){


        return (Double) getEntityManager().createNamedQuery("ProductStock.availableStock")
                .setParameter("productId", productId.id())
                .getSingleResult();
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

    public ValidityPeriod getValidityPeriod(){return this.validityPeriod;}

}
