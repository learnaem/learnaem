package com.learnaem.core.models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BeSlingModel {

	
    @RequestAttribute
	private String param1;
    @RequestAttribute
    private String one;
    @RequestAttribute
    private String two;
	
	@ValueMapValue(name = "jcr:title")
	private String title;

	@ValueMapValue
	private String fname;

	@ValueMapValue
	private String lname;

	@ValueMapValue
	private String dob;

	private long age;

	@PostConstruct
	public void init() {
		if (dob != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
			LocalDateTime from = LocalDateTime.parse(dob, formatter);
			LocalDateTime now = LocalDateTime.now();
			Duration timeDifferencehours = Duration.between(from, now);
			long timeinHours = timeDifferencehours.toHours();
			age = timeinHours / (24 * 365);
		}

	}

	public String getTitle() {
		return title;
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
	public String getFname() {
		return fname;
	}

	public String getLname() {
		return lname;
	}

	public String getDob() {
		return dob;
	}

	public long getAge() {
		return age;
	}
}