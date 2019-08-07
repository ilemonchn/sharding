package com.example.mysphere.sharding;

import com.example.mysphere.domain.Order;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;
import java.util.Iterator;

public class OrderPreciseShardingTableAlgorithm implements PreciseShardingAlgorithm<Order> {
    @Override
    public String doSharding(Collection availableTargetNames, PreciseShardingValue shardingValue) {
        if(shardingValue.getLogicTableName().equals("t_order")){
            int size = availableTargetNames.size();
            int idx = Integer.valueOf(shardingValue.getValue().toString()) % size;
            String tableName = "";
            Iterator iterator = availableTargetNames.iterator();
            while (idx-- >= 0){
                tableName = iterator.next().toString();
            }
            return tableName;
        }
        return shardingValue.getLogicTableName();
    }
}
