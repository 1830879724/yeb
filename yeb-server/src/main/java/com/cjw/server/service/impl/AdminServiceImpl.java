package com.cjw.server.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjw.server.AdminUtils;
import com.cjw.server.config.security.component.JwtTokenUtil;
import com.cjw.server.mapper.AdminMapper;
import com.cjw.server.mapper.AdminRoleMapper;
import com.cjw.server.mapper.RoleMapper;
import com.cjw.server.pojo.Admin;
import com.cjw.server.pojo.AdminRole;
import com.cjw.server.pojo.RespBean;
import com.cjw.server.pojo.Role;
import com.cjw.server.service.IAdminService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;


    /**
     * 登录之后返回token
     * @param username
     * @param password
     * @param request
     * @return
     */
    @Override
    public RespBean login(String username, String password,String code, HttpServletRequest request) {
        String captcha= (String) request.getSession().getAttribute("captcha");
        if (StringUtils.isEmpty(code)||!captcha.equalsIgnoreCase(code)){
            return RespBean.error("验证码输入错误，请重新输入！");
        }
        //登录
       UserDetails userDetails = userDetailsService.loadUserByUsername(username);
       //判断密码是否正确
       if (null ==userDetails||!passwordEncoder.matches(password,userDetails.getPassword())){
           return RespBean.error("用户名密码不正确!");
       }
       //判断用户名是否被禁用
       if (!userDetails.isEnabled()){
           return RespBean.error("账号被禁用,请联系管理员!");
       }
       //更新security登录对象
        UsernamePasswordAuthenticationToken authenticationToken =new
                UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
       //根据jwtTokenUtil拿到令牌  生成token
       String token = jwtTokenUtil.generateToken(userDetails);
        Map<String ,String> tokenMap=new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        return RespBean.success("登录成功!",tokenMap);
    }

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    @Override
    public Admin getAdminByUserName(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username)
                .eq("enabled",true));
    }

    /**
     * 根据用户id查询角色
     * @param adminId
     * @return
     */
    @Override
    public List<Role> getRoles(Integer adminId) {
        return  roleMapper.getRoles(adminId);
    }

    /**
     * 获取所有操作员
     * @param keywords
     * @return
     */
    @Override
    public List<Admin> getAllAdmin(String keywords) {
        return adminMapper.getAllAdmin(
                AdminUtils.getCurrentAdmin().getId(),keywords);
    }

    /**
     * 更新操作员角色
     * @param adminId
     * @param rids
     * @return
     */
    @Override
    @Transactional
    public RespBean updateAdminRole(Integer adminId, Integer[] rids) {
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId",adminId));
        Integer result=adminRoleMapper.addAdminRole(adminId,rids);
        if (rids.length==result){
            return  RespBean.success("更新成功!");
        }
        return  RespBean.error("更新失败！");
    }


}
