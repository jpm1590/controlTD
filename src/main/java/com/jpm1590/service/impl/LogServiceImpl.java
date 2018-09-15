package com.jpm1590.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jpm1590.service.LogService;

public class LogServiceImpl implements LogService {
	
	private static final Log LOG = LogFactory.getLog(LogServiceImpl.class);

	@Override
	public void messageLog() {
		// TODO Auto-generated method stub
		LOG.info("LOG de Servicio");		
	}

}
