/*
 * Copyright contributors to the Galasa project
 */

package dev.galasa.inttests.ceci;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.gson.JsonObject;

import dev.galasa.BeforeClass;
import dev.galasa.Test;
import dev.galasa.galasaecosystem.GalasaEcosystemManagerException;
import dev.galasa.galasaecosystem.IGenericEcosystem;
import dev.galasa.zos.IZosImage;

public abstract class AbstractCECILocal {
	
	@BeforeClass
    public void setUp() throws GalasaEcosystemManagerException {
		
		// Properties for provisioning a CICS Region
        getEcosystem().setCpsProperty("cicsts.provision.type", getEcosystem().getHostCpsProperty("cicsts", "provision", "type"));
        String cicstsDefaultVersion = getEcosystem().getHostCpsProperty("cicsts", "default", "version");
        getEcosystem().setCpsProperty("cicsts.default.version", cicstsDefaultVersion);
        getEcosystem().setCpsProperty("cicsts.default.logon.initial.text", getEcosystem().getHostCpsProperty("cicsts", "default.logon", "text", "initial"));
        getEcosystem().setCpsProperty("cicsts.default.logon.gm.text", getEcosystem().getHostCpsProperty("cicsts", "default.logon", "text", "gm"));
        // Properties for SEM
        String semInternalVersion = getEcosystem().getHostCpsProperty("sem", "internal", cicstsDefaultVersion, "version");
        getEcosystem().setCpsProperty("sem.internal.version." + cicstsDefaultVersion, semInternalVersion);
        String semExternalVersion = getEcosystem().getHostCpsProperty("sem", "external", semInternalVersion, "version");
        getEcosystem().setCpsProperty("sem.external.version." + semInternalVersion, semExternalVersion);
        getEcosystem().setCpsProperty("sem.model.url", getEcosystem().getHostCpsProperty("sem", "model", "url"));
        getEcosystem().setCpsProperty("sem.model.ports", getEcosystem().getHostCpsProperty("sem", "model", "ports"));
        getEcosystem().setCpsProperty("sem.model.applids", getEcosystem().getHostCpsProperty("sem", "model", "applids"));
        // Properties for compiling Zos Programs
        getEcosystem().setCpsProperty("zosprogram.cobol." + getZosImage().getImageID() + ".dataset.prefix", getEcosystem().getHostCpsProperty("zosprogram", "cobol", "dataset.prefix", getZosImage().getImageID()));
        getEcosystem().setCpsProperty("zosprogram.le." + getZosImage().getImageID() + ".dataset.prefix", getEcosystem().getHostCpsProperty("zosprogram", "le", "dataset.prefix", getZosImage().getImageID()));  
        getEcosystem().setCpsProperty("zosprogram.cics." + getZosImage().getImageID() + ".dataset.prefix", getEcosystem().getHostCpsProperty("zosprogram", "cics", "dataset.prefix", getZosImage().getImageID()));
        	
	}

    @Test
    public void testCECIIvtTest() throws Exception {

        String runName = getEcosystem().submitRun(null, 
                null, 
                null, 
                "dev.galasa.cicsts.ceci.manager.ivt",
                "dev.galasa.cicsts.ceci.manager.ivt.CECIManagerIVT", 
                null, 
                null, 
                null, 
                null);
        
        JsonObject run = getEcosystem().waitForRun(runName);
        
        String result = run.get("result").getAsString();
        
        assertThat(result).as("The test indicates the test passes").isEqualTo("Passed");

    }

    abstract protected IGenericEcosystem getEcosystem();
    
    abstract protected IZosImage getZosImage();
    
}