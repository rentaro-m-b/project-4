CREATE TABLE task_ticket_definitions (
    id UUID PRIMARY KEY,
    description TEXT NOT NULL,
    unit TEXT NOT NULL,
    expected NUMERIC(8, 2) NOT NULL,
    cycle_per_days SMALLINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
