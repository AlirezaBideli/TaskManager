package com.example.admin.software_1.models;

import android.app.AlertDialog;
import android.util.Log;

import com.example.admin.software_1.R;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.io.Serializable;
import java.util.UUID;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;


@Entity()
public class Task {


    //Change optional variables to "Undefined" when these variables are "null"

    @Id(autoincrement = true)
    private Long _id;
    @Convert(converter = UUIDConverter.class, columnType = String.class)
    private UUID uuId;
    private String title;
    private String description = "Undefined";
    private String date = "Undefined";
    private String time = "Undefined";
    @Convert(converter = TaskTypeConverter.class, columnType = Integer.class)
    private TaskType taskType;
    private Long user_id;
    @ToOne(joinProperty = "user_id")
    private User user;
    private String imagePath;

    public String getPictureName() {

        return "IMG_"+getUser_id()+getUuId()+".jpg";
    }


    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1469429066)
    private transient TaskDao myDao;
    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;

    @Generated(hash = 1400693316)
    public Task(Long _id, UUID uuId, String title, String description, String date,
            String time, TaskType taskType, Long user_id, String imagePath) {
        this._id = _id;
        this.uuId = uuId;
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.taskType = taskType;
        this.user_id = user_id;
        this.imagePath = imagePath;
    }

    @Generated(hash = 733837707)
    public Task() {
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public UUID getUuId() {
        return this.uuId;
    }

    public void setUuId(UUID uuId) {
        this.uuId = uuId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public TaskType getTaskType() {
        return this.taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public Long getUser_id() {
        return this.user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    /**
     * To-one relationship, resolved on first access.
     */
    @Generated(hash = 2126679178)
    public User getUser() {
        Long __key = this.user_id;
        if (user__resolvedKey == null || !user__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User userNew = targetDao.load(__key);
            synchronized (this) {
                user = userNew;
                user__resolvedKey = __key;
            }
        }
        return user;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 972586207)
    public void setUser(User user) {
        synchronized (this) {
            this.user = user;
            user_id = user == null ? null : user.get_id();
            user__resolvedKey = user_id;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1442741304)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTaskDao() : null;
    }


    public static class TaskTypeConverter implements PropertyConverter<TaskType, Integer> {

        @Override
        public TaskType convertToEntityProperty(Integer databaseValue) {
            for (TaskType taskType : TaskType.values()) {
                if (taskType.getValue() == databaseValue)
                    return taskType;
            }
            return null;
        }

        @Override
        public Integer convertToDatabaseValue(TaskType entityProperty) {
            return entityProperty.getValue();
        }
    }

    public static class UUIDConverter implements PropertyConverter<UUID, String> {

        @Override
        public UUID convertToEntityProperty(String databaseValue) {
            return UUID.fromString(databaseValue);
        }

        @Override
        public String convertToDatabaseValue(UUID entityProperty) {
            return entityProperty.toString();
        }
    }

    public enum TaskType {
        ALL(0),
        DONE(1),
        UNDONE(2);

        TaskType(int value) {
            this.value = value;
        }


        private int value;

        public int getValue() {

            return value;
        }

        public static TaskType getTaskType(int value) {
            switch (value) {
                case 0:
                    return Task.TaskType.ALL;
                case 1:
                    return Task.TaskType.DONE;
                default:
                    return Task.TaskType.UNDONE;
            }
        }


    }


}
