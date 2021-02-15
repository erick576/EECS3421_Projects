/* 
    Entitity Relations
*/

create table Professor (
    SSN VARCHAR(11),
    Professor_Name VARCHAR(30),
    PRIMARY KEY (SSN)
);

create table Team (
    Team_ID INT,
    PRIMARY KEY (Team_ID)
);

create table Class (
    Department VARCHAR(30),
    Course_Number INT,
    PRIMARY KEY (Department, Course_Number)
);

create table Section ( -- Weak Entity Set and Many to One Relationship
    Section_ID CHAR(1),
    Class_Department VARCHAR(30),
    Class_Course_Number INT, 
    Rating INT,
    Team_ID INT,
    PRIMARY KEY (Section_ID, Class_Department, Class_Course_Number),
    FOREIGN KEY (Class_Department, Class_Course_Number) REFERENCES Class,
    FOREIGN KEY (Team_ID) REFERENCES Team
);

create table Students (
    SSN VARCHAR(11),
    Student_Name VARCHAR(30),
    PRIMARY KEY (SSN)
);

create table GTAs ( -- ISA Relationship
    Student_SSN VARCHAR(11) REFERENCES Students(SSN),
    Salary INT,
    PRIMARY KEY (Student_SSN)
);


/*
    Relationship Relations
*/

create table On_Team1 (
    Professor_SSN VARCHAR(11),
    Team_ID INT,
    PRIMARY KEY (Professor_SSN, Team_ID),
    FOREIGN KEY (Professor_SSN) REFERENCES Professor,
    FOREIGN KEY (Team_ID) REFERENCES Team
);

create table On_Team2 (
    GTA_SSN VARCHAR(11),
    Team_ID INT,
    PRIMARY KEY (GTA_SSN, Team_ID),
    FOREIGN KEY (GTA_SSN) REFERENCES GTAs,
    FOREIGN KEY (Team_ID) REFERENCES Team
);

create table Wait_For (
    Section_ID CHAR(1),
    Class_Department VARCHAR(30),
    Class_Course_Number INT, 
    Student_SSN VARCHAR(11),
    Wait_Rank INT,
    PRIMARY KEY (Student_SSN, Section_ID, Class_Department, Class_Course_Number),
    FOREIGN KEY (Student_SSN) REFERENCES Students,
    FOREIGN KEY (Section_ID, Class_Department, Class_Course_Number) REFERENCES Section
);

create table Takes (
    Section_ID CHAR(1),
    Class_Department VARCHAR(30),
    Class_Course_Number INT, 
    Student_SSN VARCHAR(11),
    Grade VARCHAR(2),
    PRIMARY KEY (Student_SSN, Section_ID, Class_Department, Class_Course_Number),
    FOREIGN KEY (Student_SSN) REFERENCES Students,
    FOREIGN KEY (Section_ID, Class_Department, Class_Course_Number) REFERENCES Section
);

create table Can_Teach (
    Professor_SSN VARCHAR(11),
    Class_Department VARCHAR(30),
    Class_Course_Number INT, 
    PRIMARY KEY (Professor_SSN, Class_Department, Class_Course_Number),
    FOREIGN KEY (Professor_SSN) REFERENCES Professor,
    FOREIGN KEY (Class_Department, Class_Course_Number) REFERENCES Class
);

create table Req_Of (
    Department_Required_By VARCHAR(30),
    Course_Number_Required_By INT,
    Department_Required VARCHAR(30),
    Course_Number_Required INT,
    PRIMARY KEY (Department_Required_By, Course_Number_Required_By, Department_Required, Course_Number_Required),
    FOREIGN KEY (Department_Required_By, Course_Number_Required_By) REFERENCES Class, 
    FOREIGN KEY (Department_Required, Course_Number_Required) REFERENCES Class
);
