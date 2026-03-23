/*
 * Copyright contributors to the Galasa project
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package dev.galasa.inttests.docker;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.logging.Log;

import com.google.gson.JsonObject;

import dev.galasa.BeforeClass;
import dev.galasa.ICredentialsKeyStore;
import dev.galasa.Test;
import dev.galasa.core.manager.CoreManager;
import dev.galasa.core.manager.ICoreManager;
import dev.galasa.core.manager.Logger;
import dev.galasa.galasaecosystem.IGenericEcosystem;
import dev.galasa.ipnetwork.ICommandShell;
import dev.galasa.linux.ILinuxImage;

public abstract class AbstractDocker {
	
	@Logger
	public Log logger;

	protected ICommandShell shell;

    @CoreManager
    public ICoreManager coreManager;
	
	final static String DOCKER_PORT = "2376";

	@BeforeClass
	public void setProps() throws Exception {
		logger.info("Setting the CPS props for shadow environment");
		String dockerHostname = getDockerLinuxImage().getIpHost().getIpv4Hostname();
		getEcosystem().setCpsProperty("docker.default.engines", "DKRTESTENGINE");
		getEcosystem().setCpsProperty("docker.engine.DKRTESTENGINE.hostname", dockerHostname);
		getEcosystem().setCpsProperty("docker.engine.DKRTESTENGINE.port", DOCKER_PORT);
		getEcosystem().setCpsProperty("docker.engine.DKRTESTENGINE.max.slots", "3");
		// TODO: remove hard coded values
		getEcosystem().setCpsProperty("docker.default.registries", "GHCR");
		getEcosystem().setCpsProperty("docker.registry.GHCR.url", "https://ghcr.io");
		getEcosystem().setCpsProperty("docker.registry.GHCR.busybox.image", "galasa-dev/busybox:1.36.1");
		
		// Credentials
		getEcosystem().setCpsProperty("docker.engine.DKRTESTENGINE.credentials.id", "DOCKER_TLS_CERTS");
		ICredentialsKeyStore keystoreCreds = (ICredentialsKeyStore) coreManager.getCredentials("DOCKER_TLS_CERTS");
		getEcosystem().setCredsProperty("secure.credentials.DOCKER_TLS_CERTS.keystore", keystoreCreds.getEncodedKeyStore());
		getEcosystem().setCredsProperty("secure.credentials.DOCKER_TLS_CERTS.password", keystoreCreds.getKeyStorePassword());
		getEcosystem().setCredsProperty("secure.credentials.DOCKER_TLS_CERTS.type", keystoreCreds.getKeyStoreType());
	}
	
	@Test
	public void testDockerIvtTest() throws Exception {
		
		String runName = getEcosystem().submitRun(null,
				null,
				null,
				"dev.galasa.docker.manager.ivt", 
                "dev.galasa.docker.manager.ivt.DockerManagerIVT", 
                null, 
                null, 
                null, 
                null);
		
		
		JsonObject run = getEcosystem().waitForRun(runName);
        
        String result = run.get("result").getAsString();
        
        assertThat(result).describedAs("The test indicates the test passes").isEqualTo("Passed");
	}
	
	abstract protected IGenericEcosystem getEcosystem() throws Exception;
	
	abstract protected ILinuxImage getDockerLinuxImage() throws Exception;
}
