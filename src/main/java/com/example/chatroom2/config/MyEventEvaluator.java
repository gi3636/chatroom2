package com.example.chatroom2.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.boolex.EvaluationException;
import ch.qos.logback.core.boolex.EventEvaluatorBase;

/**
 * 自定义logback日志输出
 *
 * @author daify
 * @date 2019-07-17 14:26
 **/
public class MyEventEvaluator extends EventEvaluatorBase<ILoggingEvent> {

    private String page;

    /**
     *
     */
    @Override
    public boolean evaluate(ILoggingEvent event)
            throws NullPointerException, EvaluationException {
        String loggerName = event.getLoggerName();
        String substring = page.substring(0, page.indexOf(".*"));
        return loggerName.startsWith(substring);
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
