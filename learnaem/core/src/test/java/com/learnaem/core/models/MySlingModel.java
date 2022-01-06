package com.learnaem.core.models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = SlingHttpServletRequest.class,defaultInjectionStrategy=DefaultInjectionStrategy.OPTIONAL)
public class MySlingModel {

	@RequestAttribute
	private String param1;
	@RequestAttribute
	private String one;
	@RequestAttribute
	private String two;
	@ValueMapValue
	private String dob;

	@ValueMapValue
	private String fname;

	@ValueMapValue
	private String lname;

	@ValueMapValue(name = "jcr:title")
	private String title;

	private long age;

	@PostConstruct
	public void init()
	{
		if (dob != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
			LocalDateTime from = LocalDateTime.parse(dob, formatter);
			LocalDateTime now = LocalDateTime.now();
			Duration timeDifferencehours = Duration.between(from, now);
			long timeinHours = timeDifferencehours.toHours();
			age = timeinHours / (24 * 365);
		}
		fullName = fname + "AEM" + lname;

		if(StringUtils.isNotEmpty(param1))
		{

		}

	}


	public String getFname() {
		return fname;
	}

	public String getLname() {
		return lname;
	}

	public String getTitle() {
		return title;
	}
	public long getAge() {
		return age;
	}


	public String getFullName() {
		return fullName;
	}


	public String getParam1() {
		return param1;
	}


	public String getOne() {
		return one;
	}


	public String getTwo() {
		return two;
	}




}