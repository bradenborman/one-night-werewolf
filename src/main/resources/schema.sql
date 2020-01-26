create table lobbies
(
   lobby_id int NOT NULL AUTO_INCREMENT,
   isLocked boolean DEFAULT false,
   left_community_card varchar(30),
   middle_community_card varchar(30),
   right_community_card varchar(30),
   primary key(lobby_id)
);


create table players
(
   player_id int NOT NULL AUTO_INCREMENT,
   username varchar(75) not null,
   is_ready_up boolean DEFAULT false,
   lobby_playing int NOT NULL,
   role_assigned varchar(75),
   primary key(player_id),
   FOREIGN KEY (lobby_playing) REFERENCES lobbies(lobby_id)
);