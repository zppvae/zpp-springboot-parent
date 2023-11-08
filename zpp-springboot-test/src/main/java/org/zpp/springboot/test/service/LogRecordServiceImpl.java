package org.zpp.springboot.test.service;

import com.mzt.logapi.beans.LogRecord;
import com.mzt.logapi.service.ILogRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LogRecordServiceImpl implements ILogRecordService {

    @Override
    public void record(LogRecord logRecord) {
        log.info("自定义日志消息: {}", logRecord.getAction());
    }

    @Override
    public List<LogRecord> queryLog(String bizNo, String type) {
        return null;
    }

    @Override
    public List<LogRecord> queryLogByBizNo(String bizNo, String type, String subType) {
        return null;
    }
}
