create table category (id bigint not null, name varchar(255), primary key (id)) engine=InnoDB;
create table category_seq (next_val bigint) engine=InnoDB;
insert into category_seq values ( 1 );
create table product (id bigint not null, description varchar(255), image varchar(255), name varchar(255), price integer not null, category_id bigint, primary key (id)) engine=InnoDB;
create table product_seq (next_val bigint) engine=InnoDB;
insert into product_seq values ( 1 );
alter table product add constraint FK1mtsbur82frn64de7balymq9s foreign key (category_id) references category (id);