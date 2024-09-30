ALTER TABLE inventory_book
    ADD COLUMN sale_price DECIMAL(10, 2);

--  Cập nhật giá trị sale_price từ book.price
UPDATE inventory_book ib
SET sale_price = b.price - 2.00
FROM book b
WHERE ib.book_id = b.id;

--  Đảm bảo tất cả các bản ghi đều có giá trị sale_price và thêm ràng buộc NOT NULL
ALTER TABLE inventory_book
    ALTER COLUMN sale_price SET NOT NULL;