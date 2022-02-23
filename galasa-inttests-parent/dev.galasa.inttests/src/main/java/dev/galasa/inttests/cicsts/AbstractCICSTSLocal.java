/*
 * Copyright contributors to the Galasa project
 */

package dev.galasa.inttests.cicsts;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.gson.JsonObject;

import dev.galasa.BeforeClass;
import dev.galasa.Test;
import dev.galasa.core.manager.IResourceString;
import dev.galasa.core.manager.ResourceString;
import dev.galasa.galasaecosystem.GalasaEcosystemManagerException;
import dev.galasa.galasaecosystem.IGenericEcosystem;

public abstract class AbstractCICSTSLocal {
	
	@ResourceString(tag = "VARNAME", length = 8)
	public IResourceString resourceString1;
	
	@ResourceString(tag = "PROG", length = 8)
	public IResourceString resourceString2;
	
	@ResourceString(tag = "GROUP", length = 8)
	public IResourceString resourceString3;
	
	@BeforeClass
	public void setUp() throws GalasaEcosystemManagerException {
        getEcosystem().setCpsProperty("cicsts.dse.tag.PRIMARY.applid", "IYK2ZNB5");
        getEcosystem().setCpsProperty("cicsts.provision.type", "DSE");
        getEcosystem().setCpsProperty("cicsts.default.logon.initial.text", "HIT ENTER FOR LATEST STATUS");
        getEcosystem().setCpsProperty("cicsts.default.logon.gm.text", "******\\(R)");
        
        getEcosystem().setCpsProperty("test.IVT.RESOURCE.STRING.VARNAME", resourceString1.toString());	
        getEcosystem().setCpsProperty("test.IVT.RESOURCE.STRING.PROG", resourceString2.toString());	
        getEcosystem().setCpsProperty("test.IVT.RESOURCE.STRING.GROUP", resourceString3.toString());	
	}

    @Test
    public void testCICSTSIvtTest() throws Exception {

        String runName = getEcosystem().submitRun(null, 
                null, 
                null, 
                "dev.galasa.cicsts.manager.ivt",
                "dev.galasa.cicsts.manager.ivt.CICSTSManagerIVT", 
                null, 
                null, 
                null, 
                null);
        
        JsonObject run = getEcosystem().waitForRun(runName);
        
        String result = run.get("result").getAsString();
        
        assertThat(result).as("The test indicates the test passes").isEqualTo("Passed");

    }

    abstract protected IGenericEcosystem getEcosystem();
    
}