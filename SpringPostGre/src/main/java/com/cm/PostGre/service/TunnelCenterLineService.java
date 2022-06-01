package com.cm.PostGre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cm.PostGre.domain.TunnelCenterLine;

/**
 * @author chenming
 * @description
 * @create: 2022-04-14
 */
public interface TunnelCenterLineService extends IService<TunnelCenterLine> {

    public String createGeom(TunnelCenterLine last,TunnelCenterLine current);

}
