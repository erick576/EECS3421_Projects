SELECT section_query.Class_Department, section_query.Class_Course_Number
FROM (
    SELECT * 
    FROM takes
    INNER JOIN students
    ON  takes.Student_SSN = students.SSN
    WHERE students.Student_Name = 'John Malkovich'    
) as section_query;
