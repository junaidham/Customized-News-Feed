package tech.ducletran.customizednewsfeedapp.OtherResource;

public class MusicTrack {
    private String trackName;
    private String artist;
    private String webURL;
    private String thumbnail;

    public MusicTrack(String trackName,String artist,String webURL, String thumbnail) {
        this.artist = artist;
        this.trackName = trackName;
        this.webURL = webURL;
        this.thumbnail = thumbnail;
    }

    public String getArtist() {
        return artist;
    }

    public String getTrackName() {
        return trackName;
    }

    public String getWebURL() {
        return webURL;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
