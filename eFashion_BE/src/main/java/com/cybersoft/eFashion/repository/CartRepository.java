package com.cybersoft.eFashion.repository;

import com.cybersoft.eFashion.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface CartRepository extends JpaRepository<OrderItems,Integer> {
    @Query("select o from order_item o")
    List<OrderItems> getAllOrderItems();

    List<OrderItems> findProductById(int id);

    @Modifying
    @Transactional
    @Query("update order_item set quantity = quantity+1 where product_id =?1")
    Integer plusQuantity(int id);

    @Modifying
    @Transactional
    @Query("update order_item set quantity = quantity-1 where product_id =?1")
    Integer subtractQuantity(int id);
}