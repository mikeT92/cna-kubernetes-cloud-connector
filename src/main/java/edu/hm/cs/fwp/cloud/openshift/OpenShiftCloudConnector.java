/*
 * cna-openshift-cloud-connector:OpenShiftCloudConnector.java
 */
package edu.hm.cs.fwp.cloud.openshift;

import org.springframework.cloud.FallbackServiceInfoCreator;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.cloud.util.EnvironmentAccessor;

import edu.hm.cs.fwp.cloud.common.AbstractCloudConnector;
import edu.hm.cs.fwp.cloud.openshift.service.KubernetesFallbackServiceInfoCreator;
import edu.hm.cs.fwp.cloud.openshift.service.ServiceData;
import edu.hm.cs.fwp.cloud.openshift.service.mongodb.MongoDbServiceDataResolver;
import edu.hm.cs.fwp.cloud.openshift.service.mongodb.MongoDbServiceInfoCreator;
import edu.hm.cs.fwp.cloud.openshift.service.mysql.MySqlServiceDataResolver;
import edu.hm.cs.fwp.cloud.openshift.service.mysql.MySqlServiceInfoCreator;
import edu.hm.cs.fwp.cloud.openshift.service.rabbitmq.RabbitMqServiceDataResolver;
import edu.hm.cs.fwp.cloud.openshift.service.rabbitmq.RabbitMqServiceInfoCreator;

/**
 * Kubernetes-specific cloud connector.
 * 
 * @author Michael Theis
 * @version 1.0
 * @since 07.05.2018
 */
public final class OpenShiftCloudConnector extends AbstractCloudConnector<ServiceData> {

	/**
	 * Name of the environment variable that triggers this {@code CloudConnector}.
	 */
	public static final String CLOUD_ENVVAR_NAME = "CNAP_CLOUD";

	/**
	 * Value of the environment variable
	 * {@link OpenShiftCloudConnector#CLOUD_ENVVAR_NAME} that triggers this
	 * {@code CloudConnector}.
	 */
	public static final String CLOUD_ENVVAR_VALUE = "openhift";

	/**
	 * Default constructor for instantiation via service lookup by Spring Cloud.
	 */
	public OpenShiftCloudConnector() {
		this(new EnvironmentAccessor());
	}

	/**
	 * Specialized constructor for test purposes.
	 * 
	 * @param environment
	 *            test environment
	 */
	public OpenShiftCloudConnector(EnvironmentAccessor environment) {
		super(environment);
		registerServiceDataResolver(new MySqlServiceDataResolver());
		registerServiceInfoCreator(new MySqlServiceInfoCreator());
		registerServiceDataResolver(new RabbitMqServiceDataResolver());
		registerServiceInfoCreator(new RabbitMqServiceInfoCreator());
		registerServiceDataResolver(new MongoDbServiceDataResolver());
		registerServiceInfoCreator(new MongoDbServiceInfoCreator());
	}

	/**
	 * Returns {@code true}, if environment variable {@link #CLOUD_ENVVAR_NAME} is
	 * set to the value {@link #CLOUD_ENVVAR_VALUE}.
	 * 
	 * @see org.springframework.cloud.CloudConnector#isInMatchingCloud()
	 */
	@Override
	public boolean isInMatchingCloud() {
		String cloudEnvironment = getEnvironment().getEnvValue(CLOUD_ENVVAR_NAME);
		return CLOUD_ENVVAR_VALUE.equals(cloudEnvironment);
	}

	/**
	 * Returns the {@code FallbackServiceInfoCreator} for this
	 * {@code CloudConnector}, if no {@code ServiceInfoCreator} for a service
	 * defined in the environment could be found.
	 * 
	 * @see edu.hm.cs.fwp.cloud.common.AbstractCloudConnector#getFallbackServiceInfoCreator()
	 */
	@Override
	public FallbackServiceInfoCreator<ServiceInfo, ServiceData> getFallbackServiceInfoCreator() {
		return new KubernetesFallbackServiceInfoCreator();
	}
}
