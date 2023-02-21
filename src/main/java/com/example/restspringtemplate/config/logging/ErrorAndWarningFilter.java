package com.example.restspringtemplate.config.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

import java.util.List;

/**
 * Filter to allow only error and warning level log messages.
 */
public class ErrorAndWarningFilter extends Filter<ILoggingEvent> {
	@Override
	public FilterReply decide(ILoggingEvent event) {
		return List.of(Level.ERROR, Level.WARN).contains(event.getLevel())
		       ? FilterReply.ACCEPT
		       : FilterReply.DENY;
	}
}
