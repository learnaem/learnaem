package com.learnaem.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables=Resource.class)
public class MetadataValues {
	
	@ValueMapValue(name="dam:size")
	private String size;
	@ValueMapValue(name="dc:format")
	private String format;
	public String getSize() {
		return size;
	}
	public String getFormat() {
		return format;
	}
}
