package com.example.storeapi_item.Repo;

import com.example.storeapi_item.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
