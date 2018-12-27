package com.topgroup.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.topgroup.service.LogService;

public class LogServiceImpl implements LogService {
	
	private static final Log LOG = LogFactory.getLog(LogServiceImpl.class);

	@Override
	public void messageLog() {
		// TODO Auto-generated method stub
		LOG.info("LOG de Servicio");		
	}

}
