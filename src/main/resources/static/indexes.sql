CREATE INDEX idx_course_title ON courses USING btree (title);
CREATE INDEX idx_course_rating ON courses USING btree (rating);
CREATE INDEX idx_course_date_created ON courses USING btree (date_created);
CREATE INDEX idx_courses_users ON courses_users USING btree (course_id, user_id);
CREATE INDEX idx_courses_modules ON modules USING btree (course_id);
CREATE INDEX idx_modules_lessons ON lessons USING btree (module_id);