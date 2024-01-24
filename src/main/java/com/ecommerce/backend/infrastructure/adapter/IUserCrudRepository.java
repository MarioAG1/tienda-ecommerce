package com.ecommerce.backend.infrastructure.adapter;

import com.ecommerce.backend.infrastructure.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface IUserCrudRepository extends CrudRepository<UserEntity,Integer> {
}
