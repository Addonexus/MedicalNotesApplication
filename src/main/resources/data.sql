INSERT INTO users(id,username,password)
VALUES (1,'admin','$2a$10$B188I9BfwGLsWGU9eF4wPOV6O6z.MgEbNxcErNEKb8xwM.4ChBT7G');

INSERT INTO users(id,username,password)
VALUES (2,'test','$2a$10$B188I9BfwGLsWGU9eF4wPOV6O6z.MgEbNxcErNEKb8xwM.4ChBT7G');

INSERT INTO roles(id,role) VALUES(1,'ADMIN');


INSERT INTO users_roles_link(user_id,role_id) VALUES(1,1);

insert into categories (id, user_id, name) values (1, 1, 'heart');
insert into categories (id, user_id, name) values (2, 1, 'skin');
insert into categories (id, user_id, name) values (3, 1, 'brain');

insert into categories (id, user_id, name) values (4, 2, 'leg');
insert into categories (id, user_id, name) values (5, 2, 'arm');
insert into categories (id, user_id, name) values (6, 2, 'face');

insert into diagnoses (id, name, category_id, user_id) values (1,'heart ache', 1, 1);
insert into diagnoses (id, name, category_id, user_id) values (2,'circulation problems', 1, 1);
insert into diagnoses (id, name, category_id, user_id) values (3,'beats too fast', 1, 1);
insert into diagnoses (id, name, category_id, user_id) values (4,'beats too slow', 1, 1);
insert into diagnoses (id, name, category_id, user_id) values (5,'beats just right', 1, 1);
insert into diagnoses (id, name, category_id, user_id) values (6,'doesnt beat', 1, 1);
insert into diagnoses (id, name, category_id, user_id) values (7,'heart attack', 1, 1);
insert into diagnoses (id, name, category_id, user_id) values (8,'skin cancer', 2, 1);
insert into diagnoses (id, name, category_id, user_id) values (9,'skin burnt', 2, 1);
insert into diagnoses (id, name, category_id, user_id) values (10,'skin freeze', 2, 1);
insert into diagnoses (id, name, category_id, user_id) values (11,'skin fell off', 2, 1);
insert into diagnoses (id, name, category_id, user_id) values (12,'brain fell out', 3, 1);
insert into diagnoses (id, name, category_id, user_id) values (13,'brain missing', 3, 1);
insert into diagnoses (id, name, category_id, user_id) values (14,'dog ate brain', 3, 1);
insert into diagnoses (id, name, category_id, user_id) values (15,'insane brain pain', 3, 1);

insert into notifications(id,user_id,content, diagnosis_id, is_done, is_read, creation_date) values (1,1,'HEEHEE', 1, FALSE, FALSE, '2019-11-11 13:23:44');
insert into notifications(id,user_id,content, diagnosis_id, is_done, is_read, creation_date) values (2,1,'SMOOTH CRIMINAL', 2, FALSE, TRUE, '2019-11-11 13:23:44');
insert into notifications(id,user_id,content, diagnosis_id, is_done, is_read, creation_date) values (3,1,'OH',3, TRUE, TRUE, '2019-11-11 13:23:44');

insert into cases (id, name, demographics, notes, date_created, user_id) values (2,'things from kid', '6 y/o kid', 'the kid was nice', CURRENT_TIMESTAMP, 1);

