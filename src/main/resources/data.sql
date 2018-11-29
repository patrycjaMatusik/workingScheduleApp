insert into workers(name, surname, address, phone_number) values
('Jan', 'Kowalski', 'ul. Sosnowa 55/16, 75-896 Poznań', '987586951'),
('Anna', 'Nowak', 'ul. Filipowa 12/36, 55-256 Wrocław', '+48548965745');

insert into schedules(work_date, start_working_hour, end_working_hour) values
('2018-11-26', '09:00:00', '17:00:00'),
('2018-11-27', '10:00:00', '18:00:00'),
('2018-11-27', '10:00:00', '18:00:00');

insert into schedules_workers(workers_id, schedules_id) values
('1', '1'),
('1', '2'),
('2', '2');
