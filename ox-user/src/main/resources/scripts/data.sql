INSERT INTO user_entity(id, email, email_constraint, email_verified,
                        enabled, realm_id, username, created_timestamp)
VALUES ('dbf2b51c-a14f-4e22-a5c0-c4269fad8537', 'admin@mail.co', 'admin@mail.co', true,
        true, '3743ce53-d2ef-4ca6-9a5a-399ba2ca7205', 'adminito25', 1715945883054),
       ('2e04849a-0f4a-481c-96de-8f7f9863bf4a', 'david@mail.co', 'david@mail.co', true,
        true, '3743ce53-d2ef-4ca6-9a5a-399ba2ca7205', 'davidos', 1715945883056),
       ('ddeaecc5-68f3-4ddc-9731-175dffa994c7', 'ston@mail.co', 'ston@mail.co', true,
        true, '3743ce53-d2ef-4ca6-9a5a-399ba2ca7205', 'stonisloff24', 1715945883060),
       ('8f05a0eb-68c5-4d90-8cc6-c23f06f1817e', 'marko@mail.co', 'marko@mail.co', true,
        true, '3743ce53-d2ef-4ca6-9a5a-399ba2ca7205', 'markiz23', 1715945883063);

INSERT INTO keycloak_role(id, name, realm_id)
VALUES ('184099ca-f8d6-4209-b108-1bc8ce90899c', 'ox_user',
        '3743ce53-d2ef-4ca6-9a5a-399ba2ca7205'),
       ('892abe69-161b-42ba-85e5-f1e9a09a7414', 'ox_admin',
        '3743ce53-d2ef-4ca6-9a5a-399ba2ca7205'),
       ('f5d40d67-6afe-46c2-943c-0d23a86165e3', 'ox_seller',
        '3743ce53-d2ef-4ca6-9a5a-399ba2ca7205');

INSERT INTO user_role_mapping(role_id, user_id)
VALUES ('892abe69-161b-42ba-85e5-f1e9a09a7414', 'dbf2b51c-a14f-4e22-a5c0-c4269fad8537'), -- adminito25 - ox_admin
       ('184099ca-f8d6-4209-b108-1bc8ce90899c', 'dbf2b51c-a14f-4e22-a5c0-c4269fad8537'), -- adminito25 - ox_user
       ('184099ca-f8d6-4209-b108-1bc8ce90899c', '2e04849a-0f4a-481c-96de-8f7f9863bf4a'), -- davidos - ox_user
       ('184099ca-f8d6-4209-b108-1bc8ce90899c', 'ddeaecc5-68f3-4ddc-9731-175dffa994c7'), -- stonisloff24 - ox_user
       ('184099ca-f8d6-4209-b108-1bc8ce90899c', '8f05a0eb-68c5-4d90-8cc6-c23f06f1817e'), -- markiz23 - ox_user
       ('f5d40d67-6afe-46c2-943c-0d23a86165e3', '8f05a0eb-68c5-4d90-8cc6-c23f06f1817e'); -- markiz23 - ox_seller


// all passwords here is - '1234'
INSERT INTO credential(id, salt, type, user_id, created_date, user_label, secret_data, credential_data, priority)
VALUES ('951d72b1-2335-4a8d-9fa5-d5952361503c', null, 'password', 'dbf2b51c-a14f-4e22-a5c0-c4269fad8537',
        '1715945341964', null,
        '{"value":"melJaiZVEhUuX0D3BknjCB9u6LIyHwuGMNvvpzb4UnI=","salt":"0SlTWodi3ktkBhXAdCbo6A==","additionalParameters":{}}',
        '{"hashIterations":27500,"algorithm":"pbkdf2-sha256","additionalParameters":{}}', 10),
       ('2755de19-aa81-450e-854c-c0f9f92230dc', null, 'password', '2e04849a-0f4a-481c-96de-8f7f9863bf4a',
        '1715945341965', null,
        '{"value":"melJaiZVEhUuX0D3BknjCB9u6LIyHwuGMNvvpzb4UnI=","salt":"0SlTWodi3ktkBhXAdCbo6A==","additionalParameters":{}}',
        '{"hashIterations":27500,"algorithm":"pbkdf2-sha256","additionalParameters":{}}', 10),
       ('bc3bb77e-ed19-4a0f-8123-c004ab44e16b', 'My password', 'password', 'ddeaecc5-68f3-4ddc-9731-175dffa994c7',
        '1715945341967', null,
        '{"value":"melJaiZVEhUuX0D3BknjCB9u6LIyHwuGMNvvpzb4UnI=","salt":"0SlTWodi3ktkBhXAdCbo6A==","additionalParameters":{}}',
        '{"hashIterations":27500,"algorithm":"pbkdf2-sha256","additionalParameters":{}}', 10),
       ('079b308b-bdf8-45c4-9bab-1d7872b2c349', null, 'password', '8f05a0eb-68c5-4d90-8cc6-c23f06f1817e',
        '1715945341968', null,
        '{"value":"melJaiZVEhUuX0D3BknjCB9u6LIyHwuGMNvvpzb4UnI=","salt":"0SlTWodi3ktkBhXAdCbo6A==","additionalParameters":{}}',
        '{"hashIterations":27500,"algorithm":"pbkdf2-sha256","additionalParameters":{}}', 10)

