/*
 * cna-openshift-cloud-connector:RabbitMqServiceInfoCreatorTest.java
 * (c) Copyright msg systems ag Automotive Technology 2017
 */
package edu.hm.cs.fwp.cloud.openshift.service.rabbitmq;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.cloud.service.common.AmqpServiceInfo;

import edu.hm.cs.fwp.cloud.openshift.service.ServiceData;
import edu.hm.cs.fwp.cloud.openshift.service.ServiceType;
import edu.hm.cs.fwp.cloud.openshift.service.rabbitmq.RabbitMqServiceInfoCreator;

/**
 * {@code Unit test} for {@code RabbitMqServiceInfoCreator}.
 * 
 * @author theism
 * @version 1.0
 * @since 21.11.2017
 */
public class RabbitMqServiceInfoCreatorTest {

	private static final String RABBITMQ_SERVICE_NAME = "rabbitmq-mydata";

	private static final String RABBITMQ_MQ_HOST_ENVAR_NAME = "RABBITMQ_MQ_HOST";

	private static final String RABBITMQ_MQ_HOST_ENVAR_VALUE = "10.0.4.216";

	private static final String RABBITMQ_MQ_PORT_ENVAR_NAME = "RABBITMQ_MQ_PORT";

	private static final String RABBITMQ_MQ_PORT_ENVAR_VALUE = "5672";

	private static final String RABBITMQ_MQ_USER_ENVAR_NAME = "RABBITMQ_MQ_USER";

	private static final String RABBITMQ_MQ_USER_ENVAR_VALUE = "mydata_rabbitmq_user";

	private static final String RABBITMQ_MQ_PASSWORD_ENVAR_NAME = "RABBITMQ_MQ_PASSWORD";

	private static final String RABBITMQ_MQ_PASSWORD_ENVAR_VALUE = "s3cr3t";

	private final RabbitMqServiceInfoCreator underTest = new RabbitMqServiceInfoCreator();

	@Test
	public void createServiceInfoWithMongoDbServiceDataReturnsExpectedServiceInfo() {
		ServiceData serviceData = new ServiceData.Builder(ServiceType.RABBITMQ, RABBITMQ_SERVICE_NAME)
				.withVariable(RABBITMQ_MQ_HOST_ENVAR_NAME, RABBITMQ_MQ_HOST_ENVAR_VALUE)
				.withVariable(RABBITMQ_MQ_PORT_ENVAR_NAME, RABBITMQ_MQ_PORT_ENVAR_VALUE)
				.withVariable(RABBITMQ_MQ_USER_ENVAR_NAME, RABBITMQ_MQ_USER_ENVAR_VALUE)
				.withVariable(RABBITMQ_MQ_PASSWORD_ENVAR_NAME, RABBITMQ_MQ_PASSWORD_ENVAR_VALUE).build();
		AmqpServiceInfo serviceInfo = this.underTest.createServiceInfo(serviceData);
		assertNotNull("createServiceInfo() must not return null", serviceInfo);
		assertEquals("service ID must match", RABBITMQ_SERVICE_NAME, serviceInfo.getId());
		assertEquals("service user must match", RABBITMQ_MQ_USER_ENVAR_VALUE, serviceInfo.getUserName());
		assertEquals("service password must match", RABBITMQ_MQ_PASSWORD_ENVAR_VALUE, serviceInfo.getPassword());
		assertEquals("service URI must match",
				"amqp://" + RABBITMQ_MQ_USER_ENVAR_VALUE + ":" + RABBITMQ_MQ_PASSWORD_ENVAR_VALUE + "@"
						+ RABBITMQ_MQ_HOST_ENVAR_VALUE + ":" + RABBITMQ_MQ_PORT_ENVAR_VALUE,
				serviceInfo.getUri());
	}
}
