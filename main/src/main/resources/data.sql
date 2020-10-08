insert into email (email, id) values ('diego@old-camp.xyz', 1);
--Dieg0!pass
insert into login (username, password, id) values ('Diego', '$2a$10$6eogBTbEfGIBR4rw5VC5puM8bKCRBGr0NSM23njtXbJhESqkD6n5e', 1);
insert into user (email_id, login_id, id) values (1,1,1);


insert into email (email, id) values ('lester@swamp-camp.abc', 2);
--Lester0!pass
insert into login (username, password, id) values ('Lester', '$2y$12$tiUk.l2finp/6rc64DfyauCaOvx.hc0WbzhnqaJg3QrRGCiiU.T0e', 2);
insert into user (email_id, login_id, id) values (2, 2, 2);

insert into observation (date, species_name, user_id, id) values ('2020-04-17',  'Black woodpecker', 1, 1);
insert into observation (date, species_name, user_id, id) values ('2020-04-18',  'European green woodpecker', 1, 2);
insert into observation (date, species_name, user_id, id) values ('2020-04-19',  'Eurasian three-toed woodpecker', 1, 3);
insert into observation (date, species_name, user_id, id) values ('2019-11-20',  'European green woodpecker', 1, 4);
insert into observation (date, species_name, user_id, id) values ('2019-11-21',  'Black woodpecker', 1, 5);
insert into observation (date, species_name, user_id, id) values ('2019-11-23',  'Middle spotted woodpecker', 1, 6);
insert into observation (date, species_name, user_id, id) values ('2019-11-30',  'European green woodpecker', 1, 7);
insert into observation (date, species_name, user_id, id) values ('2019-11-30',  'Black woodpecker', 1, 8);