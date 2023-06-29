package com.marcos.API.Model.Task;

import com.marcos.API.Model.FactoryConnection;
import com.marcos.API.Model.Motor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {
    private final Motor motorSQL;

    public TaskDAO() {
        this.motorSQL = FactoryConnection.getConnection("postgresql");
    }

    public List<Task> getAll(String SQL_MODIFIER) throws SQLException {
        return getTasks(SQL_MODIFIER);
    }

    private List<Task> getTasks(String SQL_MODIFIER) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String SQL_SELECT = SQL_SELECT_BUILDER(SQL_MODIFIER);
        motorSQL.connect();
        ResultSet rs = motorSQL.executeQuery(SQL_SELECT);
        if (rs != null) {
            while (rs.next()) {
                Task task = createTaskFromResultSet(rs);
                tasks.add(task);
            }
        }
        motorSQL.disconnect();
        return tasks;
    }

    public boolean insertTask(Task task) throws SQLException {
        String SQL_INSERT = SQL_INSERT_BUILDER("VALUES ('" + task.getTitle() + "', '" + task.getDescription() + "')");
        motorSQL.connect();
        int res = motorSQL.executeUpdate(SQL_INSERT);
        motorSQL.disconnect();
        return res == 1;
    }

    public boolean updateTask(Task task) throws SQLException {
        String SQL_UPDATE = buildUpdateStatement(task);
        motorSQL.connect();
        int res = motorSQL.executeUpdate(SQL_UPDATE);
        motorSQL.disconnect();
        return res > 0;
    }

    public boolean deleteTask(int taskId) throws SQLException {
        String SQL_DELETE = "DELETE FROM tasks WHERE id = " + taskId;
        motorSQL.connect();
        int res = motorSQL.executeUpdate(SQL_DELETE);
        motorSQL.disconnect();
        return res > 0;
    }

    private Task createTaskFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String description = rs.getString("description");
        return new Task(id, title, description);
    }

    private String SQL_SELECT_BUILDER(String SQL_MODIFIER) {
        return "SELECT * FROM tasks " + SQL_MODIFIER;
    }

    private String SQL_INSERT_BUILDER(String SQL_MODIFIER) {
        return "INSERT INTO tasks (title, description) " + SQL_MODIFIER;
    }

    private String buildUpdateStatement(Task task) {
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE tasks SET ");

        List<String> fieldsToUpdate = new ArrayList<>();
        if (task.getTitle() != null) {
            fieldsToUpdate.add("title = '" + task.getTitle() + "'");
        }
        if (task.getDescription() != null) {
            fieldsToUpdate.add("description = '" + task.getDescription() + "'");
        }

        builder.append(String.join(", ", fieldsToUpdate));
        builder.append(" WHERE id = ").append(task.getId());
        return builder.toString();
    }
}