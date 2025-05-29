INSERT INTO
    Users(email)
VALUES
    ('alice@example.com'),
    ('bob@example.com'),
    ('charlie@example.com'),
    ('david@example.com'),
    ('emma@example.com'),
    ('frank@example.com'),
    ('grace@example.com'),
    ('henry@example.com'),
    ('isabella@example.com'),
    ('jack@example.com'),
    ('karen@example.com'),
    ('liam@example.com'),
    ('mia@example.com'),
    ('noah@example.com'),
    ('olivia@example.com'),
    ('paul@example.com'),
    ('quinn@example.com'),
    ('rachel@example.com'),
    ('steve@example.com'),
    ('tina@example.com');

INSERT INTO
    Role(name)
VALUES
    ('Administrator'),
    ('Editor'),
    ('Moderator'),
    ('Author'),
    ('Subscriber'),
    ('Manager'),
    ('Contributor'),
    ('Reviewer'),
    ('Analyst'),
    ('Coordinator'),
    ('Designer'),
    ('Developer'),
    ('Tester'),
    ('Operator'),
    ('Support'),
    ('Trainer'),
    ('Consultant'),
    ('Researcher'),
    ('Marketer'),
    ('Sales');

INSERT INTO
    Blog(name, manager)
VALUES
    ('Tech Insights', 1),
    ('Food Paradise', 2),
    ('Travel Diaries', 3),
    ('Health Hub', 4),
    ('Finance Focus', 5),
    ('Artistic Vibes', 6),
    ('Music Mania', 7),
    ('Movie Magic', 8),
    ('Sports Central', 9),
    ('Book Corner', 10),
    ('Gaming World', 11),
    ('Fashion Trends', 12),
    ('Science Daily', 13),
    ('Nature''s Echo', 14),
    ('Education Today', 15),
    ('History Highlights', 16),
    ('Business Beat', 17),
    ('Lifestyle Lounge', 18),
    ('Culture Connect', 19),
    ('Innovation Station', 20);

INSERT INTO
    Article(title, author, blog)
VALUES
    ('Understanding Java', 1, 1),
    ('Spring Boot Tips', 2, 2),
    ('Hibernate Explained', 3, 3),
    ('JPA Best Practices', 4, 4),
    ('Database Optimization', 5, 5),
    ('REST API Design', 6, 6),
    ('Microservices Architecture', 7, 7),
    ('Docker for Developers', 8, 8),
    ('Kubernetes Basics', 9, 9),
    ('Cloud Computing Trends', 10, 10),
    ('Machine Learning Intro', 11, 11),
    ('Data Science Essentials', 12, 12),
    ('Cybersecurity 101', 13, 13),
    ('Blockchain Overview', 14, 14),
    ('AI Innovations', 15, 15),
    ('IoT Applications', 16, 16),
    ('Big Data Analytics', 17, 17),
    ('DevOps Practices', 18, 18),
    ('Software Testing Strategies', 19, 19),
    ('UI/UX Design Principles', 20, 20);

INSERT INTO
    Users_Role
VALUES
    (1, 1),
    (1, 4),
    (2, 2),
    (2, 5),
    (3, 3),
    (3, 6),
    (4, 1),
    (4, 7),
    (5, 8),
    (6, 9),
    (7, 10),
    (8, 11),
    (9, 12),
    (10, 13),
    (11, 14),
    (12, 15),
    (13, 16),
    (14, 17),
    (15, 18),
    (16, 19);