--
-- Copyright 2012 coroptis.com
--
--    Licensed under the Apache License, Version 2.0 (the "License");
--    you may not use this file except in compliance with the License.
--    You may obtain a copy of the License at
--
--        http://www.apache.org/licenses/LICENSE-2.0
--
--    Unless required by applicable law or agreed to in writing, software
--    distributed under the License is distributed on an "AS IS" BASIS,
--    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
--    See the License for the specific language governing permissions and
--    limitations under the License.
--

delete from identity;
delete from user;

insert into user (id_user, name, password) values (1, 'qwe', 'qwe' );
insert into identity (id_identity, id_user, country, email, fullname, gendre, language, nickname, postcode, timezone) values ('karel', 1, 'cz', 'karel@gmail.com', 'Karel Black', 0, 'cs', 'Charles', '120 00', 'Prague');

insert into user (id_user, name, password) values (2, 'asd', 'asd' );
insert into identity (id_identity, id_user, country, email, fullname, gendre, language, nickname, postcode, timezone) values ('juan', 2, 'es', 'juan@gmail.com', 'Juan Ceur', 1, 'es', 'Juan', '130 00', 'Madrid');
