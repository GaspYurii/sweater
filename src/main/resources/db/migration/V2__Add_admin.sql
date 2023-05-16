insert into USERS (id, username, password, active)
    values (1, 'admin', '$2a$12$Yxvh5hiVhOnybyR3k62df.aiB/QrpNmlKbbmmMJQA.s0lH7a5170q', true);
insert into user_role (user_id, roles)
    values (1, 'USER'), (1, 'ADMIN');