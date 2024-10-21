package com.hibernate.mapping;

import com.hibernate.mapping.entity.Address;
import com.hibernate.mapping.entity.Order;
import com.hibernate.mapping.entity.OrderItem;
import com.hibernate.mapping.repo.OrderRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.Set;

@SpringBootTest
public class OneToManyMappingTests {

    @Autowired
    private OrderRepo orderRepo;

    @Test
    @org.junit.jupiter.api.Order(1)
    public void addOrder() {
        Order order = new Order();
        order.setStatus("In Transit");

        Address address = Address.builder()
                .city("Sarjapur")
                .state("Karntaka")
                .country("India")
                .order(order)
                .build();

        order.setAddress(address);

        OrderItem orderItem1 = OrderItem.builder()
                .price(100)
                .quantity(10)
                .order(order)
                .build();

        OrderItem orderItem2 = OrderItem.builder()
                .price(1000)
                .quantity(5)
                .order(order)
                .build();

        order.getOrderItemSet().addAll(Set.of(orderItem1, orderItem2));

        orderRepo.save(order);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    public void updateOrder() {
        Optional<Order> order = orderRepo.findById(1L);
        order.get().getOrderItemSet().forEach(item -> item.setPrice(500));
        orderRepo.save(order.get());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    public void deleteOrderItem() {
        orderRepo.deleteById(1L);
    }

}
