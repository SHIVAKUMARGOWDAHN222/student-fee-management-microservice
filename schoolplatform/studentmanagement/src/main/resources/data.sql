CREATE TABLE Student (
  student_id INT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  school_name VARCHAR(255) NOT NULL,
  grade INT,
  email VARCHAR(255),
  mobile_number VARCHAR(20)
);

CREATE TABLE Address (
  address_id INT AUTO_INCREMENT PRIMARY KEY,
  student_id INT NOT NULL,
  street VARCHAR(255) NOT NULL,
  city VARCHAR(255) NOT NULL,
  state VARCHAR(255) NOT NULL,
  postal_code VARCHAR(20),
  CONSTRAINT fk_address_student FOREIGN KEY (student_id) REFERENCES Student(student_id)
);


CREATE TABLE Guardian (
  guardian_id INT AUTO_INCREMENT PRIMARY KEY,
  student_id INT NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  relationship VARCHAR(255),
  email VARCHAR(255),
  mobile_number VARCHAR(20),
  CONSTRAINT fk_guardian_student FOREIGN KEY (student_id) REFERENCES Student(student_id)
);


CREATE TABLE Course (
  course_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255),
  cost DECIMAL(10,2) NOT NULL,
  students_limit INT NOT NULL,
  CONSTRAINT CHECK_cost CHECK (cost > 0)
);


CREATE TABLE Enrollment (
  enrollment_id INT AUTO_INCREMENT PRIMARY KEY,
  student_id INT NOT NULL,
  course_id INT NOT NULL,
  joining_year INT NOT NULL,
  status VARCHAR(255) NOT NULL,
  reason VARCHAR(255) NOT NULL,
  CONSTRAINT fk_enrollment_student FOREIGN KEY (student_id) REFERENCES Student(student_id),
  CONSTRAINT fk_enrollment_course FOREIGN KEY (course_id) REFERENCES Course(course_id)
);


INSERT INTO Course (name, description, cost, students_limit) VALUES
  ('Introduction to Programming', 'Learn the basics of computer programming', 100.00, 100),
  ('Biology 101', 'Explore the fundamentals of life', 150.00, 100),
  ('Literature', 'Dive into classic and modern literature', 200.00, 50),
  ('Introduction to Science', 'Science', 100.00, 100),
  ('Tution fees', 'Tution', 150.00, 100),
  ('English Literature', 'Dive into classic and modern literature', 200.00, 50),
  ('Chess', 'Chess classes', 100.00, 100),
  ('Abacus', 'Abacus classes', 150.00, 100),
  ('Chemistry', 'Chemistry class', 200.00, 50),
  ('Karate', 'Karate Class', 100.00, 100),
  ('Swimming Classes', 'Swimming Classes fees', 150.00, 100);