insert into cases (id, name, demographics, notes, date_created, user_id) values (1,'things that went wrong', '80 y/o woman', null, CURRENT_TIMESTAMP, 1);
insert into cases (id, name, demographics, notes, date_created, user_id) values (3,'dog title', '8 y/o dog', null, CURRENT_TIMESTAMP, 1);
insert into cases (id, name, demographics, notes, date_created, user_id) values (4,'dummy data', 'dont care', null, CURRENT_TIMESTAMP, 1);
insert into cases (id, name, demographics, notes, date_created, user_id) values (5,'dummy data 1', 'dont care', null, CURRENT_TIMESTAMP, 1);
insert into cases (id, name, demographics, notes, date_created, user_id) values (6,'dummy data 2', 'dont care', null, CURRENT_TIMESTAMP, 1);
insert into cases (id, name, demographics, notes, date_created, user_id) values (7,'dummy data 3', 'dont care', null, CURRENT_TIMESTAMP, 1);
insert into cases (id, name, demographics, notes, date_created, user_id) values (8,'dummy data 4', 'dont care', null, CURRENT_TIMESTAMP, 1);
insert into cases (id, name, demographics, notes, date_created, user_id) values (9,'dummy data 5', 'dont care', null, CURRENT_TIMESTAMP, 1);
insert into cases (id, name, demographics, notes, date_created, user_id) values (10,'dummy data 6', 'dont care', null, CURRENT_TIMESTAMP, 1);
insert into cases (id, name, demographics, notes, date_created, user_id) values (11,'dummy data 7', 'dont care', null, CURRENT_TIMESTAMP, 1);
insert into cases (id, name, demographics, notes, date_created, user_id) values (12,'dummy data 8', 'dont care', null, CURRENT_TIMESTAMP, 1);
insert into cases (id, name, demographics, notes, date_created, user_id) values (13,'dummy data 9', 'dont care', null, CURRENT_TIMESTAMP, 1);
insert into cases (id, name, demographics, notes, date_created, user_id) values (14,'dummy data 10', 'dont care', null, CURRENT_TIMESTAMP, 1);
insert into cases (id, name, demographics, notes, date_created, user_id) values (15,'dummy data 11', 'dont care', null, CURRENT_TIMESTAMP, 1);
insert into cases (id, name, demographics, notes, date_created, user_id) values (16,'dummy data 12', 'dont care', null, CURRENT_TIMESTAMP, 1);
insert into cases (id, name, demographics, notes, date_created, user_id) values (17,'dummy data 13', 'dont care', null, CURRENT_TIMESTAMP, 1);
insert into cases (id, name, demographics, notes, date_created, user_id) values (18,'dummy data 14', 'dont care', null, CURRENT_TIMESTAMP, 1);
insert into cases (id, name, demographics, notes, date_created, user_id) values (19,'dummy data 15', 'dont care', null, CURRENT_TIMESTAMP, 1);
insert into cases (id, name, demographics, notes, date_created, user_id) values (20,'dummy data 16', 'dont care', null, CURRENT_TIMESTAMP, 1);

insert into cases_diagnoses_link (case_id, diagnosis_id) values (1,2);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (1,3);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (2,2);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (3,2);

insert into cases_diagnoses_link (case_id, diagnosis_id) values (1,1);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (3,1);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (4,1);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (5,1);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (6,1);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (7,1);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (8,1);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (9,1);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (10,1);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (11,1);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (12,1);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (13,1);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (14,1);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (15,1);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (16,1);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (17,1);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (18,1);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (19,1);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (20,1);

insert into diagnosis_informations (id,diagnosis_id, field, value) values (1,1, 'sample_field_1', 'sample_data_1');
insert into diagnosis_informations (id,diagnosis_id, field, value) values (2,1, 'sample_field_2', 'sample_data_2');
insert into diagnosis_informations (id,diagnosis_id, field, value) values (3,1, 'sample_field_3', 'sample_data_3');

insert into freehand_notes (id,diagnosis_id, field) values (3,1, 'BLAAAAAAAAAAA');




-- INSERT INTO user(username,password, fav_charity_id)
-- VALUES ('jude','{bcrypt}$2a$10$B188I9BfwGLsWGU9eF4wPOV6O6z.MgEbNxcErNEKb8xwM.4ChBT7G',2);
-- /* password = password */
-- INSERT INTO role(userid, role)
-- VALUES (001, 'ROLE_USER');
-- INSERT INTO role(userid, role)
-- VALUES (002, 'ROLE_ADMIN');


-- insert into auth_role (auth_role_id, role_name, role_desc) values(1,'SUPER_USER','This user has ultimate rights for everything');
-- insert into auth_role (auth_role_id, role_name, role_desc) values(2,'ADMIN_USER','This user has admin rights for administrative work');
-- insert into auth_role (auth_role_id, role_name, role_desc) values(3,'SITE_USER','This user has access to site, after login - normal user');
--
--
-- insert into auth_user (auth_user_id,first_name,last_name,email,password,status) values (1,'Ankit','Wasankar','admin@gmail.com','{bcrypt}$2a$10$B188I9BfwGLsWGU9eF4wPOV6O6z.MgEbNxcErNEKb8xwM.4ChBT7G','VERIFIED');
-- insert into auth_user_role (auth_user_id, auth_role_id) values ('1','1');
-- insert into auth_user_role (auth_user_id, auth_role_id) values ('1','2');
-- insert into auth_user_role (auth_user_id, auth_role_id) values ('1','3');