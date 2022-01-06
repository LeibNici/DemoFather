package com.shardingSphere.mapper;

import com.shardingSphere.domain.BusCarOperation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 车辆运行记录Mapper接口
 *
 * @author mxg
 * @date 2021-02-23
 */
@Mapper
public interface BusCarOperationMapper
{
    /**
     * 查询车辆运行记录
     *
     * @param id 车辆运行记录ID
     * @return 车辆运行记录
     */
    public BusCarOperation selectBusCarOperationById(Long id);

    /**
     * 查询车辆运行记录列表
     *
     * @param busCarOperation 车辆运行记录
     * @return 车辆运行记录集合
     */
    public List<BusCarOperation> selectBusCarOperationList(BusCarOperation busCarOperation);

    /**
     * 查询车辆运行记录列表
     *
     * @param busCarOperation 车辆运行记录
     * @return 车辆运行记录集合
     */
    public BusCarOperation selectOneBusCarOperation(BusCarOperation busCarOperation);

    /**
     * 新增车辆运行记录
     *
     * @param busCarOperation 车辆运行记录
     * @return 结果
     */
    public int insertBusCarOperation(BusCarOperation busCarOperation);

    /**
     * 修改车辆运行记录
     *
     * @param busCarOperation 车辆运行记录
     * @return 结果
     */
    public int updateBusCarOperation(BusCarOperation busCarOperation);

    /**
     * 删除车辆运行记录
     *
     * @param id 车辆运行记录ID
     * @return 结果
     */
    public int deleteBusCarOperationById(Long id);

    /**
     * 批量删除车辆运行记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusCarOperationByIds(Long[] ids);


}
