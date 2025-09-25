CREATE TABLE task_definitions (
    id UUID PRIMARY KEY,
    description TEXT NOT NULL,
    unit TEXT NOT NULL,
    expected NUMERIC(8, 2) NOT NULL,
    cycle_per_days SMALLINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE tasks (
    id UUID PRIMARY KEY,
    task_definition_id UUID NOT NULL,
    actual NUMERIC(8, 2) NOT NULL,
    due_date TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
