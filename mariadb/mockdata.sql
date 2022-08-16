-- Initial categories for the forums
INSERT INTO 
  categories (
    id, topic, 
    topic_description, amount_threads, 
    amount_posts, last_post, 
    created, updated
  ) VALUES (
  0, 'Forum discussion', 
  'Discussions regarding the forums and support.', 0,
  0, NULL,
  current_timestamp(), '0000-00-00 00:00:00');

INSERT INTO 
  categories (
    id, topic, 
    topic_description, amount_threads, 
    amount_posts, last_post, 
    created, updated
  ) VALUES (
  1, 'Off Topic', 
  'Discussions for your non forum-related discussions.', 0,
  0, NULL,
  current_timestamp(), '0000-00-00 00:00:00');

INSERT INTO 
  categories (
    id, topic, 
    topic_description, amount_threads, 
    amount_posts, last_post, 
    created, updated
  ) VALUES (
  2, 'Archive', 
  'Old topics which are not needed anymore.', 0,
  0, NULL,
  current_timestamp(), '0000-00-00 00:00:00');

