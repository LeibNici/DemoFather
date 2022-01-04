package com.shardingSphere.DynamicTableName;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Range;
import com.shardingSphere.mapper.UserShardingMapper;
import com.shardingSphere.utils.IsTableExistUtil;
import com.shardingSphere.utils.ShardingUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class DateRangeShardingAlgorithm implements RangeShardingAlgorithm<Date> {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Date> rangeShardingValue) {

        List<String> tableList = new ArrayList<>();

        Range<Date> valueRange = rangeShardingValue.getValueRange();
        Date lower = valueRange.lowerEndpoint();
        Date upper = valueRange.upperEndpoint();

        TreeSet<String> range = ShardingUtils.getRange(dateFormat.format(lower), dateFormat.format(upper));

        for (String tableName : collection) {
            for (String rangeOne : range) {
                if (tableName.endsWith(rangeOne) && IsTableExistUtil.checkTable(tableName)) {
                    tableList.add(tableName);
                }
            }
        }
        return tableList;
    }
}
