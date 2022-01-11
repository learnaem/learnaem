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

import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.settings.SlingSettingsService;

import com.day.cq.dam.api.Asset;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@Model(adaptables = SlingHttpServletRequest.class)
public class HelloWorldModel {

    @ValueMapValue(name=PROPERTY_RESOURCE_TYPE, injectionStrategy=InjectionStrategy.OPTIONAL)
    @Default(values="No resourceType")
    protected String resourceType;

    @OSGiService
    private SlingSettingsService settings;
    @SlingObject
    private Resource currentResource;
    @SlingObject
    private ResourceResolver resourceResolver;
    
    @RequestAttribute
    private String image;

    
    private String message;
    
    private String[] techUsedinAEM;
    
    private String size;

    @PostConstruct
    protected void init() {
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        String currentPagePath = Optional.ofNullable(pageManager)
                .map(pm -> pm.getContainingPage(currentResource))
                .map(Page::getPath).orElse("");
   
      String finalImagePath = image+"/jcr:content/metadata";
      Resource r = resourceResolver.getResource(finalImagePath);
      
      ValueMap vr = r.adaptTo(ValueMap.class); // 1st way
      MetadataValues m = r.adaptTo(MetadataValues.class); //2nd ways
      size=m.getSize();
      Resource imageres = resourceResolver.getResource(image);
      /*Asset asset = imageres != null ? imageres.adaptTo(Asset.class) : null; //3rd way
      Map<String, Object> assetMetadata = asset != null ? asset.getMetadata() : null;
      */
      Asset asset = imageres.adaptTo(Asset.class);
      Map<String, Object> assetMetadata = asset.getMetadata();
    
      long assestSize =   (long) assetMetadata.get("dam:size");
      long s = vr.get("dam:size", Long.class);

        message = "Hello World!\n"
            + "Resource type is: " + resourceType + "\n"
            + "Current page is:  " + currentPagePath + "\n"
            + "This is instance: " + settings.getSlingId() + "\n";
        techUsedinAEM= new String[]{"java","sightly","sling","OSGI","JCR","JettyServer"};
    }

    public String getMessage() {
        return message;
    }

	public String[] getTechUsedinAEM() {
		return techUsedinAEM;
	}

	public String getSize() {
		return size;
	}
    

}
