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


    private static HashMap<String, String> parseIrcTags(String part) {
        if (part == null || part.length() == 0)
            return null;

        if (!part.startsWith("@"))
            return null;

        part = part.substring(1);

        HashMap<String, String> tags = new HashMap<>();

        for (String t : part.split(";")) {
            String[] pair = t.split("=", 2);

            if (pair.length != 2)
                continue;

            if (pair[0] == null || pair[0].length() == 0)
                continue;

            if (pair[1] == null || pair[1].length() == 0)
                continue;

            tags.put(pair[0], pair[1]);
        }

        return tags;
    }

    private static String formatPrivMsg(String[] parts) {
        if (parts == null)
            return null;

        if (parts.length != 5)
            return null;

        HashMap<String, String> tags = parseIrcTags(parts[0]);
        if (tags == null)
            return null;

        if (parts[4] == null || parts[4].length() == 0)
            return null;

        String content = getContent(parts[4]);

        StringBuilder msgBuilder = new StringBuilder();

        String time = getSendTime(tags);
        if (time != null)
            msgBuilder.append(time).append(" ");

        String username = getUsername(tags);
        if (username != null)
            msgBuilder.append(username).append(": ");

        msgBuilder.append(content);

        return msgBuilder.toString();
    }

    private static String formatNotice(String[] parts) {
        if (parts == null)
            return null;

        if (parts.length != 5)
            return null;

        HashMap<String, String> tags = parseIrcTags(parts[0]);
        if (tags == null)
            return null;

        if (parts[4] == null || parts[4].length() == 0)
            return null;

        String content = getContent(parts[4]);

        StringBuilder msgBuilder = new StringBuilder();

        String time = getSendTime(tags);
        if (time != null)
            msgBuilder.append(time).append(" ");

        msgBuilder.append(content);

        return msgBuilder.toString();
    }

    private static String getContent(String part) {
        if (part == null || part.length() == 0)
            return null;

        if (part.startsWith(":"))
            return part.substring(1);

        return part;
    }

    private static String getUsername(HashMap<String, String> tags) {
        if (tags == null)
            return null;

        String displayName = tags.get("display-name");
        if (displayName == null || displayName.length() == 0)
            return null;

        return displayName;
    }

    private static String getSendTime(HashMap<String, String> tags) {
        if (tags == null)
            return null;

        String receivedTime = tags.get("rm-received-ts");
        if (receivedTime == null || receivedTime.length() == 0)
            return null;

        long time = Long.parseLong(receivedTime);

        return new SimpleDateFormat("HH:mm", Locale.UK).format(new Date(time));
    }

    private static int getBanDuration(HashMap<String, String> tags) {
        if (tags == null)
            return 0;

        String duration = tags.get("ban-duration");
        if (duration == null || duration.length() == 0)
            return -1;

        return Integer.parseInt(duration);
    }

    private static String formatClearchat(String[] parts) {
        if (parts == null)
            return null;

        if (parts.length != 5)
            return null;

        HashMap<String, String> tags = parseIrcTags(parts[0]);
        if (tags == null)
            return null;

        if (parts[4] == null || parts[4].length() == 0)
            return null;

        String username = getContent(parts[4]);

        StringBuilder msgBuilder = new StringBuilder();

        String time = getSendTime(tags);
        if (time != null)
            msgBuilder.append(time).append(" ");

        int banDuration = getBanDuration(tags);

        msgBuilder.append(username);

        if (banDuration == -1) {
            msgBuilder.append(" has been permanently banned");
        } else {
            if (banDuration > 0) {
                msgBuilder.append(" has been timed out for ").append(banDuration).append(" seconds");
            } else {
                msgBuilder.append(" has been timed out");
            }
        }

        return msgBuilder.toString();
    }

    private static String formatRoomstate(String[] parts) {
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
        if (line == null || line.length() == 0)
            return null;

        String[] parts = line.split(" ", 5);
        if (parts.length < 3) {
            Logger.debug("Bad line: " + line);
            return null;
        }

        if (parts[2] == null || parts[2].length() == 0) {
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
        if (tags == null)
            return null;

        String systemMsg = tags.get("system-msg");
        if (systemMsg == null || TextUtils.isEmpty(systemMsg))
            return null;

        StringBuilder msgBuilder = new StringBuilder();

        String time = getSendTime(tags);
        if (time != null)
            msgBuilder.append(time).append(" ");

        msgBuilder.append(systemMsg.replace("\\s", " "));

        return msgBuilder.toString();
    }
}
