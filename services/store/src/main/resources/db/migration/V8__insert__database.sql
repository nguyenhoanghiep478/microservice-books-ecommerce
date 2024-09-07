INSERT INTO address values (1,'141 tám danh','Hồ Chí Minh','Quận 8',70000,CURRENT_TIMESTAMP,current_timestamp);

INSERT INTO inventory values (1,'LOCAL STORE',1);

INSERT INTO inventory_book (inventory_id, book_id, available_quantity)
SELECT 1, b.id, 50
FROM book b
WHERE NOT EXISTS (
    SELECT 1 FROM inventory_book ib WHERE ib.book_id = b.id AND ib.inventory_id = 1
);