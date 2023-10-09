-- Hibernate doesn't support multiline, so all statements must be on the same line.
-- Table names marked in red will be created when the app runs.
INSERT INTO USERS (USERID, NAME, EMAIL, PASSWORD) VALUES (1, 'Ken', 'test@gmail.com', 'password');

INSERT INTO TRAIL (TRAILID, NAME, DIFFICULTY, TERRAIN, TRAIL_TYPE, ADDRESS, LATITUDE, LONGITUDE, ZIP_CODE) VALUES (1, 'Bender Mountain Loop Trail', 'Moderate', 'Hill', 'Hiking', '123 Test St', '39.15838', '-84.59775', '45211'), (2, 'Beechwood, Furnas, Ponderosa, and Red Oak Trail', 'Moderate', 'Hill', 'Hiking', '124 Test St', '39.16372', '-84.45241', '45212'), (3, 'French Park', 'Moderate', 'Hill', 'Hiking', '125 Test St', '39.18001', '-84.42146', '45213'), (4, 'Ault Forest Loop Trail', 'Easy', 'Hill', 'Hiking', '126 Test St', '39.12040', '-84.54868', '45214'), (5, 'Stone Steps Loop', 'Hard', 'Hill', 'Hiking', '127 Test St', '39.23810', '-84.45970', '45215');

INSERT INTO ALERT (ALERTID, TRAILID, ALERT_TEXT, USERID) VALUES (1, 1, 'Flooding', 1);

