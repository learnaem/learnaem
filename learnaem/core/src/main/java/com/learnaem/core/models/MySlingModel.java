package com.learnaem.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables=Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MySlingModel {
	
@ValueMapValue(name="jcr:title")
private String title;

@ValueMapValue
private String fname;
@ValueMapValue
private String lname;

@ValueMapValue
private String dob;

public String getTitle() {
	return title;
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



}
