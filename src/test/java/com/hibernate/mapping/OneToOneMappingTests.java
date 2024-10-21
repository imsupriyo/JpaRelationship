package com.hibernate.mapping;

import com.hibernate.mapping.entity.Address;
import com.hibernate.mapping.entity.Order;
import com.hibernate.mapping.repo.OrderRepo;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OneToOneMappingTests {
    @Autowired
    private OrderRepo orderRepo;

    @Test
    @org.junit.jupiter.api.Order(1)
    public void addRows() {
        Order order = Order
                .builder()
                .status("Confirmed")
                .build();

        Address address = Address
                .builder()
                .city("Electronics City")
                .state("Karnataka")
                .country("India")
                .order(order)
                .build();

        order.setAddress(address);

        orderRepo.save(order);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    public void updateRow() {
        Optional<Order> order = orderRepo.findById(1L);
        order.get().setStatus("Shipped");
        order.get().getAddress().setCity("Coorg");

        orderRepo.save(order.get());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    public void deleteRow() {
        orderRepo.deleteById(1L);
    }
}
