SET @ROLE_USER_UUID = UUID();
    SET @ROLE_ADMIN_UUID = UUID();
        SET @USER_ADMIN_UUID = UUID();

INSERT INTO roles (id, authority, is_active, created_at, updated_at, created_by, updated_by)
VALUES (@ROLE_USER_UUID, "USER", 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, "SYSTEM", "SYSTEM"),
       (@ROLE_ADMIN_UUID, "ADMIN", 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, "SYSTEM", "SYSTEM");

INSERT INTO users (id, first_name, last_name, username, password,
                   is_active, created_at, updated_at, created_by, updated_by)
VALUES (@USER_ADMIN_UUID, "Tuyen", "Dang", "admin", "$2a$10$MsQC7Jk6IUS7GRG1T5L.TevOqwzA/JdoSxhfxI7lJHHnL9W.b.gP.",
        1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, "SYSTEM", "SYSTEM");

INSERT INTO users_roles (role_id, user_id)
VALUES (@ROLE_USER_UUID, @USER_ADMIN_UUID),
       (@ROLE_ADMIN_UUID, @USER_ADMIN_UUID)