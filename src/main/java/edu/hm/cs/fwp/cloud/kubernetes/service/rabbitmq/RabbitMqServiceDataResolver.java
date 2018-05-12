/*
 * cna-kubernetes-cloud-connector:RabbitMqServiceDataResolver.java
 */
package edu.hm.cs.fwp.cloud.kubernetes.service.rabbitmq;

import edu.hm.cs.fwp.cloud.kubernetes.service.AbstractServiceDataResolver;
import edu.hm.cs.fwp.cloud.kubernetes.service.ServiceType;

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
