package ecomarkets.domain.core.fair;

import ecomarkets.FixtureFactory;
import ecomarkets.domain.core.basket.Basket;
import ecomarkets.domain.core.farmer.Farmer;
import ecomarkets.domain.core.partner.Partner;
import ecomarkets.domain.core.product.Product;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
public class FairTest {

    @Test
    @TestTransaction
    public void testAvailableStock(){

        Product prd = FixtureFactory.createProduct();
        prd.persist();

        Farmer farmer = FixtureFactory.createFarmer();
        farmer.persist();

        Fair fair = FixtureFactory.getFair();
        fair.persist();

        ProductStock stock = FixtureFactory.getProductStock(fair.fairId(), farmer.farmerId(), prd.productId(), 10);
        stock.persist();

        Partner partner = FixtureFactory.createPartner();
        partner.persist();

        Basket basket = Basket.of(fair.fairId(), partner.partnerId());
        basket.addItem(prd, 8);
        basket.persist();

        Double result = Fair.getAvailableStock(fair.fairId(), prd.productId());

        assertEquals(2, result);

    }

    @Test
    public void testValidityPeriod(){
        assertThrows(RuntimeException.class, () -> {
            ShoppingPeriod.of(LocalDateTime.now(), LocalDateTime.now().plusWeeks(-1));
        });
    }
}
