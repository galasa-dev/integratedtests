/*
 * Copyright contributors to the Galasa project
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package dev.galasa.inttests.zosVSAM.local.isolated;

import dev.galasa.Test;
import dev.galasa.TestAreas;
import dev.galasa.galasaecosystem.IGenericEcosystem;
import dev.galasa.galasaecosystem.ILocalEcosystem;
import dev.galasa.galasaecosystem.IsolationInstallation;
import dev.galasa.galasaecosystem.LocalEcosystem;
import dev.galasa.inttests.zosVSAM.AbstractZosVSAMLocalRSE;
import dev.galasa.java.JavaVersion;
import dev.galasa.java.ubuntu.IJavaUbuntuInstallation;
import dev.galasa.java.ubuntu.JavaUbuntuInstallation;
import dev.galasa.linux.ILinuxImage;
import dev.galasa.linux.LinuxImage;
import dev.galasa.linux.OperatingSystem;
import dev.galasa.zos.IZosImage;
import dev.galasa.zos.ZosImage;

// @Test
@TestAreas({"zosManager","localecosystem","java11","ubuntu","isolated"})
public class ZosVSAMLocalJava11UbuntuIsolatedRse extends AbstractZosVSAMLocalRSE {

    @LocalEcosystem(linuxImageTag = "PRIMARY", isolationInstallation = IsolationInstallation.Full, addDefaultZosImage = "PRIMARY")
    public ILocalEcosystem ecosystem;
    
    @LinuxImage(operatingSystem = OperatingSystem.ubuntu, capabilities = "isolated")
    public ILinuxImage linuxImage;
    
    @JavaUbuntuInstallation(javaVersion = JavaVersion.v11)
    public IJavaUbuntuInstallation java;
    
    @ZosImage
    public IZosImage zosImage;

    @Override
    protected IGenericEcosystem getEcosystem() {
        return this.ecosystem;
    }

}
