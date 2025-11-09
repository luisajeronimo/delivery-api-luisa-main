package com.deliverytech.delivery.repository.ProductFolder;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class IProductRepositoryTest {
    @Test
    void testFindByCategoryAndStatus() {

    }

    @Test
    void testFindByNameContainingIgnoreCase() {

    }

    @Test
    void testFindByNameContainingIgnoreCaseAndStatus() {

    }

    @Test
    void testFindByPriceBetweenAndStatus() {

    }

    @Test
    void testFindByPriceIsLessThanEqualAndStatus() {

    }

    @Test
    void testFindByRestaurantAndStatus() {

    }

    @Test
    void testFindByRestaurantIdAndStatus() {

    }

    @Test
    void testFindByStatusOrderByPriceAsc() {

    }

    @Test
    void testFindByStatusOrderByPriceDesc() {

    }
}
