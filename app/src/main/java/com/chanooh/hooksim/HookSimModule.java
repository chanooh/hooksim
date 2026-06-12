package com.chanooh.hooksim;

import android.util.Log;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;

import io.github.libxposed.api.XposedInterface;
import io.github.libxposed.api.XposedModule;
import io.github.libxposed.api.XposedModuleInterface.ModuleLoadedParam;

public final class HookSimModule extends XposedModule {
    private static final String TAG = "HookSim";
    private static final String FORCED_COUNTRY_ISO = "JP";

    private final AtomicBoolean telephonyHookInstalled = new AtomicBoolean(false);

    @Override
    public void onModuleLoaded(ModuleLoadedParam param) {
        installTelephonyHook();
    }

    private void installTelephonyHook() {
        if (!telephonyHookInstalled.compareAndSet(false, true)) {
            return;
        }

        try {
            Class<?> telephonyManagerClass = Class.forName("android.telephony.TelephonyManager");
            Method getSimCountryIso = telephonyManagerClass.getDeclaredMethod("getSimCountryIso");

            hook(getSimCountryIso)
                    .setPriority(XposedInterface.PRIORITY_HIGHEST)
                    .intercept(chain -> FORCED_COUNTRY_ISO);

            log(Log.INFO, TAG, "Hooked TelephonyManager#getSimCountryIso() -> " + FORCED_COUNTRY_ISO);
        } catch (Throwable throwable) {
            telephonyHookInstalled.set(false);
            log(Log.ERROR, TAG, "Failed to hook TelephonyManager#getSimCountryIso()", throwable);
        }
    }
}
