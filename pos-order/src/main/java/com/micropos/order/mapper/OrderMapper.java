package com.micropos.order.mapper;

import com.micropos.common.dto.OrderDto;
import com.micropos.common.model.Order;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Collection<OrderDto> toOrdersDto(Collection<Order> orders);

    Collection<Order> toOrders(Collection<OrderDto> orders);

    Order toOrder(OrderDto orderDto);

    OrderDto toOrderDto(Order order);
}
