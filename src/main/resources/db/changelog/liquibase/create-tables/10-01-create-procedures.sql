CREATE PROCEDURE get_users_by_email (email_cr VARCHAR(50))
    LANGUAGE SQL
AS $$
SELECT *
FROM users
         INNER JOIN credentials ON users.credId=credentials.id
WHERE credentials.email = email_cr;
$$;

CREATE PROCEDURE if_exist_user_by_id (id_user int2)
    LANGUAGE SQL
AS $$
SELECT
    CASE WHEN EXISTS
        (
            SELECT * FROM users WHERE id=id_user
        )
             THEN 'TRUE'
         ELSE 'FALSE'
        END
$$;

CREATE PROCEDURE get_course_by_title (title_course VARCHAR(50))
    LANGUAGE SQL
AS $$
SELECT *
FROM courses
WHERE courses.title = title_course;
$$;

CREATE PROCEDURE approve_booking (id_booking int2)
    LANGUAGE SQL
AS $$
UPDATE booking SET status=1
    WHERE id=id_booking;
INSERT INTO users_courses (userid, coursesid, roleid)
  (SELECT userId as u, courseId as c, 3  as roles  FROM booking where id = id_booking);
$$;

call get_users_by_email('stesh2@mail.ru')
