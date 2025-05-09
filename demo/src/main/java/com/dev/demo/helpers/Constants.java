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

        public static final String NOT_FOUND = "{0} not found!";
        public static final String DUPLICATED = "{0} already existed!";
        public static final String BAD_CREDENTIALS = "Authentication failed! Invalid username or password";
        public static final String ACCESS_DENIED = "You don't have permission to access or use this resource!";
        public static final String AUTHENTICATION_FAILED = "Authentication failed! Something went wrong...";
        public static final String INVALID_TOKEN = "Invalid token (expired or incorrect signature)!";
        public static final String TOKEN_REQUIRED = "This resource requires authentication token!";
    }

    public static class API_RESPONSE_MESSAGES {

        public static final String SUCCESS = "{0} successfully";
    }

    public static class OTHERS {

        public static final String API_PREFIX = "/api";
        public static final String API_VERSION = "v1";
    }
}
