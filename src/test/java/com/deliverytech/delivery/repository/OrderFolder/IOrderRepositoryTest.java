package com.deliverytech.delivery.repository.OrderFolder;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class IOrderRepositoryTest {
    @Test
    void testFindByCustomerIdOrderByOrderDateDesc() {

    }

    @Test
    void testFindByCustomerOrderByOrderDateDesc() {

    }

    @Test
    void testFindByOrderDateBetweenOrderByOrderDateDesc() {

    }

    @Test
    void testFindByOrderStatusAndOrderDateBetweenOrderByOrderDateDesc() {

    }

    @Test
    void testFindByOrderStatusOrderByOrderDateDesc() {

    }

    @Test
    void testFindByRestaurantIdAndOrderStatusOrderByOrderDateDesc() {

    }
}
