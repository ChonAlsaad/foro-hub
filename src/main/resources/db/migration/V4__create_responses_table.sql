CREATE TABLE responses (
    id BIGSERIAL PRIMARY KEY,
    message TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    topic_id BIGINT NOT NULL REFERENCES topics(id),
    author_id BIGINT NOT NULL REFERENCES users(id),
    solution BOOLEAN NOT NULL DEFAULT FALSE
);
