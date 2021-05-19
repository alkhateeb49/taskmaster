package com.example.taskmaster;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM tasks")
    List<Tasks> getAll();

    @Query("SELECT * FROM tasks WHERE id IN (:tasksIds)")
    List<Tasks> loadAllByIds(int[] tasksIds);

    @Query("SELECT * FROM tasks WHERE title LIKE :title LIMIT 1")
    Tasks findByName(String title);

    @Insert
    void insertAll(Tasks... tasks);

    @Delete
    void delete(Tasks tasks);

}
