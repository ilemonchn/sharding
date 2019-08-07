package com.example.mysphere.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Order implements Comparable<Order>{
    private Long id;
    private Long userId;
    private String orderName;
    private Integer price;
    private String remark;

    @Override
    public int compareTo(Order other) {
        return userId.compareTo(other.getUserId());
    }

    public Order(Long userId, String orderName, Integer price, String remark){
        this.userId = userId;
        this.orderName = orderName;
        this.price = price;
        this.remark = remark;
    }
}
