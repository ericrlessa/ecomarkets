package ecomarkets.domain.core.basket;

import ecomarkets.FixtureFactory;
import ecomarkets.domain.core.Tenant;
import ecomarkets.domain.core.partner.Partner;
import ecomarkets.domain.core.product.Product;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.ValidatableResponse;
import jakarta.transaction.Transactional;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class ReserveBasketTest {
    final static Partner PARTNER_JOHN = FixtureFactory.createPartner();

    static Product product;

    static Basket basket;

    final static Tenant TENANT = Tenant.of("Tenant1", "1");

    @BeforeAll
    @Transactional
    static void beforeAll(){
        Tenant.persist(TENANT);
        Partner.persist(PARTNER_JOHN);

        product = FixtureFactory.createProduct();

        product.persist();

        basket = Basket.of(PARTNER_JOHN.partnerId());

        basket.addItem(product, 5);

        basket.persist();
    }

    @Test
    public void testReserveBasket(){

        final ValidatableResponse vrCreate = given().contentType("application/json")
                .put("/api/basket/" + basket.id + "/reserve")
                .then()
                .body("reservedDate", is(notNullValue()))
                .statusCode(HttpStatus.SC_OK)
                .assertThat();

        Basket basketFromDB = Basket.findById(basket.id);
        assertThat(basketFromDB.getReservedDate(), notNullValue());
    }

}
