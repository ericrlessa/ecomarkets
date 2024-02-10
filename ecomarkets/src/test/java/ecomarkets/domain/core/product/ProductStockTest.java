package ecomarkets.domain.core.product;

import ecomarkets.FixtureFactory;
import ecomarkets.domain.core.fair.Fair;
import ecomarkets.domain.core.fair.ProductStock;
import ecomarkets.domain.core.farmer.Farmer;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class ProductStockTest {

    @Test
    @TestTransaction
    void createProductStock() {
        Product prd = FixtureFactory.createProduct();
        prd.persist();

        Farmer farmer = FixtureFactory.createFarmer();
        farmer.persist();

        Fair fair = FixtureFactory.getFair();
        fair.persist();

        ProductStock stockBefore = FixtureFactory.getProductStock(fair.fairId(), farmer.farmerId(), prd.productId(), 100);
        stockBefore.persist();

        ProductStock stock = ProductStock.findById(stockBefore.id);

        assertEquals(stockBefore.id, stock.id);
        assertEquals(stockBefore.getFarmerId(), stock.getFarmerId());
        assertEquals(stockBefore.getProductId(), stock.getProductId());
        assertEquals(stockBefore.getAmount(), stock.getAmount());

    }

}
