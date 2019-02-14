CREATE TABLE IF NOT EXISTS blocked_ip
(
    ip_address VARCHAR(45) NOT NULL,
    number_of_requests INTEGER NOT NULL,
    requests_start_date TIMESTAMP NOT NULL,
    requests_end_date TIMESTAMP NOT NULL,
    blocking_reason VARCHAR(255) NOT NULL,
    CONSTRAINT ip_address_pkey PRIMARY KEY (ip_address)
);
