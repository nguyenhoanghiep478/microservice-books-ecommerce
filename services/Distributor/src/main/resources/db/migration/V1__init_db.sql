CREATE TABLE distributor(
                        id INTEGER PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        slug VARCHAR(255) NOT NULL,
                        license_id  INTEGER UNIQUE ,
                        created_date TIMESTAMP,
                        last_modified_date TIMESTAMP,
                        active boolean not null,
                        email varchar(255) not null,
                        phone varchar(255) not null

);
CREATE TABLE distributor_license(
                             id INTEGER PRIMARY KEY,
                             license_number varchar(255) NOT NULL UNIQUE ,
                             License_type varchar(50) NOT NULL,
                             licence_expiry_date timestamp not null ,
                             account_id integer not null,
                             distributor_id integer not null UNIQUE ,
                             last_modified_date TIMESTAMP,
                             created_date TIMESTAMP,
                             CONSTRAINT fk_license_distributor FOREIGN KEY (distributor_id) REFERENCES distributor (id) ON DELETE CASCADE

);
ALter table distributor
ADD CONSTRAINT fk_distributor_license FOREIGN KEY (license_id) REFERENCES distributor_license (id) ON DELETE CASCADE
