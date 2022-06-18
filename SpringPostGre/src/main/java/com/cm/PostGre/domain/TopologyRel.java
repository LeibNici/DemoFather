package com.cm.PostGre.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author chenming
 * @description
 * @create: 2022-06-08
 */
@Data
@TableName("t_tunnel_topology_rel")
public class TopologyRel {

    private String startCode;
    private String endCode;
    private String guid;

}
