package tv.twitch.android.core.crashreporter;

import tv.twitch.android.mod.bridges.ResourcesManager;
import tv.twitch.android.mod.utils.Logger;
import tv.twitch.android.util.LogArg;
import tv.twitch.android.util.LogTag;


public class CrashReporter {
    /* ... */

    public enum LogLevel {/* ... */}

    /* ... */

    public final void log(int i) { // TODO: __REPLACE_METHOD
        Logger.debug(ResourcesManager.INSTANCE.getStringById(i));
    }

    public final void log(int i, LogArg... logArgArr) { // TODO: __REPLACE_METHOD
        if (logArgArr == null) {
            Logger.warning("logArgArr is null");
            return;
        }

        Logger.debug(String.format(ResourcesManager.INSTANCE.getStringById(i), (Object[]) logArgArr));
    }

    public final void log(LogLevel logLevel, LogTag logTag, int i, LogArg... logArgArr) { // TODO: __REPLACE_METHOD
        if (logArgArr == null) {
            Logger.warning("logArgArr is null");
            return;
        }

        Logger.debug("logLevel=" + getString(logLevel) + ", logTag=" + getString(logTag) + String.format(ResourcesManager.INSTANCE.getStringById(i), (Object[]) logArgArr));
    }

    public final void logException(Throwable th) { // TODO: __REPLACE_METHOD
        Logger.debug(getString(th));
    }

    public final void nonFatal(int i, LogArg... logArgArr) { // TODO: __REPLACE_METHOD
        if (logArgArr == null) {
            Logger.warning("logArgArr is null");
            return;
        }

        Logger.debug(String.format(ResourcesManager.INSTANCE.getStringById(i), (Object[]) logArgArr));
    }

    public final void setBool(String str, boolean z) { // TODO: __REPLACE_METHOD
        Logger.debug("str=" + getString(str) + ", z=" + z);
    }

    public final void setPlayingItem(String str, String str2, String str3) { // TODO: __REPLACE_METHOD
        Logger.debug("str=" + getString(str) + ", str2" + str2 + ", str3" + str3);
    }

    public final void setString(String str, String str2) { // TODO: __REPLACE_METHOD
        Logger.debug("str=" + getString(str) + ", str2" + str2);
    }

    public final void setUserIdentifierForDebugBuilds(String str) { // TODO: __REPLACE_METHOD
        Logger.debug("str=" + getString(str));
    }

    public final void setUserNameForDebugBuilds(String str) { // TODO: __REPLACE_METHOD
        Logger.debug("str=" + getString(str));
    }

    public final void e(LogTag logTag, int i, LogArg... logArgArr) { // TODO: __REPLACE_METHOD
        if (logArgArr == null) {
            Logger.warning("logArgArr is null");
            return;
        }

        Logger.debug("logTag=" + getString(logTag) + String.format(ResourcesManager.INSTANCE.getStringById(i), (Object[]) logArgArr));
    }

    /* ... */

    private static String getStackTraceLine(StackTraceElement[] stackTraceElements) { // TODO: __INJECT_METHOD
        StringBuilder sb = new StringBuilder();
        if (stackTraceElements != null) {
            for (StackTraceElement element : stackTraceElements) {
                if (element == null)
                    sb.append("->...");
                else
                    sb.append("->").append(element.toString());
            }
        }
        return sb.toString();
    }

    private static String getString(Object o) { // TODO: __INJECT_METHOD
        if (o == null)
            return "null";

        if (o instanceof Throwable) {
            return getStackTraceLine(((Throwable) o).getStackTrace());
        }

        return o.toString();
    }
}
