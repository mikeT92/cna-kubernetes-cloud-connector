/*
 * cna-kubernetes-cloud-connector:MySqlServiceDataResolverTest.java
 */
package edu.hm.cs.fwp.cloud.kubernetes.service.mysql;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.cloud.util.EnvironmentAccessor;

import edu.hm.cs.fwp.cloud.kubernetes.KubernetesCloudConnector;
import edu.hm.cs.fwp.cloud.kubernetes.service.ServiceData;
import edu.hm.cs.fwp.cloud.kubernetes.service.mysql.MySqlServiceDataResolver;

/**
 * {@code Unit test} for {@link KubernetesCloudConnector}.
 * 
 * @author theism
 * @version 1.0
 * @since 16.11.2017
 */
public class MySqlServiceDataResolverTest {

	public static final String MYSQL_DB_HOST_ENVAR_NAME = "MYSQL_DB_HOST";

	public static final String MYSQL_DB_HOST_ENVAR_VALUE = "10.0.4.276";

	public static final String MYSQL_DB_PORT_ENVAR_NAME = "MYSQL_DB_PORT";

	public static final String MYSQL_DB_PORT_ENVAR_VALUE = "3306";

	public static final String MYSQL_DB_NAME_ENVAR_NAME = "MYSQL_DB_NAME";

	public static final String MYSQL_DB_NAME_ENVAR_VALUE = "mydata_accounts_db";

	public static final String MYSQL_DB_USER_ENVAR_NAME = "MYSQL_DB_USER";

	public static final String MYSQL_DB_USER_ENVAR_VALUE = "mydata_accounts_user";

	public static final String MYSQL_DB_PASSWORD_ENVAR_NAME = "MYSQL_DB_PASSWORD";

	public static final String MYSQL_DB_PASSWORD_ENVAR_VALUE = "s3cr3t";

	private final MySqlServiceDataResolver underTest = new MySqlServiceDataResolver();

	@Test
	public void resolveReturnsMySqlServiceDataIfMySqlEnvVarsAreSet() {
		Map<String, String> valuesByName = new HashMap<>();
		valuesByName.put(MYSQL_DB_HOST_ENVAR_NAME, MYSQL_DB_HOST_ENVAR_VALUE);
		valuesByName.put(MYSQL_DB_PORT_ENVAR_NAME, MYSQL_DB_PORT_ENVAR_VALUE);
		valuesByName.put(MYSQL_DB_NAME_ENVAR_NAME, MYSQL_DB_NAME_ENVAR_VALUE);
		valuesByName.put(MYSQL_DB_USER_ENVAR_NAME, MYSQL_DB_USER_ENVAR_VALUE);
		valuesByName.put(MYSQL_DB_PASSWORD_ENVAR_NAME, MYSQL_DB_PASSWORD_ENVAR_VALUE);
		EnvironmentAccessor environment = Mockito.mock(EnvironmentAccessor.class);
		Mockito.when(environment.getEnv()).thenReturn(valuesByName);
		List<ServiceData> serviceData = this.underTest.resolve(environment);
		assertNotNull("resolve() must not return null", serviceData);
		assertTrue("resolve() must not return empty list", !serviceData.isEmpty());
	}

}
