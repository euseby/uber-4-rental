-- Populate vehicles with diverse cars at real Bucharest, Romania locations
-- Run this after the backend starts (it will have created the new columns)

-- Clear existing test vehicles
DELETE FROM vehicles;

-- Reset the sequence
SELECT setval('vehicles_id_seq', 1, false);

-- Insert diverse cars at real Bucharest locations
INSERT INTO vehicles (id, brand, model, fabr_year, type, location, price_per_day, rating, image_url, owner_id, available) VALUES
(1,  'Toyota',        'Corolla',    2022, 'Sedan',     'Gara de Nord',          35,  4.7, 'https://images.unsplash.com/photo-1590362891991-f776e747a588?auto=format&fit=crop&w=800&q=80', NULL, true),
(2,  'BMW',           'X5',         2023, 'SUV',       'Piata Unirii',          110, 4.8, 'https://images.unsplash.com/photo-1556189250-72ba954cfc2b?auto=format&fit=crop&w=800&q=80', NULL, true),
(3,  'Tesla',         'Model 3',    2024, 'Electric',  'Piata Victoriei',       85,  4.9, 'https://images.unsplash.com/photo-1560958089-b8a1929cea89?auto=format&fit=crop&w=800&q=80', NULL, true),
(4,  'Mercedes-Benz', 'C-Class',    2022, 'Sedan',     'Universitate',          95,  4.9, 'https://images.unsplash.com/photo-1618843479313-40f8afb4b4d8?auto=format&fit=crop&w=800&q=80', NULL, true),
(5,  'Ford',          'Mustang',    2021, 'Coupe',     'Piata Romana',          120, 4.8, 'https://images.unsplash.com/photo-1584345604476-8ec5e12e42a5?auto=format&fit=crop&w=800&q=80', NULL, true),
(6,  'Honda',         'CR-V',       2020, 'SUV',       'Titan',                 50,  4.6, 'https://images.unsplash.com/photo-1604147706283-d7119b5b822c?auto=format&fit=crop&w=800&q=80', NULL, true),
(7,  'Dacia',         'Duster',     2023, 'SUV',       'Obor',                  30,  4.3, 'https://images.unsplash.com/photo-1609521263047-f8f205293f24?auto=format&fit=crop&w=800&q=80', NULL, true),
(8,  'Volkswagen',    'Golf 8',     2022, 'Hatchback', 'Aurel Vlaicu',          45,  4.5, 'https://images.unsplash.com/photo-1533473359331-0135ef1b58bf?auto=format&fit=crop&w=800&q=80', NULL, true),
(9,  'Audi',          'A4',         2023, 'Sedan',     'Eroilor',               80,  4.7, 'https://images.unsplash.com/photo-1606664515524-ed2f786a0bd6?auto=format&fit=crop&w=800&q=80', NULL, true),
(10, 'Skoda',         'Octavia',    2021, 'Sedan',     'Crangasi',              38,  4.4, 'https://images.unsplash.com/photo-1619767886558-efdc259cde1a?auto=format&fit=crop&w=800&q=80', NULL, true),
(11, 'Hyundai',       'Tucson',     2023, 'SUV',       'Politehnica',           55,  4.6, 'https://images.unsplash.com/photo-1629897048514-3dd7414fe72a?auto=format&fit=crop&w=800&q=80', NULL, true),
(12, 'Renault',       'Megane E-Tech', 2024, 'Electric', 'Aviatorilor',         70,  4.8, 'https://images.unsplash.com/photo-1593941707882-a5bba14938c7?auto=format&fit=crop&w=800&q=80', NULL, true),
(13, 'Porsche',       'Cayenne',    2022, 'SUV',       'Herastrau',             180, 4.9, 'https://images.unsplash.com/photo-1503376780353-7e6692767b70?auto=format&fit=crop&w=800&q=80', NULL, true),
(14, 'Volvo',         'XC60',       2023, 'SUV',       'Baneasa',               90,  4.7, 'https://images.unsplash.com/photo-1617469767053-d3b523a0b982?auto=format&fit=crop&w=800&q=80', NULL, true),
(15, 'Mazda',         'CX-5',       2022, 'SUV',       'Timpuri Noi',           48,  4.5, 'https://images.unsplash.com/photo-1580273916550-e323be2ae537?auto=format&fit=crop&w=800&q=80', NULL, true),
(16, 'Kia',           'Sportage',   2023, 'SUV',       'Dristor',               52,  4.4, 'https://images.unsplash.com/photo-1609521263047-f8f205293f24?auto=format&fit=crop&w=800&q=80', NULL, true),
(17, 'BMW',           '3 Series',   2024, 'Sedan',     'Cotroceni',             100, 4.8, 'https://images.unsplash.com/photo-1555215695-3004980ad54e?auto=format&fit=crop&w=800&q=80', NULL, true),
(18, 'Fiat',          '500e',       2023, 'Electric',  'Tineretului',           35,  4.2, 'https://images.unsplash.com/photo-1593941707882-a5bba14938c7?auto=format&fit=crop&w=800&q=80', NULL, true);

-- Set the sequence to after our last id
SELECT setval('vehicles_id_seq', 18, true);

-- Create an admin user with bcrypt-hashed password "admin123"
-- (keeping the existing admin or updating it)
UPDATE users SET password = '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', role = 'ADMIN' WHERE email = 'admin@uber4rental.com';

-- If admin doesn't exist, insert one
INSERT INTO users (id, email, password, role, username)
SELECT 1, 'admin@uber4rental.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN', 'admin'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@uber4rental.com');
