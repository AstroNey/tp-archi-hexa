-- Insert one team
INSERT INTO team (name) VALUES ('Dream Team');

-- Insert 6 members (persons)
INSERT INTO person (name, firstName, age) VALUES ('Smith', 'John', 25);
INSERT INTO person (name, firstName, age) VALUES ('Doe', 'Jane', 30);
INSERT INTO person (name, firstName, age) VALUES ('Brown', 'Charlie', 22);
INSERT INTO person (name, firstName, age) VALUES ('Johnson', 'Emily', 28);
INSERT INTO person (name, firstName, age) VALUES ('Williams', 'Michael', 35);
INSERT INTO person (name, firstName, age) VALUES ('Taylor', 'Olivia', 27);

-- Link the 6 members to the team
INSERT INTO members (personId, teamId) VALUES (1, 1);
INSERT INTO members (personId, teamId) VALUES (2, 1);
INSERT INTO members (personId, teamId) VALUES (3, 1);
INSERT INTO members (personId, teamId) VALUES (4, 1);
INSERT INTO members (personId, teamId) VALUES (5, 1);
INSERT INTO members (personId, teamId) VALUES (6, 1);
