
INSERT INTO ACCESS (id, email ,password,first_name, last_name, active,role)
VALUES
  (1, 'user@gmail.com','$2a$10$l2EcEutIZljGjj0wNvoTbeBEwvqBTXZHtpI2wCCHeUfyD3kWx/Yg.','Name', 'Surname',
   true,"ROLE_ADMIN");
   
INSERT INTO ACCESS (id,  email,password,first_name, last_name, active,role)
VALUES
  (2, 'himalayaprakash001@gmail.com','$2a$10$l2EcEutIZljGjj0wNvoTbeBEwvqBTXZHtpI2wCCHeUfyD3kWx/Yg.','Himalaya', 'Prakash', true,"ROLE_USER");
  

INSERT INTO ACCESS (id, email,password,first_name, last_name, active,role)
VALUES (3, 'name@gmail.com','$2a$10$l2EcEutIZljGjj0wNvoTbeBEwvqBTXZHtpI2wCCHeUfyD3kWx/Yg.','Name',
        'Surname', true,"ROLE_USER");



INSERT INTO PRODUCTs (product_id, category,product_description,product_price,product_name, product_quantity)
VALUES (1, 'mobile','OnePlus9Pro',60000.00,'OnePlus',45);

INSERT INTO PRODUCTs (product_id, category,product_description,product_price,product_name, product_quantity)
VALUES (2, 'mobile','OppoPro',10000.00,'Oppo',4);

INSERT INTO PRODUCTs (product_id, category,product_description,product_price,product_name, product_quantity)
VALUES (3, 'laptop','ideapad',50000.00,'Lenovo',35);

INSERT INTO PRODUCTs (product_id, category,product_description,product_price,product_name, product_quantity)
VALUES (4, 'laptop','Macbook',160000.00,'Apple',20);
