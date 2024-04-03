insert into book (name, author, price, description)
values ('Moon', 'Keith', 113, 'Test subject');

INSERT INTO users VALUES ('keith', '{noop}keithpw', 'Lee Lap Kei Keith', 'keithlee@hkmu.edu.hk', '
81 Chung Hau Street, Ho Man Tin, Kowloon');
INSERT INTO user_roles(username, role) VALUES ('keith', 'ROLE_USER');
INSERT INTO user_roles(username, role) VALUES ('keith', 'ROLE_ADMIN');
INSERT INTO users VALUES ('john', '{noop}johnpw', 'John Lai', 'jlai@hkmu.edu.hk', '
81 Chung Hau Street, Ho Man Tin, Kowloon');
INSERT INTO user_roles(username, role) VALUES ('john', 'ROLE_ADMIN');
INSERT INTO users VALUES ('mary', '{noop}marypw', 'Mary Wang', 'maryw@hkmu.edu.hk', '
81 Chung Hau Street, Ho Man Tin, Kowloon');
INSERT INTO user_roles(username, role) VALUES ('mary', 'ROLE_USER');