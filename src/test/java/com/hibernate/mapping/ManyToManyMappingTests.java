package com.hibernate.mapping;

import com.hibernate.mapping.entity.Address;
import com.hibernate.mapping.entity.Order;
import com.hibernate.mapping.entity.OrderItem;
import com.hibernate.mapping.entity.Promotion;
import com.hibernate.mapping.repo.OrderRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Set;

@SpringBootTest
public class ManyToManyMappingTests {

    @Autowired
    private OrderRepo orderRepo;

    @Test
    public void addRows() {
        Order order = new Order();
        order.setStatus("In Transit");

        Address address = Address.builder()
                .city("Sarjapur")
                .state("Karntaka")
                .country("India")
                .build();
        order.setAddress(address);
        address.setOrder(order);

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

        Promotion promotion1 = Promotion.builder()
                .name("Christmas Offer")
                .couponCode("MERRYCHRISTMAS")
                .discountValue(1000)
                .startDate(LocalDate.now())
                .endDate(LocalDate.of(2024, 12, 25))
                .build();

        order.getPromotions().add(promotion1);

        Promotion promotion2 = Promotion.builder()
                .name("Big Billion Day")
                .couponCode("BBDSALE")
                .discountValue(5000)
                .startDate(LocalDate.of(2024, 9, 25))
                .endDate(LocalDate.of(2024, 9, 30))
                .build();

        order.getPromotions().add(promotion2);

        orderRepo.save(order);
    }
}
