package com.example.ordering.mapper;

import com.example.ordering.dto.OrderResponseDTO;
import com.example.ordering.model.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponseDTO toDto(OrderEntity e);
}
