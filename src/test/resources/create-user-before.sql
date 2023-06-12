delete from user_role;
delete from users;

insert into users(id, active, password, username) values
(1, true, '$2a$12$Yxvh5hiVhOnybyR3k62df.aiB/QrpNmlKbbmmMJQA.s0lH7a5170q', 'dru'),
(2, true, '$2a$12$Yxvh5hiVhOnybyR3k62df.aiB/QrpNmlKbbmmMJQA.s0lH7a5170q', 'mike');

insert into user_role(user_id, roles) values
(1, 'USER'), (1, 'ADMIN'),
(2, 'USER');