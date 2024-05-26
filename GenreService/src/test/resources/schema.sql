-- Copyright (C) 2024 Lokesh Bisht
--
-- Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
-- in compliance with the License. You may obtain a copy of the License at
--
-- http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software distributed under the License
-- is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
-- or implied. See the License for the specific language governing permissions and limitations under
-- the License.


CREATE SCHEMA movies;

CREATE TABLE movies.genre (
	id int8 NOT NULL,
	created_at timestamp(6) NULL,
	created_by varchar(255) NULL,
	name varchar(255) NOT NULL,
	updated_at timestamp(6) NULL,
	updated_by varchar(255) NULL,
	CONSTRAINT genre_pkey PRIMARY KEY (id)
);