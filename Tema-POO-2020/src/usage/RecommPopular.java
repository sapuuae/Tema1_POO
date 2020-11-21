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

public final class RecommPopular {
    private final ArrayList<Video> totalVideoList;
    private final ActionInputData action;
    private final ArrayList<User> userArrayList;

    public RecommPopular(final ArrayList<Video> totalVideoList,
                         final ActionInputData action,
                         final ArrayList<User> userArrayList) {
        this.totalVideoList = totalVideoList;
        this.action = action;
        this.userArrayList = userArrayList;
    }

    public void showPopular(final JSONArray arrayResult,
                            final Writer fileWriter) throws IOException {
        User theUser = null;
        for (User getTheName : userArrayList) {
            if (getTheName.getUsername().equals(action.getUsername())
                    && getTheName.getUserSubType().equals("PREMIUM")) {
                theUser = getTheName;
                break;
            }
        }
        String recommendVideo = null;
        if (theUser != null) {
            HashMap<String, VideosWithGenresAndViews> theMap = new HashMap<>();
            for (Video v : totalVideoList) {
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
                arrayResult.add(fileWriter.writeFile(action.getActionId(), "?",
                        "Popular" + "Recommendation " + "result: " + recommendVideo));
            } else {
                arrayResult.add(fileWriter.writeFile(action.getActionId(), "?",
                        "Popular" + "Recommendation " + "cannot be applied!"));
            }
        } else {
            arrayResult.add(fileWriter.writeFile(action.getActionId(), "?",
                    "Popular" + "Recommendation " + "cannot be applied!"));
        }
    }
}
