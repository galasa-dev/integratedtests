/*
 * Licensed Materials - Property of IBM
 * 
 * (c) Copyright IBM Corp. 2021.
 */
package dev.galasa.inttests.compilation.simbank.local.isolated;

import dev.galasa.Test;
import dev.galasa.TestAreas;
import dev.galasa.galasaecosystem.IGenericEcosystem;
import dev.galasa.galasaecosystem.ILocalEcosystem;
import dev.galasa.galasaecosystem.IsolationInstallation;
import dev.galasa.galasaecosystem.LocalEcosystem;
import dev.galasa.inttests.compilation.simbank.AbstractCompilationLocalSimBankOffline;
import dev.galasa.java.JavaVersion;
import dev.galasa.java.ubuntu.IJavaUbuntuInstallation;
import dev.galasa.java.ubuntu.JavaUbuntuInstallation;
import dev.galasa.linux.ILinuxImage;
import dev.galasa.linux.LinuxImage;
import dev.galasa.linux.OperatingSystem;

@Test
@TestAreas({"compilation","localecosystem","java08","ubuntu","isolated"})
public class CompilationLocalJava08UbuntuIsolated extends AbstractCompilationLocalSimBankOffline {

    @LocalEcosystem(linuxImageTag = "PRIMARY", isolationInstallation = IsolationInstallation.Full)
    public ILocalEcosystem ecosystem;
    
    @LinuxImage(operatingSystem = OperatingSystem.ubuntu, capabilities = "isolated")
    public ILinuxImage linuxImage;
    
    @JavaUbuntuInstallation(javaVersion = JavaVersion.v8)
    public IJavaUbuntuInstallation java;
    
    @Override
    protected IGenericEcosystem getEcosystem() {
        return this.ecosystem;
    }

    @Override
    protected ILinuxImage getLinuxImage() {
        return this.linuxImage;
    }
    
    @Override
    protected IJavaUbuntuInstallation getJavaInstallation() {
        return this.java;
    }

}