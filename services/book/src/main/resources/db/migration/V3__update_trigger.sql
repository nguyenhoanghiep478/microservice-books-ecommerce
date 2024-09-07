CREATE OR REPLACE FUNCTION modified_in_stock()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.available_quantity = 0  THEN NEW.is_in_stock = false;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_modified_in_stock
    BEFORE INSERT OR UPDATE ON book
    FOR EACH ROW
EXECUTE FUNCTION modified_in_stock();

CREATE TRIGGER trg_check_last_modified_date
    BEFORE INSERT OR UPDATE ON book
    FOR EACH ROW
EXECUTE FUNCTION check_last_modified_date();