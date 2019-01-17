USE
`ird-security`;
insert into users(username, password, enabled)
values ('admin', '$2a$10$aikc64ahT9Zl41tWSoakJOJj0cOAO.gru//uAiAiFWZmyTtd/5lxm', true);
insert into authorities(username, authority)
values ('admin', 'ROLE_ADMIN');