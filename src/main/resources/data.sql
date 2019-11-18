insert into diagnoses (id, name, category_id) values (1,'heart', 1);
insert into diagnoses (id, name, category_id) values (2,'stuff', 1);
insert into diagnoses (id, name, category_id) values (3,'heart things', 1);
insert into diagnoses (id, name, category_id) values (4,'things', 1);

insert into cases (id, name, demographics, notes) values (1,'things that went wront', '80 y/o woman', null);
insert into cases (id, name, demographics, notes) values (2,'things from kid', '6 y/o kid', 'the kid was nice');
insert into cases (id, name, demographics, notes) values (3,'dog title', '8 y/o dog', null);

insert into cases_diagnoses_link (case_id, diagnosis_id) values (1,2);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (1,1);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (1,3);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (2,2);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (3,2);
insert into cases_diagnoses_link (case_id, diagnosis_id) values (3,1);