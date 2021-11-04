CREATE TABLE comics_colaborators(id bigint auto_increment, comic_id bigint, colaborator_id bigint);
ALTER TABLE comics_colaborators ADD PRIMARY KEY (id);
ALTER TABLE comics_colaborators ADD FOREIGN KEY (comic_id) REFERENCES comics(id);
ALTER TABLE comics_colaborators ADD FOREIGN KEY (colaborator_id) REFERENCES colaborators(id);