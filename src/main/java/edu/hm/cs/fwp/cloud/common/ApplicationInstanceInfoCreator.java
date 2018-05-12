/*
 * cna-kubernetes-cloud-connector:ApplicationInstanceInfoCreator.java
 */
package edu.hm.cs.fwp.cloud.common;

import org.springframework.cloud.app.ApplicationInstanceInfo;
import org.springframework.cloud.util.EnvironmentAccessor;

/**
 * Represents a {@code Factory} for {@code ApplicationInstanceInfo}'s that can
 * be registered with an {@code CloudConnector}.
 * 
 * @author theism
 * @version 1.0
 * @since 16.11.2017
 */
public interface ApplicationInstanceInfoCreator {

	/**
	 * Creates an Application Instance Info based on the data provided by the
	 * given environment.
	 * 
	 * @param environment
	 *            environment accessor for environment variables and system
	 *            properties
	 * @return cloud-specific implementation of {@code ApplicationInstanceInfo}
	 */
	ApplicationInstanceInfo createApplicationInstanceInfo(EnvironmentAccessor environment);
}
