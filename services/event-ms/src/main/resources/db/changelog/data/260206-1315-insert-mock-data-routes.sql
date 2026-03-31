INSERT INTO routes (id, profile_id, created_at, date) VALUES
('aaaaaaaa-0000-0000-0000-000000000001', NULL, '2025-10-26 10:00:00+03', '2025-10-26'),
('aaaaaaaa-0000-0000-0000-000000000002', NULL, '2025-10-27 09:00:00+03', '2025-10-27'),
('aaaaaaaa-0000-0000-0000-000000000003', NULL, '2025-10-25 11:00:00+03', '2025-10-25'),
('aaaaaaaa-0000-0000-0000-000000000004', NULL, '2025-10-28 12:00:00+03', '2025-10-28'),
('aaaaaaaa-0000-0000-0000-000000000005', NULL, '2025-10-27 13:00:00+03', '2025-10-27');


INSERT INTO route_places (id, route_id, place_id, position) VALUES
(gen_random_uuid(), 'aaaaaaaa-0000-0000-0000-000000000001', '11111111-1111-1111-1111-111111111111', 1),
(gen_random_uuid(), 'aaaaaaaa-0000-0000-0000-000000000001', '33333333-3333-3333-3333-333333333333', 2),
(gen_random_uuid(), 'aaaaaaaa-0000-0000-0000-000000000001', 'cccccccc-cccc-cccc-cccc-cccccccccccc', 3),

(gen_random_uuid(), 'aaaaaaaa-0000-0000-0000-000000000002', '22222222-2222-2222-2222-222222222222', 1),
(gen_random_uuid(), 'aaaaaaaa-0000-0000-0000-000000000002', '44444444-4444-4444-4444-444444444444', 2),
(gen_random_uuid(), 'aaaaaaaa-0000-0000-0000-000000000002', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 3),

(gen_random_uuid(), 'aaaaaaaa-0000-0000-0000-000000000003', '77777777-7777-7777-7777-777777777777', 1),
(gen_random_uuid(), 'aaaaaaaa-0000-0000-0000-000000000003', '88888888-8888-8888-8888-888888888888', 2),
(gen_random_uuid(), 'aaaaaaaa-0000-0000-0000-000000000003', '99999999-9999-9999-9999-999999999999', 3),

(gen_random_uuid(), 'aaaaaaaa-0000-0000-0000-000000000004', 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 1),
(gen_random_uuid(), 'aaaaaaaa-0000-0000-0000-000000000004', 'ffffffff-ffff-ffff-ffff-ffffffffffff', 2),
(gen_random_uuid(), 'aaaaaaaa-0000-0000-0000-000000000004', '55555555-5555-5555-5555-555555555556', 3),

(gen_random_uuid(), 'aaaaaaaa-0000-0000-0000-000000000005', 'dddddddd-dddd-dddd-dddd-dddddddddddd', 1),
(gen_random_uuid(), 'aaaaaaaa-0000-0000-0000-000000000005', 'eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 2),
(gen_random_uuid(), 'aaaaaaaa-0000-0000-0000-000000000005', '11111111-1111-1111-1111-111111111112', 3);