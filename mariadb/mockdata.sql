-- Initial categories for the forums
INSERT INTO 
  categories (
    id, 
    topic, 
    topic_description, 
    amount_threads, 
    amount_posts, 
    last_post, 
    created, 
    updated
  ) VALUES (
  0, 
  'Forum discussion', 
  'Discussions regarding the forums and support.', 
  2,
  2,
  NULL,
  current_timestamp(), 
  '0000-00-00 00:00:00'
);

INSERT INTO 
  categories (
    id,
    topic, 
    topic_description, 
    amount_threads, 
    amount_posts, 
    last_post, 
    created,
    updated
  ) VALUES (
  1, 
  'Off Topic', 
  'Discussions for your non forum-related discussions.', 
  1,
  1,
  NULL,
  current_timestamp(), 
  '0000-00-00 00:00:00'
);

INSERT INTO 
  categories (
    id,
    topic, 
    topic_description, 
    amount_threads, 
    amount_posts, 
    last_post, 
    created,
    updated
  ) VALUES (
  2,
  'Archive', 
  'Old topics which are not needed anymore.',
  2,
  2,
  NULL,
  current_timestamp(), 
  '0000-00-00 00:00:00'
);

-- Test user with authorities --
INSERT INTO 
  users (
    id, 
    email, 
    username, 
    password, 
    `role`, 
    user_status, 
    created, 
    updated, 
    posts_amount, 
    lastloggedin, 
    version
  ) VALUES (
  1, 
  'testing@gmail.com', 
  'Symb234', 
  '$2a$12$6.s0W0cWp0PkpzqqViGSoe3nk3m38d87xXD0ku14keGM2MuEoxNri', 
  'USER', 
  'ACTIVE', 
  '2022-08-19', 
  current_timestamp(), 
  237,
  '2022-08-19 13:58:17.000',
  0
);

INSERT INTO 
 authorities (
  id, 
  email, 
  authority
) VALUES (
  0, 
  'testing@gmail.com', 
  'READ'
);


---- Threads ----
INSERT INTO forum.threads (
  id,
  category_id,
  user_id, 
  subject,
  content,
  created,
  updated
) VALUES (
  3,
  0,
  1,
  'General Plant Biology',
  'Plant biology is a key area of science that bears major weight in the mankind''s ongoing and future efforts to combat the consequences of global warming, climate change, pollution, and population growth. An in-depth understanding of plant physiology is paramount to our ability to optimize current agricultural practices, to develop new crop varieties, or to implement biotechnological innovations in agriculture. The next-generation cultivars would have to withstand environmental contamination and a wider range of growth temperatures, soil nutrients and moisture levels and effectively deal with growing pathogen pressures to continue to yield well in even suboptimal conditions.', 
  '2022-08-19', 
  '2022-08-19 14:05:20.000'
);

INSERT INTO forum.threads (
  id, 
  category_id, 
  user_id, 
  subject, 
  content, 
  created, 
  updated
) VALUES (
  4,
  2,
  1, 
  'This is an archived thread',
  'Yeah, this is an old archived thread.', 
  '2022-08-19',
  '2022-08-19 11:14:07.000'
);

INSERT INTO forum.threads (
  id, 
  category_id, 
  user_id, 
  subject, 
  content, 
  created, 
  updated
) VALUES (
  5,
  1,
  1,
  'The Physiology Of Growth In Apple Fruits', 
  'Growth of fruits is a problem, not only of considerable plant physiological interest but also of outstanding economic importance. In apples, for instance, fruit size has for a long time been regarded as an important factor in determining the keeping quality of apples in storage. Since keeping quality is related to the physiology of the fruit, it is of considerable interest to investigate the anatomical and histological causes of differences in fruit size and to relate these to physiological phenomena, both during the development of the fi-uit and during its senescent life after removal from the tree. The work described l. in this paper was undertaken to study the relationship between cell size and fruit size, and in a second paper this will be related to physiological and biochemical changes.', 
  '2022-08-19',
  '2022-08-19 14:13:18.000'
);

INSERT INTO forum.threads (
  id, 
  category_id, 
  user_id, 
  subject, 
  content, 
  created, 
  updated
) VALUES (
  6, 
  2, 
  1, 
  'This is something', 
  'something...', 
  '2022-08-19', 
  '2022-08-19 14:14:30.000'
);

INSERT INTO forum.threads (
  id, 
  category_id, 
  user_id, 
  subject, 
  content, 
  created, 
  updated
) VALUES (
  7,
  0,
  1,
  'More of something',
  'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed eget tortor scelerisque, laoreet lectus ac, tempor ipsum. Nam malesuada porta neque sit amet laoreet. Nullam vitae lacus nec tellus dictum dictum. Ut congue lectus et ex elementum blandit. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Sed pellentesque dui augue, nec congue velit aliquet vel. Nulla sed consequat lorem. Quisque dui nunc, ullamcorper vel rhoncus non, sollicitudin lacinia eros. Maecenas pharetra mollis nibh, eget aliquam tortor facilisis at. Aenean in dignissim ante. Donec accumsan ante at tempor condimentum. Fusce quis ex lacinia, accumsan massa id, tincidunt sem. Pellentesque id dolor id diam posuere pellentesque a facilisis odio. Nunc pharetra ex at lectus vulputate, fermentum vestibulum mauris mollis. Nullam sagittis vitae purus vel ultrices.',
  '2022-08-19',
  '2022-08-19 14:15:27.000'
);
