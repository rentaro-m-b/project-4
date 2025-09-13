CREATE TABLE task_ticket_definition (
    id UUID PRIMARY KEY,
    description TEXT NOT NULL,
    unit TEXT NOT NULL,
    expected NUMERIC(8, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
