package tv.twitch.android.core.crashreporter;


import tv.twitch.android.mod.utils.Logger;


public class CrashReporter {
    /* ... */

    public enum LogLevel {/* ... */}

    /* ... */

    private final void log(String msg) { // TODO: __REPLACE_METHOD
        Logger.debug(msg);
    }

    private final void log(int i, String str1, String str2) { // TODO: __REPLACE_METHOD
        Logger.debug("i=" + i + ", str1=" + str1 + ", str=" + str2);
    }

    public final void logException(Throwable th) { // TODO: __REPLACE_METHOD
        Logger.debug(getString(th));
    }

    public final void setBool(String str, boolean z) { // TODO: __REPLACE_METHOD
        Logger.debug("str=" + getString(str) + ", z=" + z);
    }

    public final void setString(String str, String str2) { // TODO: __REPLACE_METHOD
        Logger.debug("str=" + getString(str) + ", str2=" + str2);
    }

    public final void setUserIdentifierForDebugBuilds(String str) { // TODO: __REPLACE_METHOD
        Logger.debug("str=" + getString(str));
    }

    public final void setUserNameForDebugBuilds(String str) { // TODO: __REPLACE_METHOD
        Logger.debug("str=" + getString(str));
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
