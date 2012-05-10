delete from identity;
delete from user;

insert into user (id_user, name, password) values (1, 'karel', 'karel' );
insert into identity (id_identity, id_user) values ('karlatko', 1);

insert into user (id_user, name, password) values (2, 'qwe', 'qwe' );
insert into identity (id_identity, id_user) values ('Juan', 2);
