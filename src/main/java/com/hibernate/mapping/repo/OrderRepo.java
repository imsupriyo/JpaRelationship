package com.hibernate.mapping.repo;

import com.hibernate.mapping.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
