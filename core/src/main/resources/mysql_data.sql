USE
  `ird-security`;
insert into users(username, password, enabled)
values ('admin', '$2a$10$aikc64ahT9Zl41tWSoakJOJj0cOAO.gru//uAiAiFWZmyTtd/5lxm', true);
insert into authorities(username, authority)
values ('admin', 'ROLE_ADMIN');

# client boot and security both have secret as client secret
INSERT INTO oauth_client_details
(client_id, client_secret, scope, authorized_grant_types,
 web_server_redirect_uri, authorities, access_token_validity,
 refresh_token_validity, additional_information, autoapprove)
VALUES ("boot", "$2a$10$L3cKmG.fiN8V2Tenw2oaDOc.3SlxyncnJwUOqLRS/x1dKOsIk29c2", "all,read,write",
        "password,authorization_code,refresh_token", "http://example.com", null, 36000, 36000, null, true);

INSERT INTO oauth_client_details
(client_id, client_secret, scope, authorized_grant_types,
 web_server_redirect_uri, authorities, access_token_validity,
 refresh_token_validity, additional_information, autoapprove)
VALUES
("security", "$2a$10$L3cKmG.fiN8V2Tenw2oaDOc.3SlxyncnJwUOqLRS/x1dKOsIk29c2", "all,read,write",
    "password,authorization_code,refresh_token", "http://example.com", null, 36000, 36000, null, false);