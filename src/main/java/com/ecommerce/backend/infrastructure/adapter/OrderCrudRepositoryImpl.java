package com.ecommerce.backend.infrastructure.adapter;


import com.ecommerce.backend.domain.model.Order;
import com.ecommerce.backend.domain.model.OrderState;
import com.ecommerce.backend.domain.port.IOrderRepository;
import com.ecommerce.backend.infrastructure.entity.OrderEntity;
import com.ecommerce.backend.infrastructure.entity.UserEntity;
import com.ecommerce.backend.infrastructure.mapper.IOrderMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OrderCrudRepositoryImpl implements IOrderRepository {
    private final IOrderMapper iOrderMapper;
    private final IOrderCrudRepository iOrderCrudRepository;

    public OrderCrudRepositoryImpl(IOrderMapper iOrderMapper, IOrderCrudRepository iOrderCrudRepository) {
        this.iOrderMapper = iOrderMapper;
        this.iOrderCrudRepository = iOrderCrudRepository;
    }

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = iOrderMapper.toOrderEntity(order);
        orderEntity.getOrderProducts().forEach(
                orderProductEntity -> orderProductEntity.setOrderEntity(orderEntity)
        );
        return iOrderMapper.toOrder(iOrderCrudRepository.save(orderEntity));
    }

    @Override
    public Order findById(Integer id) {
        Optional<OrderEntity> orderEntityOptional = iOrderCrudRepository.findById(id);
        if (orderEntityOptional.isPresent()) {
            return iOrderMapper.toOrder(orderEntityOptional.get());
        } else {
            throw new RuntimeException("Order con id " + id + " no encontrada");
        }
    }

    @Override
    public Iterable<Order> findAll() {
        return iOrderMapper.toOrderList(iOrderCrudRepository.findAll());
    }

    @Override
    public Iterable<Order> findByUserId(Integer userId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        return iOrderMapper.toOrderList(iOrderCrudRepository.findByUserEntity(userEntity));
    }

    @Override
    public void updateStateById(Integer id, String state) {
        if(state.equals(OrderState.CANCELLED)){
            iOrderCrudRepository.updateStateById(id,OrderState.CANCELLED);
        } else {
            iOrderCrudRepository.updateStateById(id,OrderState.CONFIRMED);
        }
    }
}
