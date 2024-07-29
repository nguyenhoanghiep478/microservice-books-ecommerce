create table category(
    id integer not null primary key ,
    name varchar(50) not null,
    description varchar(255),
    created_date timestamp not null,
    last_modified_date timestamp

);

create table book(
    id integer not null primary key ,
    name varchar(50) not null,
    available_quantity integer not null CHECK ( available_quantity >= 0 ),
    image varchar(255) not null,
    price numeric(38,2)  not null CHECK ( price >0 ),
    is_in_stock boolean not null DEFAULT FALSE,
    created_date timestamp not null,
    last_modified_date timestamp not null,
    category_id integer
                 constraint fk_book_category references category,
    title varchar(255) not null,
    chapter int not null check ( chapter>0 )

);
create sequence if not exists category_seq increment by 50;
create sequence if not exists book_seq increment by 50;

;CREATE OR REPLACE FUNCTION check_create_date()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.createDate <> OLD.createDate THEN
        RAISE EXCEPTION 'Cannot change the createDate field';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

;CREATE OR REPLACE FUNCTION check_last_modified_date()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.last_modified_date < OLD.created_date THEN
        RAISE EXCEPTION 'modified date cannot before created date';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
