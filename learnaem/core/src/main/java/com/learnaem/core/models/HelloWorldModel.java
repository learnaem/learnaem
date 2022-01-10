/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.learnaem.core.models;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.settings.SlingSettingsService;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Model(adaptables = Resource.class)
public class HelloWorldModel extends Object{

	@ValueMapValue(name = PROPERTY_RESOURCE_TYPE, injectionStrategy = InjectionStrategy.OPTIONAL)
	@Default(values = "No resourceType")
	protected String resourceType;

	@OSGiService
	private SlingSettingsService settings;
	@SlingObject
	private Resource currentResource;
	@SlingObject
	private ResourceResolver resourceResolver;

	private String message;
	
	private String[] techUsedinAEM;
	
	private Map m;

	@PostConstruct
	protected void init() {
		PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
		String currentPagePath = Optional.ofNullable(pageManager).map(pm -> pm.getContainingPage(currentResource))
				.map(Page::getPath).orElse("");
		HelloWorldModel h = new HelloWorldModel();
		
		ValueMap v = currentResource.adaptTo(ValueMap.class);
		 String str = (String) v.get("fname");
		 String s = (String) v.get("jcr:title");
		 String s1 = (String) v.get("jcr:createdBy");
		 String s2 = (String) v.get("sling:resourceType");
		// String s3 =  (String)v.get("jcr:created");
		 Calendar calendar = v.get(JcrConstants.JCR_CREATED, Calendar.class);
		 
		m  = new HashMap<>();
		 
	     m.put("fname",str); 
	     m.put("jcr:title",s);
	     m.put("jcr:createdBy",s1);
	     m.put("sling:resourceType",s2); 
	    // m.put("jcr:created", s3);
	     m.put("calendar", calendar);
	     Date d = new Date();
	     
	     

		message = "Hello World!\n" + "Resource type is: " + resourceType + "\n" + "Current page is:  " + currentPagePath
				+ "\n" + "This is instance: " + settings.getSlingId() + "\n";
		techUsedinAEM = new String[] { "java", "javascript", "html", "jcr", "OSGI", "sling" };
	}

	public String getMessage() {
		return message;
	}

	public String[] getTechUsedinAEM() {
		return techUsedinAEM;
	}

	public String getResourceType() {
		return resourceType;
	}

	public SlingSettingsService getSettings() {
		return settings;
	}

	public Resource getCurrentResource() {
		return currentResource;
	}

	public ResourceResolver getResourceResolver() {
		return resourceResolver;
	}

	public Map getM() {
		return m;
	}
	

}
