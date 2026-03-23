INSERT INTO client (name)
VALUES ('Google'),
       ('Amazon'),
       ('Microsoft');

INSERT INTO worker (name, birthday, level, salary)
VALUES ('Іван', '1990-05-10', 'Senior', 5000),
       ('Галина', '1995-03-15', 'Middle', 3500),
       ('Микола', '1988-11-20', 'Senior', 6000),
       ('Тетяна', '2000-01-12', 'Junior', 2000);

INSERT INTO project (client_id, name, start_date, finish_date)
VALUES (1, 'project-1', '2023-01-01', '2023-06-01'),
       (1, 'project-2', '2023-02-01', '2023-12-01'),
       (2, 'project-3', '2023-03-01', '2023-09-01');

INSERT INTO project_worker
VALUES (1,1),
       (1,2),
       (2,3),
       (3,4);