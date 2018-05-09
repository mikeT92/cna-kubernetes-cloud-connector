/*
 * cna-openshift-cloud-connector:RabbitMqServiceDataResolver.java
 * (c) Copyright msg systems ag Automotive Technology 2017
 */
package edu.hm.cs.fwp.cloud.openshift.service.rabbitmq;

import edu.hm.cs.fwp.cloud.openshift.service.AbstractServiceDataResolver;
import edu.hm.cs.fwp.cloud.openshift.service.ServiceType;

/**
 * {@code ServiceDataResolver} for RabbitMQ-specific service data.
 * 
 * @author theism
 * @version 1.0
 * @since 16.11.2017
 */
public final class RabbitMqServiceDataResolver extends AbstractServiceDataResolver {

	public RabbitMqServiceDataResolver() {
		super(ServiceType.RABBITMQ, "RABBITMQ_MQ_HOST", "RABBITMQ_MQ_PORT", "RABBITMQ_MQ_USER", "RABBITMQ_MQ_PASSWORD");
	}

}
