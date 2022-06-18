package com.cm.PostGre.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author chenming
 * @description
 * @create: 2022-04-14
 */
@Data
@TableName("t_tunnel")
public class Tunnel{
    private String name;
    private String topoStartId;
    private String topoEndId;
    private String guid;
    private Long sort;
}
