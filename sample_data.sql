# insert sample data into the Plants table

INSERT INTO Plant(name, life_length, growing_time, spacing, planting_depth, planting_time, pre_growing)
VALUES ('bazalka prava', 'annual', 42, 30, 1, 3, 0);

INSERT INTO Plant(name, life_length, growing_time, spacing, planting_depth, planting_time, pre_growing)
VALUES ('mrkev darina', 'biennial', 170, 8, 3, 3, 0);

INSERT INTO Plant(name, life_length, growing_time, spacing, planting_depth, planting_time, pre_growing)
VALUES ('hrach ambrosia', 'annual', 80, 20, 4, 4, 0);

# insert sample data into the Flowerbed table

INSERT INTO Flowerbed(size, number)
VALUES (1.2, 1);

INSERT INTO Flowerbed(size, number)
VALUES (5.12, 2);

INSERT INTO Flowerbed(size, number)
VALUES (6.3, 3);

INSERT INTO Flowerbed(size, number)
VALUES (3.13, 4);

INSERT INTO Flowerbed(size, number)
VALUES (2.69, 5);

# insert sample data into the Packaging table

INSERT INTO Packaging(expiration_date, number_of_seeds)
VALUES ('2026-06-24', 15);

INSERT INTO Packaging(expiration_date, number_of_seeds)
VALUES ('2028-10-30', 40);

INSERT INTO Packaging(expiration_date, number_of_seeds)
VALUES ('2025-08-10', 10);

# insert sample data into the Storage table

INSERT INTO Storage(plant_id, packaging_id)
VALUES (1, 1);

INSERT INTO Storage(plant_id, packaging_id)
VALUES (1, 2);

INSERT INTO Storage(plant_id, packaging_id)
VALUES (2, 3);

# insert sample data into the Planting table

INSERT INTO Planting(date_from, date_to, number_of_seeds, plant_id, flowerbed_id)
VALUES ('2023-03-15', '2023-09-12', 2, 2, 1);

INSERT INTO Planting(date_from, date_to, number_of_seeds, plant_id, flowerbed_id)
VALUES ('2024-03-16', '2023-09-12', 4, 2, 3);

INSERT INTO Planting(date_from, date_to, number_of_seeds, plant_id, flowerbed_id)
VALUES ('2024-04-20', NULL, 20, 3, 2);