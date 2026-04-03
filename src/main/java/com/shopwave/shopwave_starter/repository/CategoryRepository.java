// Student Number: ATE/5750/14
package com.shopwave.shopwave_starter.repository;

import com.shopwave.shopwave_starter.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
