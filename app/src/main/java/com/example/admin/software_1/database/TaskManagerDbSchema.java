package com.example.admin.software_1.database;

/**
 * Created by ADMIN on 1/2/2019.
 */

public class TaskManagerDbSchema {
    public static final String NAME = "TaskManager";
    public static final int VERSION = 1;

    public static final class TaskTable {
        public static final String NAME = "TaskTable";

        public static final class Cols {
            public static final String _id="id";//Primary Key
            public static final String UUID="uuid";
            public static final String TITLE="title";
            public static final String DESCRIPTION="description";
            public static final String DATE="date";
            public static final String TIME="time";
            public static final String TASK_TYPE="taskType";
            public static final String USER_ID = "user_id";//Foreign Key
        }
    }

    public static final class UserTable
    {
        public static final String NAME="UserTable";
        public static final int VERSION=1;

        public static final class Cols
        {
            public static final String _id="user_id";//Primary Key
            public static final String FIRST_NAME="firstName";
            public static final String LAST_NAME="lastName";
            public static final String USERNAME="userName";
            public static final String PASSWORD="password";
        }

    }

}
