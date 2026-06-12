# HookSim

HookSim is a small LSPosed module for learning and testing modern libxposed API 101 hooks.

It hooks:

```text
android.telephony.TelephonyManager#getSimCountryIso()
```

and returns:

```text
JP
```

## Notes

- This module uses libxposed API 101, not API 100.
- The hook is installed with the API 101 interceptor-chain model.
- Build is intended to run on GitHub Actions, not locally.
- The generated release APK is debug-key signed for learning and test installation.

## Usage

1. Install the APK on a device with LSPosed API 101 support.
2. Enable the HookSim module in LSPosed.
3. Add the app you want to test to the module scope.
4. Force stop and reopen the target app.
5. Calls to `TelephonyManager#getSimCountryIso()` in the scoped process should return `JP`.

The default recommended scope includes `com.android.settings`, but you can add any test app manually in LSPosed because `staticScope=false`.
