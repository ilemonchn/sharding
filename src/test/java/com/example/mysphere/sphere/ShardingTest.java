package com.example.mysphere.sphere;

import com.example.mysphere.MysphereApplication;
import com.example.mysphere.domain.Order;
import com.example.mysphere.mapper.OrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MysphereApplication.class)
public class ShardingTest {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void shardingInsetTest(){
        System.out.println("start");
        Order o = new Order(1L, "lemonOrder", 1, "8070");
//        o.setId(100L);
        orderMapper.insert(o);
        Order o2 = new Order(2L, "lemonOrder", 1, "8071");
        orderMapper.insert(o2);
        System.out.println("ID:"+o.getId());

//        orderMapper.insert(new Order(1L, "lemonOrder", 2, "second no sql"));
//        orderMapper.insert(new Order(2L, "lemonOrder", 1,"third new sql"));
//        orderMapper.insert(new Order(2L, "lemonOrder", 2, "fourth pg sql"));
    }
}
