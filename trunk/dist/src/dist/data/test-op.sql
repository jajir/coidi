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

insert into user (id_user, name, password) values (1, 'karel', 'karel' );
insert into identity (id_identity, id_user) values ('karlatko', 1);

insert into user (id_user, name, password) values (2, 'qwe', 'qwe' );
insert into identity (id_identity, id_user) values ('Juan', 2);
