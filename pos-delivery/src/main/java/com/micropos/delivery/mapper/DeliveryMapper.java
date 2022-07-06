package com.micropos.delivery.mapper;

import com.micropos.common.dto.DeliveryDto;
import com.micropos.common.model.Delivery;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {

    Collection<DeliveryDto> toDeliveriesDto(Collection<Delivery> deliveries);

    Delivery toDelivery(DeliveryDto deliveryDto);

    DeliveryDto toDeliveryDto(Delivery delivery);
}
