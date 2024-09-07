
CREATE TABLE shipment_service (
                                id SERIAL PRIMARY KEY ,
                                name VARCHAR(255) NOT NULL,
                                speed integer not null,
                                cost_per_km double PRECISION not null
);

CREATE TABLE shipment_details (
                        id SERIAL PRIMARY KEY,
                        origin_address_id INT NOT NULL,
                        destination_address_id INT NOT NULL,
                        distance double precision not null,
                        current_address_id INT NOT NULL,
                        total_fee double precision not null ,
                        tracking_number VARCHAR(255) NOT NULL,
                        status VARCHAR(50) NOT NULL,
                        created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        shipment_service_id INT NOT NULL ,
                        CONSTRAINT fk_shipment_service
                        FOREIGN KEY (shipment_service_id)
                        REFERENCES shipment_service(id)
                        ON DELETE CASCADE
);

insert into shipment_service values (1,'FAST DELIVER',50,4.5);
insert into shipment_service values (2,'STANDARD DELIVER',30,3.6);
