package software.ulpgc.kata4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:chinook.db");
            TrackLoader trackLoader = new SqliteTrackLoader(connection);
            List<Track> tracksList = trackLoader.load();

            Map<String, Integer> trackMapByAlbum = new HashMap<>();
            for (Track track : tracksList) {
                trackMapByAlbum.put(track.album(), trackMapByAlbum.getOrDefault(track.album(), 0)+1);
            }

            for(String album : trackMapByAlbum.keySet()) {
                System.out.println(album + " = " + trackMapByAlbum.get(album));
            }
            System.out.println("Finalizada kata4");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
