package tv.twitch.android.shared.player.parsers.extm3u.raw;

import java.util.ArrayList;

public class StreamInfo {
    public ArrayList<Attribute> Attributes;
    public String Audio;
    public Integer Bandwidth;
    public String Codecs;
    public Integer ProgramID;
    public String Resolution;
    public String Subtitles;
    public String Uri = "";
    public String Video;
}