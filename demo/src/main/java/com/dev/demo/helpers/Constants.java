package com.dev.demo.helpers;

public class Constants {

    public static class TABLE_NAMES {

        public static final String USER = "users";
        public static final String ROLE = "roles";
    }

    public static class USERS_ROLES {

        public static final String JOIN_COLUMN = "user_id";
        public static final String INVERSE_JOIN_COLUMN = "role_id";
    }

    public static class EXCEPTION_MESSAGES {

        public static final String NOT_FOUND = "{0} not found";
        public static final String DUPLICATED = "{0} already existed";
    }

    public static class API_RESPONSE_MESSAGES {

        public static final String SUCCESS = "{0} successfully";
    }
}
