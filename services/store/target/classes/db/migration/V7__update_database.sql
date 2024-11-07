
CREATE TABLE if not exists address (
                                       id SERIAL PRIMARY KEY,
                                       street VARCHAR(255) NOT NULL,
                                       city VARCHAR(255) NOT NULL,
                                       state VARCHAR(255) NOT NULL,
                                       zip VARCHAR(20),
                                       created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                       last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE IF NOT EXISTS inventory(
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255),
                           address_id INT UNIQUE

);


CREATE TABLE IF NOT EXISTS inventory_book (
                                inventory_id INT,
                                book_id INT,
                                available_quantity INT NOT NULL default 0,
                                PRIMARY KEY (inventory_id, book_id),
                                FOREIGN KEY (inventory_id) REFERENCES inventory(id),
                                FOREIGN KEY (book_id) REFERENCES book(id)
);

alter table inventory add
    CONSTRAINT fk_address
    FOREIGN KEY(address_id)
    REFERENCES address(id)
    ON DELETE CASCADE;



alter table book drop column if exists available_quantity

