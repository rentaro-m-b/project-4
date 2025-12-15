CREATE TABLE sticky_notes (
    id UUID PRIMARY KEY,
    concern TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE next_actions (
    id UUID PRIMARY KEY,
    description TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE scheduled_actions (
    id UUID PRIMARY KEY,
    description TEXT NOT NULL,
    starts_at TIMESTAMP NOT NULL,
    ends_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL
);
