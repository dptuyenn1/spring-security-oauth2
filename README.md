## Prerequisite

Create .env file following below structure in project's root folder.

```
DB_NAME=<your_db_name>
DB_USERNAME=<your_db_username>
DB_PASSWORD=<your_db_password>
TIME_ZONE=<your_region_timezone>
DATE_FORMAT=<your_region_datetime_format>
JWT_KEY=<your_jwt_key>
```

___

**REDIS** default address **(localhost:6379)**.

Clarify config in **_application.properties_** if using another one.

```
spring.data.redis.host=<your_redis_host>
spring.data.redis.port=<your_redis_port>
```
