package com.learnaem.core.services.impl;

import org.osgi.service.component.annotations.Component;

import com.learnaem.core.services.SampleService;
@Component(service=SampleService.class)
public class SampleServiceImpl implements SampleService{

	@Override
	public String getValues() {
		return "Hello Welcome to Services";
	}

}
