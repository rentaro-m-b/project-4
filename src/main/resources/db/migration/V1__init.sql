CREATE TABLE sticky_notes (
    id UUID PRIMARY KEY,
    concern TEXT NOT NULL,
    s3_key TEXT,
    created_at TIMESTAMP NOT NULL
);
