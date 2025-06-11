CREATE TABLE roles
(
    id         VARCHAR(36) NOT NULL,
    is_active  BIT(1) NULL,
    created_at datetime NULL,
    updated_at datetime NULL,
    created_by VARCHAR(255) NULL,
    updated_by VARCHAR(255) NULL,
    authority  VARCHAR(255) NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE users
(
    id         VARCHAR(36) NOT NULL,
    is_active  BIT(1) NULL,
    created_at datetime NULL,
    updated_at datetime NULL,
    created_by VARCHAR(255) NULL,
    updated_by VARCHAR(255) NULL,
    first_name VARCHAR(255) NULL,
    last_name  VARCHAR(255) NULL,
    username   VARCHAR(255) NULL,
    password   VARCHAR(255) NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE users_roles
(
    role_id VARCHAR(36) NOT NULL,
    user_id VARCHAR(36) NOT NULL,
    CONSTRAINT pk_users_roles PRIMARY KEY (role_id, user_id)
);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES users (id);