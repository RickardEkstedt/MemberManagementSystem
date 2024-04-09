INSERT INTO addresses (street, postalCode, city)
VALUES ('123 Main St', '12345', 'Cityville'),
       ('456 Elm St', '67890', 'Townsville'),
       ('789 Oak St', '54321', 'Villagetown'),
       ('10 Pine St', '98765', 'Hometown'),
       ('555 Maple St', '13579', 'Hamlet');



INSERT INTO members (first_name, last_name, address_id, email, phone, date_of_birth)
VALUES ('John', 'Doe', 1, 'john@example.com', 1234567890, '1990-01-01'),
       ('Jane', 'Doe', 1, 'jane@example.com', 9876543210, '1995-05-05'),
       ('Alice', 'Smith', 2, 'alice@example.com', 5554443333, '1985-10-10'),
       ('Bob', 'Johnson', 3, 'bob@example.com', 9998887777, '1980-12-12'),
       ('Emma', 'Brown', 4, 'emma@example.com', 7776665555, '1998-08-08');
