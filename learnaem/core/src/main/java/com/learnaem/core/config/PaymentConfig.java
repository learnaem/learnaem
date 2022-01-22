package com.learnaem.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name="Payment Config",description="Payment Config Description")
public @interface PaymentConfig {
	
	@AttributeDefinition(name="Payment GateWay",description="Payment Gateway Url")
	String getPayment() default "https://payment.testing.api.com";

}
