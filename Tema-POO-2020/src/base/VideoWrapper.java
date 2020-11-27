package base;

/*
/*
Used to modify a video in command class.
 */
public final class VideoWrapper {
    private Video interiorVideo;

    public VideoWrapper() { }

    public void setInteriorVideo(final Video interiorVideo) {
        this.interiorVideo = interiorVideo;
    }

    public Video getInteriorVideo() {
        return interiorVideo;
    }
}
