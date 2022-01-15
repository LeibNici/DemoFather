package com.springtest.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 车辆出入井记录对象 bus_car_enter_mine
 *
 * @author mxg
 * @date 2021-02-22
 */
@Data
public class BusCarEnterMine {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 车牌号
     */
    private String carNum;

    /**
     * 驾驶员
     */
    private String driverName;

    /**
     * 入井记录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recordDate;

    /**
     * 车辆出入井类型（1入井，2出井）
     */
    private String type;

    /**
     * 车辆出入井类型（1入井，2出井）
     */
    @TableField(exist = false)
    private String newShuxing;

    /**
     * 出井记录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date outDate;

}
