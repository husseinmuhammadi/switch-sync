package com.dpi.financial.ftcom.security.base;

import com.dpi.financial.ftcom.security.api.module.SecurityModule;
import com.dpi.financial.ftcom.security.module.HardwareSecurityModule;

public abstract class SecurityBase {
    private SecurityModule module;

    public SecurityBase() {
        module = new HardwareSecurityModule();
    }
}
