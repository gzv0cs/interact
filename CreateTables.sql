CREATE SEQUENCE IF NOT EXISTS SensorData_id_seq START 1;
CREATE TABLE IF NOT EXISTS SensorData (
    id int PRIMARY KEY default nextval('SensorData_id_seq') NOT NULL,
    weatherStationNo character(1) ,
    sensorId int,
    sessorData numeric(8,2),
    dataUnit character(2) ,
    readDateTime date,
    createdBy character(20)  NOT NULL default current_user,
    createdDateTime timestamp NOT NULL default current_timestamp,
    modifiedBy character(20) ,
    modifiedDateTime timestamp
);
