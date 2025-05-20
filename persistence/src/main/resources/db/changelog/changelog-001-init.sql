-- создание таблицы клиентов
CREATE TABLE client_app (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    api_key TEXT NOT NULL UNIQUE
);

-- таблица с лимитами на клиента
CREATE TABLE rate_limit_rule (
    id UUID PRIMARY KEY,
    client_id UUID NOT NULL REFERENCES client_app(id),
    max_requests INT NOT NULL,
    window_seconds INT NOT NULL
);

-- лог запросов клиента
CREATE TABLE request_log (
    id UUID PRIMARY KEY,
    client_id UUID NOT NULL REFERENCES client_app(id),
    timestamp TIMESTAMP NOT NULL
);
