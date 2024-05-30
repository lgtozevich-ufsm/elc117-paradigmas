CREATE TABLE IF NOT EXISTS users(
    id INTEGER,
    cpf VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    birth_date DATE NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS exercises(
    code INTEGER NOT NULL,
    name VARCHAR(100) NOT NULL,
    muscles TEXT NOT NULL,
    PRIMARY KEY (code)
);

CREATE TABLE IF NOT EXISTS plans(
    code INTEGER NOT NULL,
    name VARCHAR(100) NOT NULL,
    price DOUBLE NOT NULL,
    PRIMARY KEY (code)
);

CREATE TABLE IF NOT EXISTS subscriptions(
    id INTEGER,
    user_id INTEGER NOT NULL,
    plan_code INTEGER NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    card_number VARCHAR(255) NOT NULL,
    card_expiry_date DATE NOT NULL,
    card_cvv VARCHAR(10) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (plan_code) REFERENCES plans(code)
);

CREATE TABLE IF NOT EXISTS programs(
    id INTEGER,
    user_id INTEGER NOT NULL,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS program_exercises(
    program_id INTEGER NOT NULL,
    exercise_code INTEGER NOT NULL,
    load INTEGER NOT NULL,
    sets INTEGER NOT NULL,
    minimum_repetitions INTEGER NOT NULL,
    maximum_repetitions INTEGER NOT NULL,
    resting_time DOUBLE NOT NULL,
    PRIMARY KEY (program_id, exercise_code),
    FOREIGN KEY (program_id) REFERENCES programs(id) ON DELETE CASCADE,
    FOREIGN KEY (exercise_code) REFERENCES exercises(code)
);

CREATE TABLE IF NOT EXISTS workouts(
    id INTEGER,
    user_id INTEGER NOT NULL,
    program_name VARCHAR(100) NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS workout_activities(
    workout_id INTEGER NOT NULL,
    exercise_code INTEGER NOT NULL,
    load INTEGER NOT NULL,
    sets INTEGER NOT NULL,
    minimum_repetitions INTEGER NOT NULL,
    maximum_repetitions INTEGER NOT NULL,
    resting_time DOUBLE NOT NULL,
    completed BOOLEAN NOT NULL,
    PRIMARY KEY (workout_id, exercise_code),
    FOREIGN KEY (workout_id) REFERENCES workouts(id),
    FOREIGN KEY (exercise_code) REFERENCES exercises(code)
);