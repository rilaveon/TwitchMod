package tv.twitch.android.core.crashreporter;

import android.text.TextUtils;
import android.util.Log;


public class CrashReporter {
    private void test() {
        LogLine("method1", "KEKW");
        LogWithKey("method2", "KEKW", "LULW");
        LogMessage("method3", 0, "KEKW", "LULW");
        LogThrowable("method4", new Exception());
        LogWithKeyAndBool("method5", "KEKW", true);
    }

    private static void LogLine(String calledFrom, String what) {
        what = format(what);

        Log(calledFrom, "what==" + what);
    }

    private static void LogWithKey(String calledFrom, String key, String what) {
        what = format(what);
        key = format(key);

        Log(calledFrom, "key==" + key + ", what==" + what);
    }

    private static void LogWithKeyAndBool(String calledFrom, String key, boolean z) {
        key = format(key);

        Log(calledFrom, "what==" + key + ", z==" + z);
    }

    private static void LogThrowable(String calledFrom, Throwable throwable) {
        Log(calledFrom, "throwable==" + throwable != null ? getStackTraceLine(throwable.getStackTrace()) : "null");
    }

    private static void LogMessage(String calledFrom, int i, String tag, String what) {
        what = format(what);
        tag = format(tag);

        Log(calledFrom, "i==" + i + ", tag==" + tag + ", what==" + what);
    }

    private static void Log(String calledFrom, String message) {
        if (TextUtils.isEmpty(message)) {
            Log.e("CRASHLYTICS", "<empty>");
            return;
        }

        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(calledFrom))
            sb.append("method==").append(calledFrom).append(", ");

        sb.append(message);

        Log.d("CRASHLYTICS", sb.toString());
        new Object();
    }

    private static String format(String what) {
        if (what == null)
            return "null";
        else if (what.length() == 0)
            return "<empty>";

        return what;
    }

    private static String getStackTraceLine(StackTraceElement[] stackTraceElements) {
        StringBuilder sb = new StringBuilder();
        if (stackTraceElements != null) {
            for (StackTraceElement element : stackTraceElements) {
                if (element == null)
                    sb.append("->...");
                else
                    sb.append("->").append(element.toString());
            }
        }
        return sb.append(";").toString();
    }
}
