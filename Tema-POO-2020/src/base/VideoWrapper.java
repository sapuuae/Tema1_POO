package base;

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
