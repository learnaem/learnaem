package com.learnaem.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class SlingModel {
	@ValueMapValue
	private String dob;

	@ValueMapValue
	private String fname;

	@ValueMapValue
	private String lname;

	@ValueMapValue(name = "jcr:title")
	private String title;

	public String getDob() {
		return dob;
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

}
