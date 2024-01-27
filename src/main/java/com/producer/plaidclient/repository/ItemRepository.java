package com.producer.plaidclient.repository;

import com.producer.plaidclient.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByItemId(String itemId);
}
