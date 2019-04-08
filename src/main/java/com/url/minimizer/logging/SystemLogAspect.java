package com.url.minimizer.logging;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.url.minimizer.annotation.Logging;
import com.url.minimizer.service.StatisticService;

@Aspect
@Component
public class SystemLogAspect {

	private static final Logger LOG = LoggerFactory.getLogger(SystemLogAspect.class);

	private final StatisticService statisticService;

	@Autowired
	public SystemLogAspect(StatisticService statisticService) {
		this.statisticService = statisticService;
	}

	@Pointcut("@annotation(logging)")
	public void callAt(Logging logging) {
		//Pointcut for @Logging
	}

	@Around(value = "callAt(logging)", argNames = "joinPoint,logging")
	public Object successRedirect(ProceedingJoinPoint joinPoint, Logging logging) throws Throwable {
		Object result = joinPoint.proceed();
		Object[] args = joinPoint.getArgs();
		LOG.debug("Trying to write statistic for URL {}", args[0]);
		statisticService.addStatistic((String) args[0], (HttpServletRequest) args[1]);
		LOG.debug("Statistic was successfully written.");
		return result;
	}
}
