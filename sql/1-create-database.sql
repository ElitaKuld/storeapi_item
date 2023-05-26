create table item (
    id bigint not null auto_increment,
    name varchar(255),
    price float(53),
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;