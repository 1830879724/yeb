package com.cjw.server.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjw.server.mapper.AdminMapper;
import com.cjw.server.pojo.Admin;
import com.cjw.server.service.IAdminService;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

}
