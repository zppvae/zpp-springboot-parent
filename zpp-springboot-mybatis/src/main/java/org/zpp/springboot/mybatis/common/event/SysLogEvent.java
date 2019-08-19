package org.zpp.springboot.mybatis.common.event;

import org.springframework.context.ApplicationEvent;
import org.zpp.springboot.mybatis.model.SysLog;

/**
 * 日志事件
 */
public class SysLogEvent extends ApplicationEvent {

	public SysLogEvent(SysLog source) {
		super(source);
	}
}