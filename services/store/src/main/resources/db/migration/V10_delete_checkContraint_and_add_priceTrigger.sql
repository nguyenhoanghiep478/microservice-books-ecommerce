alter table book
drop constraint if exists book_price_check;

CREATE OR REPLACE FUNCTION check_price_in_stock()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.isInStock = TRUE AND NEW.price <= 0 THEN
        RAISE EXCEPTION 'Price must be greater than 0 when isInStock is true.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

SELECT setval('book_id_seq', (SELECT COALESCE(MAX(id), 1) FROM book));