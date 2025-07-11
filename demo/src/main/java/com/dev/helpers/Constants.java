package com.dev.helpers;

public class Constants {

    public static class OTHERS {

        public static final String SYSTEM = "SYSTEM";
        public static final String DATETIME_FORMAT_PATTERN = "dd-MM-yyyy HH:mm:ss";
    }

    public static class TABLE_NAMES {

        public static final String USER = "users";
        public static final String ROLE = "roles";
        public static final String INVALID_TOKEN = "invalid_tokens";
    }

    public static class USERS_ROLES {

        public static final String JOIN_COLUMN = "user_id";
        public static final String INVERSE_JOIN_COLUMN = "role_id";
    }

    public static class JWT {

        public static final String ISSUER = OTHERS.SYSTEM;
        public static final String ROLES_CLAIM = "roles";
        public static final String ROLE_PREFIX = "ROLE_";
        public static final String TYPE_CLAIM = "type";

        public static final int DEFAULT_DURATION = 5 * 60 * 1000;

        private static final int MILLISECONDS_OF_SECOND = 1000;

        public static class ACCESS_TOKEN {

            private static final int DAYS_OF_WEEK = 1;
            private static final int HOURS_OF_DAY = 1;
            private static final int MINUTES_OF_HOUR = 15;
            private static final int SECONDS_OF_MINUTE = 60;
            public static final int DURATION = DAYS_OF_WEEK * HOURS_OF_DAY * MINUTES_OF_HOUR
                    * SECONDS_OF_MINUTE * MILLISECONDS_OF_SECOND;
        }

        public static class REFRESH_TOKEN {

            private static final int DAYS_OF_WEEK = 1;
            private static final int HOURS_OF_DAY = 24;
            private static final int MINUTES_OF_HOUR = 60;
            private static final int SECONDS_OF_MINUTE = 60;
            public static final int DURATION = DAYS_OF_WEEK * HOURS_OF_DAY * MINUTES_OF_HOUR
                    * SECONDS_OF_MINUTE * MILLISECONDS_OF_SECOND;
        }
    }

    public static class EXCEPTION_MESSAGES {

        public static final String NOT_FOUND = "{0} not found!";
        public static final String DUPLICATED = "{0} already existed!";
        public static final String BAD_CREDENTIALS = "Authentication failed! Invalid username or password...";
        public static final String ACCESS_DENIED = "You don't have permission to access or use this resource!";
        public static final String AUTHENTICATION_FAILED = "Authentication failed! Something went wrong...";
        public static final String INVALID_TOKEN = "Invalid token (expired, incorrect signature or type)!";
        public static final String TOKEN_REQUIRED = "This resource requires authentication token!";
        public static final String TOKEN_REVOKED = "Token was revoked at {0}, please login again to get the new one!";
    }

    public static class API_RESPONSE_MESSAGES {

        public static final String SUCCESS = "{0} successfully";
    }

    public static class OPEN_API {

        public static final String SECURITY_SCHEME_NAME = "Bearer Authentication";
        public static final String SCHEME = "bearer";
        public static final String BEARER_FORMAT = "JWT";
    }

    public static class API {

        private static final String PATH_PREFIX_FORMAT = "/%s/%s";
        private static final String PREFIX = "api";
        private static final String VERSION = "v1";
        public static final String PATH_PREFIX = String.format(PATH_PREFIX_FORMAT, PREFIX, VERSION);
    }

    public static class WEB_CONFIG {

        public static final String PATH_PREFIX = API.PATH_PREFIX;
        private static final String API_BASE_PACKAGE_FORMAT = "%s.%s";
        private static final String ROOT = "com.dev";
        private static final String PACKAGE = "controllers";
        public static final String API_BASE_PACKAGE = String.format(API_BASE_PACKAGE_FORMAT, ROOT, PACKAGE);
    }

    public static class AUDIT_AWARE {

        public static final String SYSTEM = OTHERS.SYSTEM;
    }

    public static class RESPONSE_COOKIE {

        public static final String NAME = "refreshToken";
        public static final String PATH = "/auth/refresh";
    }
}
