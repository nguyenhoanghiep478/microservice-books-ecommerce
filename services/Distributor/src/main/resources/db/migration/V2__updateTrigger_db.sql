
CREATE OR REPLACE FUNCTION check_create_date()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.createDate <> OLD.createDate THEN
        RAISE EXCEPTION 'Cannot change the createDate field';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION check_last_modified_date()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.last_modified_date < OLD.createDate THEN
        RAISE EXCEPTION 'modified date cannot before created date';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION modified_active()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.license_id <> null  THEN NEW.active = true;
    else
        new.active = false;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_modified_active
    BEFORE INSERT OR UPDATE ON distributor
    FOR EACH ROW
EXECUTE FUNCTION modified_active();

