CREATE TABLE Fee (
  fee_id INT AUTO_INCREMENT PRIMARY KEY,
  totalFees DECIMAL(10,2) NOT NULL
);


CREATE TABLE Admissions (
  admission_id INT AUTO_INCREMENT PRIMARY KEY,
  enrollment_id INT NOT NULL,
  fee_id INT NOT NULL,
  student_id INT NOT NULL,
  course_id INT NOT NULL,
  joining_year INT NOT NULL,
  cost DECIMAL(10,2) NOT NULL,
  CONSTRAINT fk_admissions_fee FOREIGN KEY (fee_id) REFERENCES Fee(fee_id)
);



CREATE TABLE Payment (
  payment_id INT AUTO_INCREMENT PRIMARY KEY,
  fee_id INT NOT NULL,
  transaction_date_time DATETIME NOT NULL,
  amount DECIMAL(10,2) NOT NULL,
  transaction_status VARCHAR(255) NOT NULL,
  reference_number INT NOT NULL,
  CONSTRAINT fk_payment_fee FOREIGN KEY (fee_id) REFERENCES Fee(fee_id)
);