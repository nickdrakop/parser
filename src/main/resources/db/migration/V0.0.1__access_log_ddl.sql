CREATE SEQUENCE access_log_id_seq;
CREATE TABLE IF NOT EXISTS access_log
(
    id INTEGER NOT NULL DEFAULT nextval('access_log_id_seq'),
    log_date TIMESTAMP NOT NULL,
    ip_address VARCHAR(255) NOT NULL,
    requestType VARCHAR(255) NOT NULL,
    responseCode VARCHAR(255) NOT NULL,
    CONSTRAINT id_pkey PRIMARY KEY (id)
);
CREATE INDEX access_log_log_date_idx ON access_log (log_date);
CREATE INDEX access_log_ip_address_idx ON access_log (ip_address);