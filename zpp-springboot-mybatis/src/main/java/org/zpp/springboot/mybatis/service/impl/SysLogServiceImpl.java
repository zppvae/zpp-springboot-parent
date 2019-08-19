package org.zpp.springboot.mybatis.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.zpp.springboot.mybatis.mapper.SysLogMapper;
import org.zpp.springboot.mybatis.model.SysLog;
import org.zpp.springboot.mybatis.service.SysLogService;

/**
 * @author zpp
 * @date 2019/5/6 16:52
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper,SysLog> implements SysLogService {
}
