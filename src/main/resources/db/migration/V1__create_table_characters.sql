CREATE TABLE characters(id bigint auto_increment, 
						short_name varchar(255), 
						full_name varchar(255), 
						marvel_id bigint, 
						sync boolean, 
						last_sync TIMESTAMP WITH TIME ZONE);
ALTER TABLE characters ADD PRIMARY KEY (id);