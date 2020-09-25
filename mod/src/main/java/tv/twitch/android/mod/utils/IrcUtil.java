package tv.twitch.android.mod.utils;


import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class IrcUtil {
    private static final String COMMAND_ROOMSTATE = "ROOMSTATE";
    private static final String COMMAND_PRIVMSG = "PRIVMSG";
    private static final String COMMAND_NOTICE = "NOTICE";
    private static final String COMMAND_CLEARCHAT = "CLEARCHAT";
    private static final String COMMAND_USERNOTICE = "USERNOTICE";


    static HashMap<String, String> parseIrcTags(String part) {
        if (TextUtils.isEmpty(part)) {
            Logger.debug("empty part");
            return null;
        }

        if (!part.startsWith("@")) {
            Logger.debug("bad part: " + part);
            return null;
        }

        HashMap<String, String> tags = new HashMap<>();

        for (String t : part.substring(1).split(";")) {
            String[] tParts = t.split("=", 2);
            if (tParts.length != 2)
                continue;
            if (TextUtils.isEmpty(tParts[0]))
                continue;

            tags.put(tParts[0], tParts[1]);
        }

        return tags;
    }

    static String formatPrivMsg(String[] parts) {
        if (parts == null)
            return null;

        HashMap<String, String> tags = parseIrcTags(parts[0]);
        if (tags == null) {
            Logger.debug("no tags");
            return null;
        }

        if (TextUtils.isEmpty(parts[4])) {
            Logger.debug("no content");
            return null;
        }

        String content = getContent(parts[4]);

        StringBuilder stringBuilder = new StringBuilder();

        String time = getSendTime(tags);
        if (time != null)
            stringBuilder.append(time).append(" ");

        String username = getUsername(tags);
        if (username != null)
            stringBuilder.append(username).append(": ");

        stringBuilder.append(content);

        return stringBuilder.toString();
    }

    static String formatNotice(String[] parts) {
        if (parts == null)
            return null;

        HashMap<String, String> tags = parseIrcTags(parts[0]);
        if (tags == null) {
            Logger.debug("no tags");
            return null;
        }

        if (TextUtils.isEmpty(parts[4])) {
            Logger.debug("no content");
            return null;
        }

        String content = getContent(parts[4]);

        StringBuilder stringBuilder = new StringBuilder();

        String time = getSendTime(tags);
        if (time != null)
            stringBuilder.append(time).append(" ");

        stringBuilder.append(content);

        return stringBuilder.toString();
    }

    static String getContent(String c) {
        if (TextUtils.isEmpty(c))
            return null;

        if (c.startsWith(":"))
            return c.substring(1);

        return c;
    }

    private static String getUsername(HashMap<String, String> tags) {
        if (tags == null)
            return null;

        String tagVal = tags.get("display-name");
        if (tagVal == null || TextUtils.isEmpty(tagVal))
            return null;

        return tagVal;
    }

    static String getSendTime(HashMap<String, String> tags) {
        if (tags == null)
            return null;

        String tagVal = tags.get("rm-received-ts");
        if (tagVal == null || TextUtils.isEmpty(tagVal))
            return null;

        long time = Long.parseLong(tagVal);

        return new SimpleDateFormat("HH:mm", Locale.UK).format(new Date(time));
    }

    private static int getBanDuration(HashMap<String, String> tags) {
        if (tags == null)
            return 0;

        String tagVal = tags.get("ban-duration");
        if (tagVal == null || TextUtils.isEmpty(tagVal))
            return -1;

        return Integer.parseInt(tagVal);
    }

    static String formatClearchat(String[] parts) {
        if (parts == null)
            return null;

        HashMap<String, String> tags = parseIrcTags(parts[0]);
        if (tags == null) {
            Logger.debug("no tags");
            return null;
        }

        if (TextUtils.isEmpty(parts[4])) {
            Logger.debug("no username");
            return null;
        }

        String username = getContent(parts[4]);

        StringBuilder stringBuilder = new StringBuilder();

        String time = getSendTime(tags);
        if (time != null)
            stringBuilder.append(time).append(" ");

        int banDuration = getBanDuration(tags);

        stringBuilder.append(username);
        if (banDuration == -1) {
            stringBuilder.append(" has been permanently banned");
        } else {
            if (banDuration > 0) {
                stringBuilder.append(" has been timed out for ").append(banDuration).append(" seconds");
            } else {
                stringBuilder.append(" has been timed out");
                Logger.debug("banDuration=" + banDuration);
            }
        }

        return stringBuilder.toString();
    }

    public static String formatRoomstate(String[] parts) {
        if (parts == null)
            return null;

        HashMap<String, String> tags = parseIrcTags(parts[0]);
        if (tags == null) {
            Logger.debug("no tags");
            return null;
        }

        return null;
    }

    public static String formatIrcMessage(String line) {
        if (TextUtils.isEmpty(line))
            return null;

        String[] parts = line.split(" ", 5);
        if (parts.length < 4) {
            Logger.debug("Bad line: " + line);
            return null;
        }

        if (TextUtils.isEmpty(parts[2])) {
            Logger.debug("empty command part");
            return null;
        }

        String command = parts[2];

        switch (parts.length) {
            case 4:
                switch (command) {
                    case COMMAND_ROOMSTATE:
                        return formatRoomstate(parts);
                    case COMMAND_USERNOTICE:
                        return formatUsernotice(parts);
                    default:
                        Logger.debug("Bad line: " + line);
                        return null;
                }

            case 5:
                switch (command) {
                    case COMMAND_PRIVMSG:
                        return formatPrivMsg(parts);
                    case COMMAND_NOTICE:
                        return formatNotice(parts);
                    case COMMAND_CLEARCHAT:
                        return formatClearchat(parts);
                    case COMMAND_USERNOTICE:
                        return formatUsernotice(parts);
                    default:
                        Logger.debug("Bad line: " + line);
                        return null;
                }

            default:
                Logger.debug("Bad line: " + line);
                return null;
        }
    }

    private static String formatUsernotice(String[] parts) {
        if (parts == null)
            return null;

        HashMap<String, String> tags = parseIrcTags(parts[0]);
        if (tags == null) {
            Logger.debug("no tags");
            return null;
        }

        String systemMsg = tags.get("system-msg");
        if (systemMsg == null || TextUtils.isEmpty(systemMsg))
            return null;

        StringBuilder stringBuilder = new StringBuilder();

        String time = getSendTime(tags);
        if (time != null)
            stringBuilder.append(time).append(" ");

        stringBuilder.append(systemMsg.replace("\\s", " "));

        return stringBuilder.toString();
    }
}
