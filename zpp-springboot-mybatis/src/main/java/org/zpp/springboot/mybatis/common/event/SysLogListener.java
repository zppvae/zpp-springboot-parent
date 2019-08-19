package org.zpp.springboot.mybatis.common.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.zpp.springboot.mybatis.model.SysLog;
import org.zpp.springboot.mybatis.service.SysLogService;

@Slf4j
@Component
@AllArgsConstructor
public class SysLogListener {

	private final SysLogService sysLogService;

	@Async
	@Order
	@EventListener(SysLogEvent.class)
	public void saveLog(SysLogEvent event) {
		log.info("接收到异步日志处理事件：{}",event.getSource());
		SysLog sysLog = (SysLog) event.getSource();
		sysLogService.insert(sysLog);
	}
}