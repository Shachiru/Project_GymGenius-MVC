DROP DATABASE if exists gym_genius;
create database gym_genius;

use gym_genius;

create table User(
ID varchar(10) primary key,
Username varchar(30)not null,
Password varchar(20)not null
);


create table Employee(
ID varchar(10)primary key,
EmpName varchar(30)not null,
Address varchar(30)not null,
EmpRole varchar(50)not null,
UserID varchar(10),
FOREIGN KEY (UserID) REFERENCES User(ID)on update cascade on delete cascade
);

create table Emp_attendance(
EmpID varchar(10),
Date date,
FOREIGN KEY (EmpID) REFERENCES Employee(ID)on update cascade on delete cascade
);

create table Salary(
SalaryId int primary key,
EmpID varchar(10),
Date date,
Amount Decimal(10,2),
FOREIGN KEY (EmpID) REFERENCES Employee(ID)on update cascade on delete cascade
);

create table Member(
ID varchar(10)primary key,
Name varchar(30),
Address varchar(30),
Mobile varchar(10),
DOB date,
Gender varchar(10)
);

create table Schedule(
m_id varchar(10),
emp_id varchar(10),
Level varchar(30),
Time time,
FOREIGN KEY (emp_id) REFERENCES Employee(ID)on update cascade on delete cascade,
FOREIGN KEY (m_id) REFERENCES Member(ID)on update cascade on delete cascade
);

create table C_attendance(
m_id varchar(10),
Date date,
FOREIGN KEY (m_id) REFERENCES Member(ID)on update cascade on delete cascade
);

create table Payment(
Id int primary key,
m_id varchar(10),
Date date,
Amount decimal(10,2),
FOREIGN KEY (m_id) REFERENCES Member(ID)on update cascade on delete cascade
);

create table Supplier(
Id varchar(10)primary key,
Name varchar(30),
Address varchar(30)
);

create table Supplements(
ID varchar(10)primary key,
Description varchar(255),
Qty int
);

create table stock_details(
supplement_id varchar(10),
supplier_id varchar(10),
Date date,
FOREIGN KEY (supplement_id) REFERENCES Supplements(ID)on update cascade on delete cascade,
FOREIGN KEY (supplier_id) REFERENCES Supplier(ID)on update cascade on delete cascade
);

create table supplements_dateils(
m_id varchar(10),
supplement_id varchar(10),
qty int,
Date date,
FOREIGN KEY (m_id) REFERENCES Member(ID)on update cascade on delete cascade,
FOREIGN KEY (supplement_id) REFERENCES Supplements(ID)on update cascade on delete cascade
);

insert into User values ('U001','admin','1234');