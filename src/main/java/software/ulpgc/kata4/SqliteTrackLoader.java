package software.ulpgc.kata4;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqliteTrackLoader implements TrackLoader{
    private final Connection connection;
    private final static String querySql = "SELECT tracks.Name as track, albums.Title as album, genres.name as genre, tracks.composer as composer, tracks.Milliseconds as miliseconds, tracks.UnitPrice as price\n" +
            "FROM tracks, albums, genres\n" +
            "WHERE albums.AlbumId = tracks.AlbumId AND\n" +
            "\t  genres.GenreId = tracks.GenreId";

    public SqliteTrackLoader(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Track> load() {
        try {
            return load(resultOf(querySql));
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    private List<Track> load(ResultSet resultSet) throws SQLException {
        List<Track> result = new ArrayList<>();
        while(resultSet.next()) result.add(trackFrom(resultSet));
        return result;
    }

    private Track trackFrom(ResultSet resultSet) throws SQLException {
        return new Track(
                resultSet.getString("track"),
                resultSet.getString("album"),
                resultSet.getString("genre"),
                resultSet.getString("composer"),
                resultSet.getInt("miliseconds"),
                resultSet.getDouble("price")

        );
    }

    private ResultSet resultOf(String querySql) throws SQLException {
        return connection.createStatement().executeQuery(querySql);
    }
}
