/*
 * cna-kubernetes-cloud-connector:MySqlServiceDataResolverTest.java
 */
package edu.hm.cs.fwp.cloud.kubernetes;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.cloud.service.common.MysqlServiceInfo;
import org.springframework.cloud.util.EnvironmentAccessor;

/**
 * {@code Unit test} for {@link KubernetesCloudConnector}.
 * 
 * @author theism
 * @version 1.0
 * @since 16.11.2017
 */
public class KubernetesCloudConnectorTest {

	private static final String MYSQL_DB_HOST_ENVAR_NAME = "MYSQL_DB_HOST";

	private static final String MYSQL_DB_HOST_ENVAR_VALUE = "10.0.4.276";

	private static final String MYSQL_DB_PORT_ENVAR_NAME = "MYSQL_DB_PORT";

	private static final String MYSQL_DB_PORT_ENVAR_VALUE = "3306";

	private static final String MYSQL_DB_NAME_ENVAR_NAME = "MYSQL_DB_NAME";

	private static final String MYSQL_DB_NAME_ENVAR_VALUE = "mydata_accounts_db";

	private static final String MYSQL_DB_USER_ENVAR_NAME = "MYSQL_DB_USER";

	private static final String MYSQL_DB_USER_ENVAR_VALUE = "mydata_accounts_user";

	private static final String MYSQL_DB_PASSWORD_ENVAR_NAME = "MYSQL_DB_PASSWORD";

	private static final String MYSQL_DB_PASSWORD_ENVAR_VALUE = "s3cr3t";

	private static final String MYSQL_SERVICE_URI = "mysql://" + MYSQL_DB_USER_ENVAR_VALUE + ":"
			+ MYSQL_DB_PASSWORD_ENVAR_VALUE + "@" + MYSQL_DB_HOST_ENVAR_VALUE + ":" + MYSQL_DB_PORT_ENVAR_VALUE + "/"
			+ MYSQL_DB_NAME_ENVAR_VALUE;

	private static final String MYSQL_SERVICE_JDBC_URL = "jdbc:mysql://" + MYSQL_DB_HOST_ENVAR_VALUE + ":"
			+ MYSQL_DB_PORT_ENVAR_VALUE + "/" + MYSQL_DB_NAME_ENVAR_VALUE + "?user=" + MYSQL_DB_USER_ENVAR_VALUE
			+ "&password=" + MYSQL_DB_PASSWORD_ENVAR_VALUE;

	@Test
	public void isInMatchingCloudReturnsTrueIfCloudEnvVarIsSetToKubernetes() {
		EnvironmentAccessor environment = Mockito.mock(EnvironmentAccessor.class);
		Mockito.when(environment.getEnvValue(KubernetesCloudConnector.CLOUD_ENVVAR_NAME))
				.thenReturn(KubernetesCloudConnector.CLOUD_ENVVAR_VALUES[0]);
		KubernetesCloudConnector underTest = new KubernetesCloudConnector(environment);
		assertTrue("CloudConnector.isInMatchingCloud() must return true", underTest.isInMatchingCloud());
	}

	@Test
	public void isInMatchingCloudReturnsTrueIfCloudEnvVarIsSetToOpenshift() {
		EnvironmentAccessor environment = Mockito.mock(EnvironmentAccessor.class);
		Mockito.when(environment.getEnvValue(KubernetesCloudConnector.CLOUD_ENVVAR_NAME))
				.thenReturn(KubernetesCloudConnector.CLOUD_ENVVAR_VALUES[1]);
		KubernetesCloudConnector underTest = new KubernetesCloudConnector(environment);
		assertTrue("CloudConnector.isInMatchingCloud() must return true", underTest.isInMatchingCloud());
	}

	@Test
	public void getServiceInfoReturnMySqlServiceInfoIfMySqlEnvVarsAreSet() {
		Map<String, String> valuesByName = new HashMap<>();
		valuesByName.put(MYSQL_DB_HOST_ENVAR_NAME, MYSQL_DB_HOST_ENVAR_VALUE);
		valuesByName.put(MYSQL_DB_PORT_ENVAR_NAME, MYSQL_DB_PORT_ENVAR_VALUE);
		valuesByName.put(MYSQL_DB_NAME_ENVAR_NAME, MYSQL_DB_NAME_ENVAR_VALUE);
		valuesByName.put(MYSQL_DB_USER_ENVAR_NAME, MYSQL_DB_USER_ENVAR_VALUE);
		valuesByName.put(MYSQL_DB_PASSWORD_ENVAR_NAME, MYSQL_DB_PASSWORD_ENVAR_VALUE);
		EnvironmentAccessor environment = Mockito.mock(EnvironmentAccessor.class);
		Mockito.when(environment.getEnv()).thenReturn(valuesByName);
		KubernetesCloudConnector underTest = new KubernetesCloudConnector(environment);
		List<ServiceInfo> serviceInfos = underTest.getServiceInfos();
		assertNotNull("getServiceInfo() must not return null", serviceInfos);
		assertTrue("getServiceInfo() must not return empty list", !serviceInfos.isEmpty());
		MysqlServiceInfo serviceInfo = (MysqlServiceInfo) serviceInfos.get(0);
		assertEquals("service ID must match", "mysql-unnamed", serviceInfo.getId());
		assertEquals("URI must match", MYSQL_SERVICE_URI, serviceInfo.getUri());
		assertEquals("JDBC URL must match", MYSQL_SERVICE_JDBC_URL, serviceInfo.getJdbcUrl());
	}

}
