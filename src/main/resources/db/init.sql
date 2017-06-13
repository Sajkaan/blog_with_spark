CREATE TABLE IF NOT EXISTS blogEntry (
    id int PRIMARY KEY auto_increment,
    title VARCHAR,
    author VARCHAR,
    blogPost VARCHAR,
    date VARCHAR
);

CREATE TABLE IF NOT EXISTS comments (
    id int PRIMARY KEY auto_increment,
    entry_id INTEGER,
    author VARCHAR,
    author_comment VARCHAR,
    date VARCHAR,
    FOREIGN KEY (entry_id) REFERENCES PUBLIC.blogEntry(id) ON DELETE CASCADE
);