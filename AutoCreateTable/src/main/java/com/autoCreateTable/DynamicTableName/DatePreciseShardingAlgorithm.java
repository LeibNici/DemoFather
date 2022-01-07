package com.autoCreateTable.DynamicTableName;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Slf4j
public class DatePreciseShardingAlgorithm implements PreciseShardingAlgorithm<Date> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Date> preciseShardingValue) {
        Date date = preciseShardingValue.getValue();
        String DateString = new SimpleDateFormat("yyyyMM").format(date);
        for (String tableName : collection) {
            if (tableName.endsWith(DateString)) {
                return tableName;
            }
        }
        return null;
    }
}
