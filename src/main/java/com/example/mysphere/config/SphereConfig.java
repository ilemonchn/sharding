package com.example.mysphere.config;

import com.example.mysphere.sharding.OrderPreciseShardingTableAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.orchestration.spring.boot.util.DataSourceUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import javax.swing.*;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class SphereConfig {

    @Bean("shardingDataSource")
    DataSource getShardingDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
//        shardingRuleConfig.getTableRuleConfigs().add(getOrderItemTableRuleConfiguration());
//        shardingRuleConfig.getBindingTableGroups().add("t_order, t_order_item");
        shardingRuleConfig.getBindingTableGroups().add("t_order");
//        shardingRuleConfig.getBroadcastTables().add("t_config");
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds${user_id % 2}"));
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("price",
                new OrderPreciseShardingTableAlgorithm()));
        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, null);
    }

    private static KeyGeneratorConfiguration getKeyGeneratorConfiguration() {
        Properties p = new Properties();
        p.setProperty("worker.id", "17");
        p.setProperty("max.tolerate.time.difference.milliseconds", "3");
        KeyGeneratorConfiguration result = new KeyGeneratorConfiguration("SNOWFLAKE", "id", p);
        return result;
    }

    TableRuleConfiguration getOrderTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration("t_order", "ds${0..1}.t_order${0..1}");//
        result.setKeyGeneratorConfig(getKeyGeneratorConfiguration());

        return result;
    }

//    TableRuleConfiguration getOrderItemTableRuleConfiguration() {
//        TableRuleConfiguration result = new TableRuleConfiguration("t_order_item", "ds${0..1}.t_order_item${0..1}");
//        return result;
//    }

    Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>();

        Map<String, Object> ds0Param = new HashMap<>();
        Map<String, Object> ds1Param = new HashMap<>();
        ds0Param.put("url","jdbc:mysql://127.0.0.1:3306/order01?useUnicode=true&characterEncoding=utf8");
        ds0Param.put("username","root");
        ds0Param.put("password","654321");
        ds1Param.put("url","jdbc:mysql://127.0.0.1:3306/order02?useUnicode=true&characterEncoding=utf8");
        ds1Param.put("username","root");
        ds1Param.put("password","654321");

        try {
            result.put("ds0", DataSourceUtil
                    .getDataSource("com.alibaba.druid.pool.DruidDataSource", ds0Param));
            result.put("ds1", DataSourceUtil
                    .getDataSource("com.alibaba.druid.pool.DruidDataSource", ds1Param));
        }catch (ReflectiveOperationException ex){
            ex.printStackTrace();
        }
        return result;
    }
}
