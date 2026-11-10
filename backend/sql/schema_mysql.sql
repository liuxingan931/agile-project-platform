CREATE DATABASE IF NOT EXISTS agile_db DEFAULT CHARACTER SET utf8mb4;
USE agile_db;

-- 项目表（A模块）
DROP TABLE IF EXISTS project;
CREATE TABLE project(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    project_name VARCHAR(128) NOT NULL,
    description TEXT,
    create_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 用户表（A模块）
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    role VARCHAR(20)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 迭代表（B模块）
DROP TABLE IF EXISTS sprint;
CREATE TABLE sprint(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    sprint_name VARCHAR(100),
    goal VARCHAR(255),
    start_time DATETIME,
    end_time DATETIME,
    status VARCHAR(20),
    project_id BIGINT,
    create_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 用户故事表（C模块）
DROP TABLE IF EXISTS user_story;
CREATE TABLE user_story(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    content VARCHAR(500),
    priority INT,
    estimate_point INT,
    status VARCHAR(20),
    sprint_id BIGINT,
    assignee_id BIGINT,
    create_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 任务表（C模块：任务看板，用户故事的进一步拆分）
DROP TABLE IF EXISTS task;
CREATE TABLE task(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200),
    description TEXT,
    status VARCHAR(20),
    story_id BIGINT,
    sprint_id BIGINT,
    assignee VARCHAR(64),
    estimate_hours DECIMAL(6,1),
    create_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
