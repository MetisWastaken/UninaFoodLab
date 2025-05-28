-- Create UninaFoodLab Database for PostgreSQL
CREATE DATABASE "UninaFoodLab"
WITH 
ENCODING = 'UTF8'
LC_COLLATE = 'en_US.UTF-8'
LC_CTYPE = 'en_US.UTF-8';

-- Connect to the databaseA
\c "UninaFoodLab";

-- Create a basic configuration table
CREATE TABLE IF NOT EXISTS config (
    id SERIAL PRIMARY KEY,
    config_key VARCHAR(100) NOT NULL UNIQUE,
    config_value TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create trigger for updated_at auto-update
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_config_updated_at BEFORE UPDATE
    ON config FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- Insert initial configuration
INSERT INTO config (config_key, config_value) VALUES 
('database_version', '1.0'),
('app_name', 'UninaFoodLab'),
('created_date', CURRENT_TIMESTAMP);
