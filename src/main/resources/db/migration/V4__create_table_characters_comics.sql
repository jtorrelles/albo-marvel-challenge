CREATE TABLE characters_comics(id bigint auto_increment, character_id bigint, comic_id bigint);
ALTER TABLE characters_comics ADD PRIMARY KEY (id);
ALTER TABLE characters_comics ADD FOREIGN KEY (character_id) REFERENCES characters(id);
ALTER TABLE characters_comics ADD FOREIGN KEY (comic_id) REFERENCES comics(id);