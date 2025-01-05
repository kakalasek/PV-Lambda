CREATE DATABASE garden;

USE garden;

CREATE TABLE Flowerbed(
	id INT NOT NULL AUTO_INCREMENT,
    size DECIMAL(10,2) NOT NULL, # m^2
	number INT NOT NULL UNIQUE,
	CONSTRAINT PK_Flowerbed PRIMARY KEY (id)
);

CREATE TABLE Plant(
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	life_length ENUM('Annual', 'Perennial', 'Biennial'),
	growing_time INT, # day
	spacing INT, # cm
	planting_depth INT, # cm
	planting_time INT, # month
	pre_growing BOOL,
	CONSTRAINT PK_Plant PRIMARY KEY (id)
);

CREATE TABLE Planting(
	id INT NOT NULL AUTO_INCREMENT,
	date_from DATE NOT NULL,
	date_to DATE,
	number_of_seeds INT NOT NULL,
	plant_id INT NOT NULL,
	flowerbed_id INT NOT NULL,
	CONSTRAINT PK_Planting PRIMARY KEY (id),
	CONSTRAINT FK_Planting_Plant FOREIGN KEY (plant_id) REFERENCES Plant(id),
	CONSTRAINT FK_Planting_Flowerbed FOREIGN KEY (flowerbed_id) REFERENCES Flowerbed(id)
);

CREATE TABLE Packaging(
	id INT NOT NULL AUTO_INCREMENT,
	expiration_date DATE,
	number_of_seeds INT NOT NULL,
	CONSTRAINT PK_Packaging PRIMARY KEY (id)
);

CREATE TABLE Storage(
	id INT NOT NULL AUTO_INCREMENT,
	plant_id INT NOT NULL,
	packaging_id INT NOT NULL,
	CONSTRAINT PK_Storage PRIMARY KEY (id),
	CONSTRAINT FK_Storage_Plant FOREIGN KEY (plant_id) REFERENCES Plant(id),
	CONSTRAINT FK_Storage_Packaging FOREIGN KEY (packaging_id) REFERENCES Packaging(id)
);

CREATE VIEW Planted_plants
AS 
SELECT Plant.name AS Plant_Name, Plant.life_length AS Life_Length, Plant.growing_time AS Time_To_Grow, Plant.spacing AS Spacing, Plant.planting_depth AS Depth_Of_Planting, Plant.planting_time AS Month_To_Plant, Plant.pre_growing AS Pre_Growing,
Planting.date_from AS Date_Of_Planting, Planting.date_to AS Date_Of_Disposal, Planting.number_of_seeds AS Number_Of_Seeds, 
Flowerbed.number AS Flowerbed_Number, Flowerbed.size AS Flowerbed_Size
FROM Planting
INNER JOIN Plant ON Planting.plant_id = Plant.id
INNER JOIN Flowerbed ON Planting.flowerbed_id = Flowerbed.id;

CREATE VIEW Stored_plants
AS
SELECT Plant.name AS Plant_Name, Plant.life_length AS Life_Length, Plant.growing_time AS Time_To_Grow, Plant.spacing AS Spacing, Plant.planting_depth AS Depth_Of_Planting, Plant.planting_time AS Month_To_Plant, Plant.pre_growing AS Pre_Growing,
Packaging.expiration_date AS Expiration_Date, Packaging.number_of_seeds AS Number_Of_Seeds_In_Package
FROM Storage
INNER JOIN Plant ON Storage.plant_id = Plant.id
INNER JOIN Packaging ON Storage.packaging_id = Packaging.id;
