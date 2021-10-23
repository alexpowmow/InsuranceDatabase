create table policy (
policy_id varchar(5),
policy_date date,
policy_price numeric(8,2),
customer_id varchar(5),
agent_id varchar(5),
primary key (policy_id)
);

create table policy_Type(
policy_id varchar(5),
policy_type varchar(10),
primary key(policy_id),
foreign key (policy_id) references policy
);


create table agent(
agent_id varchar(5),
customer_id varchar(5),
primary key (agent_id)
);

create table customer(
customer_id varchar(5),
customer_fullName varchar(35), //////DELETE
customer_firstName varchar(16),
customer_lastName varchar(16),
agent_id varchar(5),
primary key (customer_id),
foreign key (agent_id) references agent
);

ALTER TABLE policy
ADD FOREIGN KEY (agent_id) references agent;

create table claim(
claim_id varchar(5),
claim_type varchar(10),
claim_desc varchar(1000),
policy_id varchar(5),
customer_id varchar(5),
adjustor_id varchar(5),
company_id varchar(5),
primary key (claim_id),
foreign key (policy_id) references policy,
foreign key (customer_id) references customer,
foreign key (adjustor_id) references adjustor,
foreign key (company_id) references company
);

create table adjustor(
adjustor_id varchar(5),
primary key (adjustor_id)
);

create table company(
company_id varchar(5),
company_name varchar(20),
primary key(company_id)
);

create table items_Insured(
item_id varchar(5),
item_type varchar(15),
///////add item price

claim_id varchar(5),
primary key (item_id),
foreign key (claim_id) references claim
);

create table payment_Type(
customer_id varchar(5),
payment_name varchar(10),
primary key(customer_id),
foreign key (customer_id) references customer
);

create table credit(
customer_id varchar(5) not null REFERENCES payment_Type,
cardHolder_name varchar(35),
cardNumber NUMERIC(16,0),
cardExpdate varchar(5),
cardCvc NUMERIC(3,0),
cardCompany varchar(15),
primary key(customer_id)
);

create table debit(
customer_id varchar(5) not null REFERENCES payment_Type,
cardHolder_name varchar(35),
cardNumber NUMERIC(16,0),
cardExpdate varchar(5),
cardCvc NUMERIC(3,0),
cardBank varchar(15),
primary key(customer_id)
);

create table checking(
customer_id varchar(5) not null REFERENCES payment_Type,
routing_Number NUMERIC(9,0),
account_Number varchar(12),
primary key(customer_id)
);

create table checking(
customer_id varchar(5) not null REFERENCES payment_Type,
routing_Number NUMERIC(9,0),
account_Number varchar(12),
primary key(customer_id)
);

create table life(
policy_id varchar(5) not null REFERENCES policy_Type,
insuredPerson varchar(35),
Beneficiary varchar(35),
coverageAmount numeric(15,0),
primary key(policy_id)
);



