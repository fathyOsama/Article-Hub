CREATE DATABASE articleHubSpringOne;

USE articleHubSpringOne;

CREATE TABLE appuser (
    id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255),
    is_deletable VARCHAR(255),
    name VARCHAR(255),
    password VARCHAR(255),
    status VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB;


create table category (
        id integer not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB


create table article (
        id integer not null auto_increment,
        content longtext,
        publication_date datetime(6),
        status varchar(255),
        title varchar(255),
        category_id integer,
        primary key (id)
    ) engine=InnoDB