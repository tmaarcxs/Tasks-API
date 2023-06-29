package com.marcos.API.Controller;

import com.google.gson.Gson;
import com.marcos.API.Model.Task.Task;
import com.marcos.API.Model.Task.TaskDAO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.System.out;

@RestController
@RequestMapping("/tasks")
public class C_Tasks {

    @GetMapping("/all")
    public String tasks() {
        try {
            TaskDAO taskDAO = new TaskDAO();
            String SQL_MODIFIER = "";
            List<Task> tasks = taskDAO.getAll(SQL_MODIFIER);
            String taskListJSON = Task.toArrayJson(tasks);
            out.println(taskListJSON);
            return taskListJSON;
        } catch (SQLException e) {
            Logger.getLogger(C_Tasks.class.getName()).log(Level.SEVERE, null, e);
            return "An error occurred.";
        }
    }

    @GetMapping("/{id}")
    public String tasks(@PathVariable int id) {
        try {
            TaskDAO taskDAO = new TaskDAO();
            String SQL_MODIFIER = "WHERE id = " + id;
            List<Task> tasks = taskDAO.getAll(SQL_MODIFIER);
            String taskListJSON = Task.toArrayJson(tasks);
            out.println(taskListJSON);
            return taskListJSON;
        } catch (SQLException e) {
            Logger.getLogger(C_Tasks.class.getName()).log(Level.SEVERE, null, e);
            return "An error occurred.";
        }
    }

    // insert task
    @PostMapping("/insert")
    public ResponseEntity<String> insertTask(@RequestBody String taskJSON) {
        try {
            Gson gson = new Gson();
            System.out.println(taskJSON);
            Task task = gson.fromJson(taskJSON, Task.class);
            if (task.getTitle() == null && task.getDescription() == null || Objects.equals(task.getTitle(), "") && Objects.equals(task.getDescription(), "")) {
                return ResponseEntity.ok("Cannot insert task with null values.");
            }
            TaskDAO taskDAO = new TaskDAO();
            if (taskDAO.insertTask(task)) {
                return ResponseEntity.ok("Task inserted successfully.");
            } else {
                return ResponseEntity.ok("Failed to insert task.");
            }
        } catch (SQLException e) {
            Logger.getLogger(C_Tasks.class.getName()).log(Level.SEVERE, null, e);
            return ResponseEntity.ok("An error occurred.");
        }
    }

    // update task
    @PostMapping("/update")
    public ResponseEntity<String> updateTask(@RequestBody String taskJSON) {
        try {
            Gson gson = new Gson();
            System.out.println(taskJSON);
            Task task = gson.fromJson(taskJSON, Task.class);
            if (task.getTitle() == null && task.getDescription() == null || Objects.equals(task.getTitle(), "") && Objects.equals(task.getDescription(), "")) {
                return ResponseEntity.ok("Cannot update task with null values.");
            }
            TaskDAO taskDAO = new TaskDAO();
            if (taskDAO.updateTask(task)) {
                return ResponseEntity.ok("Task updated successfully.");
            } else {
                return ResponseEntity.ok("Failed to update task.");
            }
        } catch (SQLException e) {
            Logger.getLogger(C_Tasks.class.getName()).log(Level.SEVERE, null, e);
            return ResponseEntity.ok("An error occurred.");
        }
    }

    // delete task
    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable int id) {
        try {
            TaskDAO taskDAO = new TaskDAO();
            if (taskDAO.deleteTask(id)) {
                return ResponseEntity.ok("Task deleted successfully.");
            } else {
                return ResponseEntity.ok("Failed to delete task.");
            }
        } catch (SQLException e) {
            Logger.getLogger(C_Tasks.class.getName()).log(Level.SEVERE, null, e);
            return ResponseEntity.ok("An error occurred.");
        }
    }
}
