insert into diagnoses (id, name, category_id) values (1,'heart ache', 1);
insert into diagnoses (id, name, category_id) values (2,'circulation problems', 1);
insert into diagnoses (id, name, category_id) values (3,'beats too fast', 1);
insert into diagnoses (id, name, category_id) values (4,'beats too slow', 1);
insert into diagnoses (id, name, category_id) values (5,'beats just right', 1);
insert into diagnoses (id, name, category_id) values (6,'doesnt beat', 1);
insert into diagnoses (id, name, category_id) values (7,'heart attack', 1);
insert into diagnoses (id, name, category_id) values (8,'skin cancer', 2);
insert into diagnoses (id, name, category_id) values (9,'skin burnt', 2);
insert into diagnoses (id, name, category_id) values (10,'skin freeze', 2);
insert into diagnoses (id, name, category_id) values (11,'skin fell off', 2);
insert into diagnoses (id, name, category_id) values (12,'brain fell out', 3);
insert into diagnoses (id, name, category_id) values (13,'brain missing', 3);
insert into diagnoses (id, name, category_id) values (14,'dog ate brain', 3);
insert into diagnoses (id, name, category_id) values (15,'insane brain pain', 3);

insert into cases (id, name, demographics, notes) values (1,'things that went wront', '80 y/o woman', null);
insert into cases (id, name, demographics, notes) values (2,'things from kid', '6 y/o kid', 'the kid was nice');
insert into cases (id, name, demographics, notes) values (3,'dog title', '8 y/o dog', null);
insert into cases (id, name, demographics, notes) values (4,'dummy data', 'dont care', null);
insert into cases (id, name, demographics, notes) values (5,'dummy data', 'dont care', null);
insert into cases (id, name, demographics, notes) values (6,'dummy data', 'dont care', null);
insert into cases (id, name, demographics, notes) values (7,'dummy data', 'dont care', null);
insert into cases (id, name, demographics, notes) values (8,'dummy data', 'dont care', null);
insert into cases (id, name, demographics, notes) values (9,'dummy data', 'dont care', null);
insert into cases (id, name, demographics, notes) values (10,'dummy data', 'dont care', null);
insert into cases (id, name, demographics, notes) values (11,'dummy data', 'dont care', null);
insert into cases (id, name, demographics, notes) values (12,'dummy data', 'dont care', null);
insert into cases (id, name, demographics, notes) values (13,'dummy data', 'dont care', null);
insert into cases (id, name, demographics, notes) values (14,'dummy data', 'dont care', null);
insert into cases (id, name, demographics, notes) values (15,'dummy data', 'dont care', null);
insert into cases (id, name, demographics, notes) values (16,'dummy data', 'dont care', null);
insert into cases (id, name, demographics, notes) values (17,'dummy data', 'dont care', null);
insert into cases (id, name, demographics, notes) values (18,'dummy data', 'dont care', null);
insert into cases (id, name, demographics, notes) values (19,'dummy data', 'dont care', null);
insert into cases (id, name, demographics, notes) values (20,'dummy data', 'dont care', null);



insert into categories (id, name) values (1, 'heart');
insert into categories (id, name) values (2, 'skin');
insert into categories (id, name) values (3, 'brain');

insert into cases_diagnoses_link (case_id, diagnosis_id) values (1,2);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (1,1);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (1,3);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (2,2);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (3,2);
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


