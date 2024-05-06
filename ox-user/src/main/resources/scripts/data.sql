INSERT INTO users (username, email, password, user_role) -- encoded password - 1234 for all users
VALUES ('adminito25', 'admin@mail.co', '$2a$10$bIjGIiFFRtxMLutKDvuPTeTtpROhRceXImnIqzOUkkwN/aBMJE/hu', 'ADMIN'),
       ('loperlo', 'loperlo@mail.co', '$2a$10$bIjGIiFFRtxMLutKDvuPTeTtpROhRceXImnIqzOUkkwN/aBMJE/hu', 'USER'),
       ('mamamiya', 'helloworld@mail.co', '$2a$10$bIjGIiFFRtxMLutKDvuPTeTtpROhRceXImnIqzOUkkwN/aBMJE/hu', 'USER'),
       ('bibus', 'incognito@mail.co', '$2a$10$bIjGIiFFRtxMLutKDvuPTeTtpROhRceXImnIqzOUkkwN/aBMJE/hu', 'ADMIN');