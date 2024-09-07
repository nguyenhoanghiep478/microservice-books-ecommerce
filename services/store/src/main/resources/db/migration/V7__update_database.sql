CREATE TABLE inventory (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255),
                           address_id INT NOT NULL
);


CREATE TABLE inventory_book (
                                inventory_id INT,
                                book_id INT,
                                available_quantity INT NOT NULL default 0,
                                PRIMARY KEY (inventory_id, book_id),
                                FOREIGN KEY (inventory_id) REFERENCES inventory(id),
                                FOREIGN KEY (book_id) REFERENCES book(id)
);

