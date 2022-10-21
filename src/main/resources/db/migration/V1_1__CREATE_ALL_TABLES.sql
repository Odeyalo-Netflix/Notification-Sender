CREATE TABLE sent_emails(
    id bigserial,
    recipient varchar(100) NOT NULL,
    body varchar(5000) NOT NULL,
    subject varchar(255) NOT NULL,
    success boolean NOT NULL,
    exception_reason varchar(255)
);


CREATE OR REPLACE FUNCTION check_id_change()
    RETURNS TRIGGER AS
$BODY$
BEGIN
    IF NEW.id <> OLD.id
    THEN
        RAISE EXCEPTION '"id" column cannot be updated';
    END IF;

    RETURN NEW;
END;

$BODY$ LANGUAGE PLPGSQL;


CREATE TRIGGER client_update_trigger
    BEFORE UPDATE OF "id"
    ON "sent_emails"
    FOR EACH ROW
EXECUTE PROCEDURE check_id_change();


CREATE OR REPLACE FUNCTION check_rows()
    RETURNS TRIGGER AS
$BODY$
    BEGIN
        IF NEW."recipient" <> OLD."recipient" THEN
            RAISE EXCEPTION '"recipient" column cannot be updated';
        end if;
        IF NEW.body <> OLD.body THEN
            RAISE EXCEPTION '"body" column cannot be updated';
        end if;
        IF NEW.subject <> OLD.subject THEN
            RAISE EXCEPTION '"subject" column cannot be updated';
        end if;
        IF NEW.success <> OLD.success THEN
            RAISE EXCEPTION '"success" column cannot be updated';
        end if;
        IF NEW.exception_reason <> OLD.exception_reason THEN
            RAISE EXCEPTION '"exception_reason" column cannot be updated';
        end if;


    END;

$BODY$ LANGUAGE PLPGSQL;

CREATE TRIGGER ROWS_CHECK_TRIGGER BEFORE UPDATE OF "body", "recipient", "subject", "success", "exception_reason"
    ON sent_emails  FOR EACH ROW EXECUTE PROCEDURE check_rows();
