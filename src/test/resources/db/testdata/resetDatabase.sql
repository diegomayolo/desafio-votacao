delete from votes;
delete from sessions;
delete from agendas;
delete from associates;

ALTER TABLE votes ALTER COLUMN ID RESTART WITH 1;
ALTER TABLE sessions ALTER COLUMN ID RESTART WITH 1;
ALTER TABLE agendas ALTER COLUMN ID RESTART WITH 1;
ALTER TABLE associates ALTER COLUMN ID RESTART WITH 1;