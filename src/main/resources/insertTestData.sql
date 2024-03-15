INSERT IGNORE INTO `comment_service`.`users`
(`user_id`, `username`, `password`, `full_name`)
VALUES ('-1000', 'test0', 'test00', 'test0 test00');

INSERT IGNORE INTO `comment_service`.`users`
(`user_id`, `username`, `password`, `full_name`)
VALUES ('-1001', 'test1', 'test11', 'test1 test11');

INSERT IGNORE INTO `comment_service`.`users`
(`user_id`, `username`, `password`, `full_name`)
VALUES ('-1002', 'test2', 'test22', 'test2 test22');

INSERT IGNORE INTO `comment_service`.`posts`
(`post_id`, `user_id`, `post_text`, `post_media_url`, `users_liked`, `users_disliked`, `timestamp`)
VALUES (-1000, '-1000', 'test', 'www.test.com', '-1001', '-1002', '1000-01-01 00:00:00');

INSERT IGNORE INTO `comment_service`.`comments`
(`comment_id`, `post_id`, `user_id`, `comment_text`, `users_liked`, `users_disliked`, `timestamp`)
VALUES ('-1000', '-1000', '-1001', 'test', '-1001', '-1002', '2018-08-10 12:10:20');

INSERT IGNORE INTO `comment_service`.`comments`
(`comment_id`, `parent_comment_id`, `user_id`, `comment_text`, `users_liked`, `users_disliked`, `timestamp`)
VALUES ('-1001', '-1000', '-1002', 'test', '-1001', '-1002', '2018-08-10 12:10:20');