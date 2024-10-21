package com.hibernate.mapping;

import com.hibernate.mapping.entity.Address;
import com.hibernate.mapping.entity.Order;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class OneToOneMappingTestsEM {

    @Autowired
    private EntityManager entityManager;

    @Test
    @DirtiesContext
    @Transactional
    public void addOrder() {
        Order order = Order.builder().status("Shipped").build();
        entityManager.persist(order);

        assertNotNull(entityManager.find(Order.class, 1L));
    }

    @Test
    @DirtiesContext
    @Transactional
    public void addAddress() {
        Address address = Address.builder()
                .city("Bangalore")
                .state("Karanataka")
                .country("India")
                .build();
        entityManager.persist(address);

        assertNotNull(entityManager.find(Address.class, 1L));
    }

    @Test
    @DirtiesContext
    @Transactional
    public void linkAddressToOrder() {
        Address address = Address.builder()
                .city("Bangalore")
                .state("Karanataka")
                .country("India")
                .build();

        Order order = Order.builder()
                .status("Shipped")
                .address(address)
                .build();

        entityManager.persist(order);

        Order order1 = entityManager.find(Order.class, 1L);
        assertNotNull(order1);
        assertNotNull(order1.getAddress());

        entityManager.refresh(address);

        Address address1 = entityManager.find(Address.class, 1L);
        assertNotNull(address1.getOrder());
    }

    @Test
    @DirtiesContext
    @Transactional
    public void linkOrderToAddress() throws InterruptedException {
        Order order = Order.builder()
                .status("Shipped")
                .build();

        Address address = Address.builder()
                .city("Bangalore")
                .state("Karanataka")
                .country("India")
                .order(order)
                .build();

        entityManager.persist(address);
        order.setAddress(address);

        Address address1 = entityManager.find(Address.class, 1L);
        assertNotNull(address1);
        assertNotNull(address1.getOrder());

        Order order1 = entityManager.find(Order.class, 1L);
        assertNotNull(order1.getAddress());
    }

    @Test
    @DirtiesContext
    @Transactional
    public void deleteUserCascadeTest() {
        Address address = Address.builder()
                .city("Bangalore")
                .state("Karanataka")
                .country("India")
                .build();

        Order order = Order.builder()
                .status("Shipped")
                .address(address)
                .build();

        entityManager.persist(order);

        Order order1 = entityManager.find(Order.class, 1L);
        assertNotNull(order1);
        assertNotNull(order1.getAddress());

        entityManager.remove(order1);
        assertNull(entityManager.find(Order.class, 1L));
        assertNull(entityManager.find(Address.class, 1L));
    }

    @Test
    @DirtiesContext
    @Transactional
    public void deleteAddressCascadeTest() {
        Address address = Address.builder()
                .city("Bangalore")
                .state("Karanataka")
                .country("India")
                .build();

        Order order = Order.builder()
                .status("Shipped")
                .build();

        order.setAddress(address);
        address.setOrder(order);

        entityManager.persist(order);

        Address address1 = entityManager.find(Address.class, 1L);
        assertNotNull(address1);
        assertNotNull(address1.getOrder());

        entityManager.remove(address1);
        assertNull(entityManager.find(Address.class, 1L));
        assertNull(entityManager.find(Order.class, 1L));
    }

    @Test
    @DirtiesContext
    @Transactional
    public void updateOrderAndAddress() throws InterruptedException {
        Address address = Address.builder()
                .city("Bangalore")
                .state("Karanataka")
                .country("India")
                .build();

        Order order = Order.builder()
                .status("Shipped")
                .build();

        order.setAddress(address);

        entityManager.persist(order);

        Order tempOrder = entityManager.find(Order.class, 1L);
        tempOrder.setStatus("Delivered");
        tempOrder.getAddress().setCity("White Field");

        entityManager.merge(tempOrder);
        System.out.println(entityManager.find(Order.class, 1L));
    }

}
