package com.ecommerce.backend.infrastructure.adapter;

import com.ecommerce.backend.domain.model.OrderState;
import com.ecommerce.backend.infrastructure.entity.OrderEntity;
import com.ecommerce.backend.infrastructure.entity.UserEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IOrderCrudRepository extends CrudRepository<OrderEntity, Integer> {
    @Transactional
    @Modifying
    @Query("UPDATE OrderEntity o SET o.state = :state WHERE  o.id = :id")
    void updateStateById(Integer id, OrderState state);

    Iterable<OrderEntity> findByUserEntity(UserEntity userEntity);
}
