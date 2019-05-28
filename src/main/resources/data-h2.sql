--NOTE: Comment the DML insert statements mentioned below after the first application launch to avoid any startup errors

--client_secret -> "secret" | It is hashed with bcrypt 10 rounds
INSERT INTO `oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`)
VALUES
	('active', NULL, '{bcrypt}$2a$10$VjQlbFdAngugnFvzcIMvnOIJgg78JLqlP2Krs260FRyjRv6Ycpjji', 'read', 'authorization_code', 'https://localhost:8443/morfeus/v1/channels/100w53412209293/redirect,https://290be04e.ngrok.io/morfeus/v1/channels/100w53412209293/redirect,https://pitangui.amazon.com/api/skill/link/M1SH6L6WJ11SZH,https://layla.amazon.com/api/skill/link/M1SH6L6WJ11SZH,https://alexa.amazon.co.jp/api/skill/link/M1SH6L6WJ11SZH,https://localhost:8443/morfeus/v1/channels/2aa52831441164/redirect,https://layla.amazon.com/api/skill/link/M1SH6L6WJ11SZH,https://alexa.amazon.co.jp/api/skill/link/M1SH6L6WJ11SZH,https://pitangui.amazon.com/api/skill/link/M1SH6L6WJ11SZH', 'CLIENT', NULL, NULL, NULL, NULL);

--passwords for both records -> "active" | It is hashed with bcrypt 10 rounds
INSERT INTO `user` (`user_id`, `email`, `last_name`, `name`, `password`)
VALUES
	(1, 'active', 'ai', 'active', '{bcrypt}$2a$10$oXhCyrm.lRO1VFtK6esUHOnBHz75qa5dyursBVB3MNaWxrqi4.5Hm'),
	(2, 'innoactive', 'intelligence', 'innoactive', '{bcrypt}$2a$10$oXhCyrm.lRO1VFtK6esUHOnBHz75qa5dyursBVB3MNaWxrqi4.5Hm');

INSERT INTO `role` (`role_id`, `role`)
VALUES
	(1, 'ADMIN'),
	(2, 'USER');

INSERT INTO `user_role` (`user_id`, `role_id`)
VALUES
	(1, 1),
	(2, 1);
