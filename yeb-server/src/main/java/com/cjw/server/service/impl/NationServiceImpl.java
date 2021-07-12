package com.cjw.server.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjw.server.mapper.NationMapper;
import com.cjw.server.pojo.Nation;
import com.cjw.server.service.INationService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cjw
 * @since 2021-07-12
 */
@Service
public class NationServiceImpl extends ServiceImpl<NationMapper, Nation> implements INationService {

}
