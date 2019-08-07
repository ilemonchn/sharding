package com.example.mysphere.mapper;

import com.example.mysphere.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface OrderMapper {

    int insert(Order order);


}
