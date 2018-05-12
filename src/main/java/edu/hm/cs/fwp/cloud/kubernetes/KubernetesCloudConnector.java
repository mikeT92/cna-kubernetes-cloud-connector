/*
 * cna-kubernetes-cloud-connector:KubernetesCloudConnector.java
 */
package edu.hm.cs.fwp.cloud.kubernetes;

import org.springframework.cloud.FallbackServiceInfoCreator;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.cloud.util.EnvironmentAccessor;

import edu.hm.cs.fwp.cloud.common.AbstractCloudConnector;
import edu.hm.cs.fwp.cloud.kubernetes.service.KubernetesFallbackServiceInfoCreator;
import edu.hm.cs.fwp.cloud.kubernetes.service.ServiceData;
import edu.hm.cs.fwp.cloud.kubernetes.service.mongodb.MongoDbServiceDataResolver;
import edu.hm.cs.fwp.cloud.kubernetes.service.mongodb.MongoDbServiceInfoCreator;
import edu.hm.cs.fwp.cloud.kubernetes.service.mysql.MySqlServiceDataResolver;
import edu.hm.cs.fwp.cloud.kubernetes.service.mysql.MySqlServiceInfoCreator;
import edu.hm.cs.fwp.cloud.kubernetes.service.rabbitmq.RabbitMqServiceDataResolver;
import edu.hm.cs.fwp.cloud.kubernetes.service.rabbitmq.RabbitMqServiceInfoCreator;

/**
 * Kubernetes-specific cloud connector.
 * 
 * @author Michael Theis
 * @version 1.0
 * @since 07.05.2018
 */
public final class KubernetesCloudConnector extends AbstractCloudConnector<ServiceData> {

	/**
	 * Name of the environment variable that triggers this {@code CloudConnector}.
	 */
	public static final String CLOUD_ENVVAR_NAME = "CNAP_CLOUD";

	/**
	 * Value of the environment variable
	 * {@link KubernetesCloudConnector#CLOUD_ENVVAR_NAME} that triggers this
	 * {@code CloudConnector}.
	 */
	public static final String[] CLOUD_ENVVAR_VALUES = { "kubernetes", "openshift" };

	/**
	 * Default constructor for instantiation via service lookup by Spring Cloud.
	 */
	public KubernetesCloudConnector() {
		this(new EnvironmentAccessor());
	}

	/**
	 * Specialized constructor for test purposes.
	 * 
	 * @param environment
	 *            test environment
	 */
	public KubernetesCloudConnector(EnvironmentAccessor environment) {
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
	 * set to any of the values {@link #CLOUD_ENVVAR_VALUES}.
	 * 
	 * @see org.springframework.cloud.CloudConnector#isInMatchingCloud()
	 */
	@Override
	public boolean isInMatchingCloud() {
		boolean result = false;
		String cloudEnvironment = getEnvironment().getEnvValue(CLOUD_ENVVAR_NAME);
		for (String current : CLOUD_ENVVAR_VALUES) {
			if (current.equalsIgnoreCase(cloudEnvironment)) {
				result = true;
				break;
			}
		}
		return result;
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
