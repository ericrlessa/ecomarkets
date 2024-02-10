package ecomarkets.domain.core.fair;

import ecomarkets.domain.core.product.ProductId;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedNativeQuery;

import java.time.LocalDateTime;

@Entity
@NamedNativeQuery(
        name = "Fair.availableStockByProduct",
        query = """
                    SELECT (stockSum - basketItemsSum) AS result
                      FROM (
                        SELECT SUM(item.amount) AS basketItemsSum
                          FROM Basket_items item
                          JOIN Basket b ON item.basket_id = b.id
                         WHERE item.product_id = :productId 
                           AND b.fair_id = :fairId ) AS basketItemsSum,
                        (
                        SELECT SUM(amount) AS stockSum
                          FROM ProductStock
                         WHERE product_id = :productId
                           and fair_id = :fairId ) AS stockSum
                """,
        resultClass = Double.class
)
public class Fair extends PanacheEntity {

    private ShoppingPeriod shoppingPeriod;

    private LocalDateTime creationDate;

    private Fair(){}

    public Fair(ShoppingPeriod shoppingPeriod) {
        this.shoppingPeriod = shoppingPeriod;
        this.creationDate = LocalDateTime.now();
    }

    public ShoppingPeriod getShoppingPeriod() {
        return shoppingPeriod;
    }

    public FairId fairId(){
        return FairId.of(id);
    }

    public static Double getAvailableStock(FairId fairId, ProductId productId){
        return (Double) getEntityManager().createNamedQuery("Fair.availableStockByProduct")
                .setParameter("fairId", fairId.id())
                .setParameter("productId", productId.id())
                .getSingleResult();
    }
}
