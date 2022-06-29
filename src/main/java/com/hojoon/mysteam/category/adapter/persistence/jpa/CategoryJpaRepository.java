package com.hojoon.mysteam.category.adapter.persistence.jpa;

import com.hojoon.mysteam.category.adapter.persistence.jpa.model.CategoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<CategoryJpaEntity, Long> {

}
