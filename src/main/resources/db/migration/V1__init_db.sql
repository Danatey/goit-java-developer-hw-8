CREATE TABLE client (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE worker (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    birthday DATE,
    level VARCHAR(20),
    salary INT
);

CREATE TABLE project (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    client_id INT,
    start_date DATE,
    finish_date DATE,
    CONSTRAINT fk_project_client
        FOREIGN KEY (client_id)
        REFERENCES client(id)
);

CREATE TABLE project_worker (
    project_id INT,
    worker_id INT,
    CONSTRAINT fk_project_worker_project
        FOREIGN KEY (project_id)
        REFERENCES project(id),
    CONSTRAINT fk_project_worker_worker
        FOREIGN KEY (worker_id)
        REFERENCES worker(id)
);