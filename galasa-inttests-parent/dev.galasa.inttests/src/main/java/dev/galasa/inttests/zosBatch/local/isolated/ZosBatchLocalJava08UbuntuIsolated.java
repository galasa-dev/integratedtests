package dev.galasa.inttests.zosBatch.local.isolated;

import dev.galasa.Test;
import dev.galasa.TestAreas;
import dev.galasa.galasaecosystem.IGenericEcosystem;
import dev.galasa.galasaecosystem.ILocalEcosystem;
import dev.galasa.galasaecosystem.IsolationInstallation;
import dev.galasa.galasaecosystem.LocalEcosystem;
import dev.galasa.inttests.zosBatch.AbstractZosBatchLocal;
import dev.galasa.java.JavaVersion;
import dev.galasa.java.ubuntu.IJavaUbuntuInstallation;
import dev.galasa.java.ubuntu.JavaUbuntuInstallation;
import dev.galasa.linux.ILinuxImage;
import dev.galasa.linux.LinuxImage;
import dev.galasa.linux.OperatingSystem;
import dev.galasa.zos.IZosImage;
import dev.galasa.zos.ZosImage;

@Test
@TestAreas({"zosManager","localecosystem","java08","ubuntu","mvp"})
public class ZosBatchLocalJava08UbuntuIsolated extends AbstractZosBatchLocal {

    @LocalEcosystem(linuxImageTag = "PRIMARY", isolationInstallation = IsolationInstallation.Full, addDefaultZosImage = "PRIMARY")
    public ILocalEcosystem ecosystem;
    
    @LinuxImage(operatingSystem = OperatingSystem.ubuntu, capabilities = "isolated")
    public ILinuxImage linuxImage;
    
    @JavaUbuntuInstallation(javaVersion = JavaVersion.v8)
    public IJavaUbuntuInstallation java;
    
    @ZosImage
    public IZosImage zosImage;

    @Override
    protected IGenericEcosystem getEcosystem() {
        return this.ecosystem;
    }

}
