/*
 * cna-kubernetes-cloud-connector:RabbitMqServiceInfoCreator.java
 */
package edu.hm.cs.fwp.cloud.kubernetes.service.rabbitmq;

import org.springframework.cloud.service.common.AmqpServiceInfo;

import edu.hm.cs.fwp.cloud.kubernetes.service.AbstractServiceInfoCreator;
import edu.hm.cs.fwp.cloud.kubernetes.service.ServiceData;
import edu.hm.cs.fwp.cloud.kubernetes.service.ServiceType;

/**
 * {@code ServiceInfoCreator} for RabbitMQ-specific {@code ServiceInfo}.
 * 
 * @author theism
 * @version 1.0
 * @since 16.11.2017
 */
public final class RabbitMqServiceInfoCreator extends AbstractServiceInfoCreator<AmqpServiceInfo> {

	public RabbitMqServiceInfoCreator() {
		super(ServiceType.RABBITMQ);
	}

	/**
	 * @see edu.hm.cs.fwp.cloud.kubernetes.service.AbstractServiceInfoCreator#createServiceInfo(edu.hm.cs.fwp.cloud.kubernetes.service.ServiceData)
	 */
	@Override
	public AmqpServiceInfo createServiceInfo(ServiceData serviceData) {
		String hostName = serviceData.getRequiredValue("RABBITMQ_MQ_HOST");
		int portNumber = serviceData.getRequiredIntValue("RABBITMQ_MQ_PORT");
		String user = serviceData.getRequiredValue("RABBITMQ_MQ_USER");
		String password = serviceData.getRequiredValue("RABBITMQ_MQ_PASSWORD");
		AmqpServiceInfo result = new AmqpServiceInfo(serviceData.getName(),
				buildCanonicalUri(hostName, portNumber, user, password));
		this.logger.info("Returning service info [{}] for RabbitMQ service [{}]", result, result.getId());
		return result;
	}

	private String buildCanonicalUri(String hostName, int portNumber, String user, String password) {
		StringBuilder result = new StringBuilder();
		result.append("amqp://");
		result.append(user).append(":").append(password).append("@");
		result.append(hostName);
		if (portNumber > 0) {
			result.append(":").append(portNumber);
		}
		return result.toString();
	}

}
