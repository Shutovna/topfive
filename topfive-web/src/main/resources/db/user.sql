insert into user_management.authority (id, authority) values  (1, 'ROLE_USER');
INSERT INTO user_management.users (id, password, username) VALUES (DEFAULT, '$2a$12$beIx37UL3.8/c7M77AuO.uS2dkBeiI7QXdNYIKug2H8G6SkTIYFmG'::varchar(255), 'nikitos'::varchar(255))
insert into user_management.users_authorities (user_id, authority_id) values (1, 1);
