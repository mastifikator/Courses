-- First request
SELECT COUNT(login) AS profile_without_posts
FROM profile
         LEFT JOIN post
                   ON profile.profile_id = post.profile_id
WHERE post.profile_id IS NULL;

-- Second request
SELECT post.post_id, post.title AS title, post.content AS content, COUNT(comment.post_id) AS sum_comment
FROM comment
         JOIN post ON comment.post_id = post.post_id
WHERE title ~ '^[0-9].+$'
  AND length(content) > 20
GROUP BY post.post_id
HAVING COUNT(comment.post_id) = 2
ORDER BY post.post_id;

-- Third request
SELECT post.post_id, COUNT(comment.post_id) AS sum_comment
FROM post
         LEFT JOIN comment ON post.post_id = comment.post_id
GROUP BY post.post_id
HAVING COUNT(comment.post_id) < 2
ORDER BY post.post_id
LIMIT 3;