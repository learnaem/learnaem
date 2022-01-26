package com.learnaem.core.services.impl;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;

import com.learnaem.core.config.PaymentConfig;
import com.learnaem.core.services.SampleService;

@Component(service=SampleService.class)
@Designate(ocd=PaymentConfig.class)
public class SampleServiceImpl implements SampleService{

	@Reference
	ResourceResolverFactory resourceResolverFactory;
	
	ResourceResolver rr;
	
	private String paymentValue;
	
	String path="/content/learnaem";
	
	@Activate
	@Modified
	public void setConfigValues(PaymentConfig config)
	{
		System.out.println("This is will execute on Activate");
		paymentValue=config.getPayment();
		
	}
	

	@Override
	public String getOsgiPayment() {
		return paymentValue;
	}


	public String getPaymentValue() {
		return paymentValue;
	}


	public void setPaymentValue(String paymentValue) {
		this.paymentValue = paymentValue;
	}

/*	@Override
	public void getValues() throws LoginException, ItemExistsException, PathNotFoundException, NoSuchNodeTypeException,
			LockException, VersionException, ConstraintViolationException, RepositoryException, PersistenceException {
		ResourceResolver rr = ServiceUtils.newResolver(resourceResolverFactory);
		Resource r = rr.getResource(path);
		Node node = r.adaptTo(Node.class);
		node.addNode("Heloo", "nt:unstructured");
		node.setProperty("hel", "someValue");
		rr.commit();
		rr.close();
	}*/
	

}
