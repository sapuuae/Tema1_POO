package usage;

import base.User;
import base.Video;
import base.VideosWithGenresAndViews;
import fileio.ActionInputData;
import fileio.Writer;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public final class PopularRecommendation extends Recommendation {

    public PopularRecommendation(final ArrayList<Video> totalVideoList,
                                 final ActionInputData action,
                                 final ArrayList<User> userArrayList) {
        super(action, userArrayList, totalVideoList);
    }

    /**
     * Show the most popular video from that genre.
     * @param arrayResult the array used to write in file
     * @param fileWriter used for writing in file
     * @throws IOException check I/O
     */
    public void showPopular(final JSONArray arrayResult,
                            final Writer fileWriter) throws IOException {
        User theUser = null;
        for (User getTheName : getUserArrayList()) {
            if (getTheName.getUsername().equals(getAction().getUsername())
                    && getTheName.getUserSubType().equals("PREMIUM")) {
                theUser = getTheName;
                break;
            }
        }
        String recommendVideo = null;
        if (theUser != null) {
            HashMap<String, VideosWithGenresAndViews> theMap = new HashMap<>();
            for (Video v : getTotalVideoList()) {
                for (String s : v.getGenres()) {
                    if (theMap.containsKey(s)) {
                        VideosWithGenresAndViews getVideo = theMap.get(s);
                        getVideo.setNumberOfViews(
                                getVideo.getNumberOfViews() + v.getTotalNumberOfViewed());
                        getVideo.getVideoList().add(v);
                    } else {
                        VideosWithGenresAndViews toAdd =
                                new VideosWithGenresAndViews(s, v.getTotalNumberOfViewed());
                        toAdd.getVideoList().add(v);
                        theMap.put(s, toAdd);
                    }
                }
            }
            if (theMap.size() != 0) {
                ArrayList<VideosWithGenresAndViews> theVideos = new ArrayList<>(theMap.values());
                theVideos.sort((o1, o2) -> {
                    int c;
                    c = o2.getNumberOfViews().compareTo(o1.getNumberOfViews());
                    return c;
                });
                boolean ok = true;
                for (VideosWithGenresAndViews aVideo : theVideos) {
                    ArrayList<Video> getVideosWithGenres = aVideo.getVideoList();
                    if (ok) {
                        for (Video ss : getVideosWithGenres) {
                            if (!theUser.getHistory().containsKey(ss.getTitle())) {
                                recommendVideo = ss.getTitle();
                                ok = false;
                                break;
                            }
                        }
                    }
                }
            }
            if (recommendVideo != null) {
                // noinspection unchecked
                arrayResult.add(fileWriter.writeFile(getAction().getActionId(), "?",
                        "Popular" + "Recommendation " + "result: " + recommendVideo));
            } else {
                // noinspection unchecked
                arrayResult.add(fileWriter.writeFile(getAction().getActionId(), "?",
                        "Popular" + "Recommendation " + "cannot be applied!"));
            }
        } else {
                // noinspection unchecked
            arrayResult.add(fileWriter.writeFile(getAction().getActionId(), "?",
                    "Popular" + "Recommendation " + "cannot be applied!"));
        }
    }
}
