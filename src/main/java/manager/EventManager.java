package manager;

import db.DBConnectionProvider;
import model.Event;
import model.EventType;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private Connection connection = DBConnectionProvider.getInstance().getConnection();

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void add(Event event){
        String sql = "insert into event(name, place, is_online, event_type, price, event_date) values (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, event.getName());
            preparedStatement.setString(2, event.getPlace());
            preparedStatement.setBoolean(3, event.isOnline());
            preparedStatement.setString(4, event.getEventType().name());
            preparedStatement.setDouble(5, event.getPrice());
            preparedStatement.setString(6, sdf.format(event.getEventDate()));
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                int id = resultSet.getInt(1);
                event.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Event> getAll(){
        String sql = "select * from event";
        List<Event> events = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                events.add(getEventFromResultSet(resultSet));
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return events;
    }

    public Event getById(int id){
        String sql = "select * from event where id = " + id;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()){
                return getEventFromResultSet(resultSet);
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Event getEventFromResultSet(ResultSet resultSet) throws SQLException, ParseException {
        return Event.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .place(resultSet.getString("place"))
                .isOnline(resultSet.getBoolean("is_online"))
                .eventType(EventType.valueOf(resultSet.getString("event_type")))
                .price(resultSet.getDouble("price"))
                .eventDate(resultSet.getString("event_date") == null ? null : sdf.parse(resultSet.getString("event_date")))
                .build();
    }


}
