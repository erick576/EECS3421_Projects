insert into professor (SSN, Professor_Name) values
    ('4359-435-09', 'Jhon Strong'),
    ('0034-030-21', 'Rushil Patel'),
    ('4980-594-82', 'Tom Stevens');


insert into team (Team_ID) values
    (1),
    (2);

insert into class (Department, Course_Number) values
    ('EECS', 3421),
    ('EECS', 2011),
    ('ECON', 1000);

insert into section (Section_ID, Class_Department, Class_Course_Number, Rating, Team_ID) values
    ('Z', 'EECS', 3421, 8, 1),
    ('N', 'EECS', 3421, 5, 1),
    ('Z', 'EECS', 2011, 6, 1),
    ('N', 'EECS', 2011, 10, 1),
    ('A', 'ECON', 1000, 3, 2),
    ('E', 'ECON', 1000, 9, 2);

insert into students (SSN, Student_Name) values
    ('4584-243-86', 'John Malkovich'),
    ('2480-987-23', 'Justin Lee'),
    ('3767-394-72', 'Mathew Doreain');

insert into gtas (Student_SSN, Salary) values
    ('2480-987-23', 10000),
    ('3767-394-72', 13045);

insert into on_team1 (Professor_SSN, Team_ID) values
    ('4359-435-09', 1),
    ('0034-030-21', 1),
    ('4980-594-82', 2);

insert into on_team2 (GTA_SSN, Team_ID) values
    ('2480-987-23', 1),
    ('3767-394-72', 2);

insert into wait_for (Student_SSN, Section_ID, Class_Department, Class_Course_Number, Wait_Rank) values
    ('4584-243-86', 'A', 'ECON', 1000, 1);

insert into takes(Student_SSN, Section_ID, Class_Department, Class_Course_Number, Grade) values
    ('4584-243-86', 'Z', 'EECS', 2011, 'A'),
    ('4584-243-86', 'Z', 'EECS', 3421, 'B+');

insert into can_teach(Professor_SSN, Class_Department, Class_Course_Number) values
    ('4359-435-09', 'EECS', 2011),
    ('4359-435-09', 'EECS', 3421),
    ('0034-030-21', 'EECS', 2011),
    ('0034-030-21', 'EECS', 3421),
    ('4980-594-82', 'ECON', 1000);

insert into req_of(Department_Required_By, Course_Number_Required_By, Department_Required, Course_Number_Required) values
    ('EECS', 3421, 'EECS', 2011);
