insert into workers(name, surname, address, phone_number) values
('Jan', 'Kowalski', 'ul. Sosnowa 55/16, 75-896 Poznań', '987586951'),
('Anna', 'Nowak', 'ul. Filipowa 12/36, 55-256 Wrocław', '+48548965745'),
('Alicja', 'Kolka', 'ul. Morska 15, 58-965 Myszkowice', '452658956');

insert into schedules(work_date, start_working_hour, end_working_hour) values
('2018-11-26', '09:00', '17:00'),
('2018-11-27', '10:00', '18:00'),
('2018-11-30', '08:00', '18:00');

insert into workers_schedules(worker_id, schedule_id) values
('1', '1'),
('1', '2'),
('2', '2'),
('2', '3'),
('3', '3');
