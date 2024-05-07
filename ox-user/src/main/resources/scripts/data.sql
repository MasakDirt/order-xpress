INSERT INTO users (username, email, password, user_role) -- encoded password - 1234 for all users
VALUES ('adminito25', 'admin@mail.co', '$2a$10$bIjGIiFFRtxMLutKDvuPTeTtpROhRceXImnIqzOUkkwN/aBMJE/hu', 'ADMIN'),
       ('loperlo', 'loperlo@mail.co', '$2a$10$bIjGIiFFRtxMLutKDvuPTeTtpROhRceXImnIqzOUkkwN/aBMJE/hu', 'USER'),
       ('mamamiya', 'helloworld@mail.co', '$2a$10$bIjGIiFFRtxMLutKDvuPTeTtpROhRceXImnIqzOUkkwN/aBMJE/hu', 'USER'),
       ('bibus', 'incognito@mail.co', '$2a$10$bIjGIiFFRtxMLutKDvuPTeTtpROhRceXImnIqzOUkkwN/aBMJE/hu', 'ADMIN');

INSERT INTO account(balance, user_id)
VALUES (100.00, 1),
       (0.30, 2),
       (2305.20, 3),
       (22.03, 4);