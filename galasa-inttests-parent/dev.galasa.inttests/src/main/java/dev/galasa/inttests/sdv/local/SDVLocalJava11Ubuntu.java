/*
 * Copyright contributors to the Galasa project
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package dev.galasa.inttests.sdv.local;

import dev.galasa.Tags;
import dev.galasa.Test;
import dev.galasa.TestAreas;
import dev.galasa.galasaecosystem.IGenericEcosystem;
import dev.galasa.galasaecosystem.ILocalEcosystem;
import dev.galasa.galasaecosystem.LocalEcosystem;
import dev.galasa.inttests.sdv.AbstractSDVLocal;
import dev.galasa.java.JavaVersion;
import dev.galasa.java.ubuntu.IJavaUbuntuInstallation;
import dev.galasa.java.ubuntu.JavaUbuntuInstallation;
import dev.galasa.linux.ILinuxImage;
import dev.galasa.linux.LinuxImage;
import dev.galasa.linux.OperatingSystem;
import dev.galasa.sem.SemTopology;
import dev.galasa.zos.IZosImage;
import dev.galasa.zos.ZosImage;

@SemTopology
@Test
@TestAreas({"sdvManager","localecosystem","java11","ubuntu"})
@Tags({"codecoverage"})
public class SDVLocalJava11Ubuntu extends AbstractSDVLocal {

    @LocalEcosystem(linuxImageTag = "PRIMARY", addDefaultZosImage = "PRIMARY")
    public ILocalEcosystem ecosystem;
    
    @LinuxImage(operatingSystem = OperatingSystem.ubuntu)
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
