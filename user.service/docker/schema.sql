create table if not exists users (
     id serial primary key not null,
     name varchar(50),
     balance int
);

create table if not exists user_transactions (
    id serial primary key not null,
    user_id bigint,
    amount int,
    transaction_date timestamp
);