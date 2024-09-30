-- Categories
INSERT INTO category VALUES (1, 'Trinh thám', 'Thể loại này dành cho ai yêu thích khám phá bí ẩn xung quanh', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO category VALUES (2, 'Tình cảm', 'Thể loại này dành cho ai yêu thích những điều ngọt ngào, yên bình xoay quanh tình yêu', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO category VALUES (3, 'Truyện tranh', 'Thể loại này dành cho những bạn nhỏ thích đọc về những câu chuyện tranh', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO category VALUES (4, 'Truyện Khoa học viễn tưởng', 'Thể loại này dành cho ai yêu thích khám phá về công nghệ mới, thế giới xung quanh', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO category VALUES (5, 'Tiểu thuyết', 'Một thể loại văn xuôi có hư cấu, thông qua nhân vật, hoàn cảnh, sự việc để phản ánh bức tranh xã hội rộng lớn và những vấn đề của cuộc sống con người', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Books
-- Trinh thám
INSERT INTO book VALUES (1, 'Thám tử lừng danh Conan tập 1', 40, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 'Conan thám tử lừng danh', 1);
INSERT INTO book VALUES (2, 'Thám tử lừng danh Conan tập 2', 50, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 'Conan thám tử lừng danh', 2);
INSERT INTO book VALUES (3, 'Thám tử lừng danh Conan tập 3', 45, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 'Conan thám tử lừng danh', 3);
INSERT INTO book VALUES (4, 'Thám tử lừng danh Conan tập 4', 50, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 'Conan thám tử lừng danh', 4);
INSERT INTO book VALUES (5, 'Thám tử lừng danh Conan tập 5', 50, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 'Conan thám tử lừng danh', 5);
INSERT INTO book VALUES (6, 'Thám tử lừng danh Conan tập 6', 50, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 'Conan thám tử lừng danh', 6);

-- Tình cảm
INSERT INTO book VALUES (7, 'Chúng ta đã ly hôn tập 1', 20, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2, 'Chúng ta đã ly hôn', 1);
INSERT INTO book VALUES (8, 'Chúng ta đã ly hôn tập 2', 20, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2, 'Chúng ta đã ly hôn', 2);
INSERT INTO book VALUES (9, 'Chúng ta đã ly hôn tập 3', 20, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2, 'Chúng ta đã ly hôn', 3);
INSERT INTO book VALUES (10, 'Chúng ta đã ly hôn tập 4', 30, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2, 'Chúng ta đã ly hôn', 4);
INSERT INTO book VALUES (11, 'Chúng ta đã ly hôn tập 5', 40, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2, 'Chúng ta đã ly hôn', 5);
INSERT INTO book VALUES (12, 'Chúng ta đã ly hôn tập 6', 40, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2, 'Chúng ta đã ly hôn', 6);

-- Truyện tranh
INSERT INTO book VALUES (13, 'Chúng ta đã ly hôn tập 1', 20, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 3, 'Chúng ta đã ly hôn', 1);
INSERT INTO book VALUES (14, 'Chúng ta đã ly hôn tập 2', 20, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 3, 'Chúng ta đã ly hôn', 2);
INSERT INTO book VALUES (15, 'Chúng ta đã ly hôn tập 3', 20, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 3, 'Chúng ta đã ly hôn', 3);
INSERT INTO book VALUES (16, 'Chúng ta đã ly hôn tập 4', 30, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 3, 'Chúng ta đã ly hôn', 4);
INSERT INTO book VALUES (17, 'Chúng ta đã ly hôn tập 5', 40, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 3, 'Chúng ta đã ly hôn', 5);
INSERT INTO book VALUES (18, 'Chúng ta đã ly hôn tập 6', 40, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 3, 'Chúng ta đã ly hôn', 6);

-- Additional Books to reach 100 entries

-- Trinh thám
INSERT INTO book VALUES (19, 'Thám tử lừng danh Conan tập 7', 60, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 'Conan thám tử lừng danh', 7);
INSERT INTO book VALUES (20, 'Thám tử lừng danh Conan tập 8', 65, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 'Conan thám tử lừng danh', 8);
INSERT INTO book VALUES (21, 'Thám tử lừng danh Conan tập 9', 70, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 'Conan thám tử lừng danh', 9);
INSERT INTO book VALUES (22, 'Thám tử lừng danh Conan tập 10', 75, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 'Conan thám tử lừng danh', 10);

-- Tình cảm
INSERT INTO book VALUES (23, 'Chúng ta đã ly hôn tập 7', 25, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2, 'Chúng ta đã ly hôn', 7);
INSERT INTO book VALUES (24, 'Chúng ta đã ly hôn tập 8', 25, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2, 'Chúng ta đã ly hôn', 8);
INSERT INTO book VALUES (25, 'Chúng ta đã ly hôn tập 9', 30, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2, 'Chúng ta đã ly hôn', 9);
INSERT INTO book VALUES (26, 'Chúng ta đã ly hôn tập 10', 35, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2, 'Chúng ta đã ly hôn', 10);

-- Truyện tranh
INSERT INTO book VALUES (27, 'Chúng ta đã ly hôn tập 7', 25, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 3, 'Chúng ta đã ly hôn', 7);
INSERT INTO book VALUES (28, 'Chúng ta đã ly hôn tập 8', 25, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 3, 'Chúng ta đã ly hôn', 8);
INSERT INTO book VALUES (29, 'Chúng ta đã ly hôn tập 9', 30, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 3, 'Chúng ta đã ly hôn', 9);
INSERT INTO book VALUES (30, 'Chúng ta đã ly hôn tập 10', 35, 'update later', 2.3, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 3, 'Chúng ta đã ly hôn', 10);

-- Truyện Khoa học viễn tưởng
INSERT INTO book VALUES (31, 'Khoa học viễn tưởng 1', 40, 'update later', 2.5, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 4, 'Khoa học viễn tưởng', 1);
INSERT INTO book VALUES (32, 'Khoa học viễn tưởng 2', 45, 'update later', 2.5, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 4, 'Khoa học viễn tưởng', 2);
INSERT INTO book VALUES (33, 'Khoa học viễn tưởng 3', 50, 'update later', 2.5, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 4, 'Khoa học viễn tưởng', 3);
INSERT INTO book VALUES (34, 'Khoa học viễn tưởng 4', 55, 'update later', 2.5, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 4, 'Khoa học viễn tưởng', 4);
INSERT INTO book VALUES (35, 'Khoa học viễn tưởng 5', 60, 'update later', 2.5, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 4, 'Khoa học viễn tưởng', 5);
INSERT INTO book VALUES (36, 'Khoa học viễn tưởng 6', 65, 'update later', 2.5, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 4, 'Khoa học viễn tưởng', 6);

-- Tiểu thuyết
INSERT INTO book VALUES (37, 'Tiểu thuyết 1', 50, 'update later', 3.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 5, 'Tiểu thuyết', 1);
INSERT INTO book VALUES (38, 'Tiểu thuyết 2', 55, 'update later', 3.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 5, 'Tiểu thuyết', 2);
INSERT INTO book VALUES (39, 'Tiểu thuyết 3', 60, 'update later', 3.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 5, 'Tiểu thuyết', 3);
INSERT INTO book VALUES (40, 'Tiểu thuyết 4', 65, 'update later', 3.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 5, 'Tiểu thuyết', 4);
INSERT INTO book VALUES (41, 'Tiểu thuyết 5', 70, 'update later', 3.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 5, 'Tiểu thuyết', 5);
INSERT INTO book VALUES (42, 'Tiểu thuyết 6', 75, 'update later', 3.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 5, 'Tiểu thuyết', 6);

-- Additional entries for variety
-- Trinh thám
INSERT INTO book VALUES (43, 'Thám tử lừng danh Conan tập 11', 80, 'update later', 2.5, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 'Conan thám tử lừng danh', 11);
INSERT INTO book VALUES (44, 'Thám tử lừng danh Conan tập 12', 85, 'update later', 2.5, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 'Conan thám tử lừng danh', 12);

-- Tình cảm
INSERT INTO book VALUES (45, 'Chúng ta đã ly hôn tập 11', 40, 'update later', 2.5, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2, 'Chúng ta đã ly hôn', 11);
INSERT INTO book VALUES (46, 'Chúng ta đã ly hôn tập 12', 45, 'update later', 2.5, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2, 'Chúng ta đã ly hôn', 12);

-- Truyện tranh
INSERT INTO book VALUES (47, 'Chúng ta đã ly hôn tập 11', 40, 'update later', 2.5, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 3, 'Chúng ta đã ly hôn', 11);
INSERT INTO book VALUES (48, 'Chúng ta đã ly hôn tập 12', 45, 'update later', 2.5, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 3, 'Chúng ta đã ly hôn', 12);

-- Khoa học viễn tưởng
INSERT INTO book VALUES (49, 'Khoa học viễn tưởng 7', 70, 'update later', 2.7, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 4, 'Khoa học viễn tưởng', 7);
INSERT INTO book VALUES (50, 'Khoa học viễn tưởng 8', 75, 'update later', 2.7, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 4, 'Khoa học viễn tưởng', 8);

-- Tiểu thuyết
INSERT INTO book VALUES (51, 'Tiểu thuyết 7', 80, 'update later', 3.2, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 5, 'Tiểu thuyết', 7);
INSERT INTO book VALUES (52, 'Tiểu thuyết 8', 85, 'update later', 3.2, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 5, 'Tiểu thuyết', 8);

-- Continue similarly for books 53 to 100
-- Example for some additional books
INSERT INTO book VALUES (53, 'Thám tử lừng danh Conan tập 13', 90, 'update later', 2.7, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 'Conan thám tử lừng danh', 13);
INSERT INTO book VALUES (54, 'Chúng ta đã ly hôn tập 13', 50, 'update later', 2.7, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2, 'Chúng ta đã ly hôn', 13);
INSERT INTO book VALUES (55, 'Chúng ta đã ly hôn tập 14', 55, 'update later', 2.7, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2, 'Chúng ta đã ly hôn', 14);
INSERT INTO book VALUES (56, 'Chúng ta đã ly hôn tập 15', 55, 'update later', 2.7, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2, 'Chúng ta đã ly hôn', 14);

-- Truyện tranh
INSERT INTO book VALUES (57, 'Truyện tranh 7', 30, 'update later', 2.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 3, 'Truyện tranh', 7);
INSERT INTO book VALUES (58, 'Truyện tranh 8', 35, 'update later', 2.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 3, 'Truyện tranh', 8);

-- Khoa học viễn tưởng
INSERT INTO book VALUES (59, 'Khoa học viễn tưởng 9', 80, 'update later', 3.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 4, 'Khoa học viễn tưởng', 9);
INSERT INTO book VALUES (60, 'Khoa học viễn tưởng 10', 85, 'update later', 3.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 4, 'Khoa học viễn tưởng', 10);

-- Tiểu thuyết
INSERT INTO book VALUES (61, 'Tiểu thuyết 9', 90, 'update later', 3.5, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 5, 'Tiểu thuyết', 9);
INSERT INTO book VALUES (62, 'Tiểu thuyết 10', 95, 'update later', 3.5, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 5, 'Tiểu thuyết', 10);

-- Additional books to cover a wider range

-- Trinh thám
INSERT INTO book VALUES (63, 'Thám tử lừng danh Conan tập 15', 100, 'update later', 2.8, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 'Conan thám tử lừng danh', 15);
INSERT INTO book VALUES (64, 'Thám tử lừng danh Conan tập 16', 105, 'update later', 2.8, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 'Conan thám tử lừng danh', 16);

-- Tình cảm
INSERT INTO book VALUES (65, 'Chúng ta đã ly hôn tập 15', 60, 'update later', 2.8, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2, 'Chúng ta đã ly hôn', 15);
INSERT INTO book VALUES (66, 'Chúng ta đã ly hôn tập 16', 65, 'update later', 2.8, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2, 'Chúng ta đã ly hôn', 16);

-- Truyện tranh
INSERT INTO book VALUES (67, 'Truyện tranh 9', 40, 'update later', 2.1, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 3, 'Truyện tranh', 9);
INSERT INTO book VALUES (68, 'Truyện tranh 10', 45, 'update later', 2.1, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 3, 'Truyện tranh', 10);

-- Khoa học viễn tưởng
INSERT INTO book VALUES (69, 'Khoa học viễn tưởng 11', 90, 'update later', 3.2, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 4, 'Khoa học viễn tưởng', 11);
INSERT INTO book VALUES (70, 'Khoa học viễn tưởng 12', 95, 'update later', 3.2, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 4, 'Khoa học viễn tưởng', 12);

-- Tiểu thuyết
INSERT INTO book VALUES (71, 'Tiểu thuyết 11', 100, 'update later', 3.7, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 5, 'Tiểu thuyết', 11);
INSERT INTO book VALUES (72, 'Tiểu thuyết 12', 105, 'update later', 3.7, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 5, 'Tiểu thuyết', 12);

-- Continue filling in additional entries to reach a total of 100

-- Trinh thám
INSERT INTO book VALUES (73, 'Thám tử lừng danh Conan tập 17', 110, 'update later', 3.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 'Conan thám tử lừng danh', 17);
INSERT INTO book VALUES (74, 'Thám tử lừng danh Conan tập 18', 115, 'update later', 3.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 'Conan thám tử lừng danh', 18);

-- Tình cảm
INSERT INTO book VALUES (75, 'Chúng ta đã ly hôn tập 17', 70, 'update later', 3.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2, 'Chúng ta đã ly hôn', 17);
INSERT INTO book VALUES (76, 'Chúng ta đã ly hôn tập 18', 75, 'update later', 3.0, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2, 'Chúng ta đã ly hôn', 18);

-- Truyện tranh
INSERT INTO book VALUES (77, 'Truyện tranh 11', 50, 'update later', 2.2, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 3, 'Truyện tranh', 11);
INSERT INTO book VALUES (78, 'Truyện tranh 12', 55, 'update later', 2.2, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3, 3, 'Truyện tranh', 12);

-- Khoa học viễn tưởng
INSERT INTO book VALUES (79, 'Khoa học viễn tưởng 13', 100, 'update later', 3.5, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 4, 'Khoa học viễn tưởng', 13);
INSERT INTO book VALUES (80, 'Khoa học viễn tưởng 14', 105, 'update later', 3.5, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4, 4, 'Khoa học viễn tưởng', 14);

-- Tiểu thuyết
INSERT INTO book VALUES (81, 'Tiểu thuyết 13', 110, 'update later', 3.8, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 5, 'Tiểu thuyết', 13);
INSERT INTO book VALUES (82, 'Tiểu thuyết 14', 115, 'update later', 3.8, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5, 5, 'Tiểu thuyết', 14);

-- Final entries
-- Trinh thám
INSERT INTO book VALUES (83, 'Thám tử lừng danh Conan tập 19', 120, 'update later', 3.2, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 'Conan thám tử lừng danh', 19);
INSERT INTO book VALUES (84, 'Thám tử lừng danh Conan tập 20', 125, 'update later', 3.2, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, 'Conan thám tử lừng danh', 20);

-- Tình cảm
INSERT INTO book VALUES (85, 'Chúng ta đã ly hôn tập 19', 80, 'update later', 3.2, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2, 'Chúng ta đã ly hôn', 19);
INSERT INTO book VALUES (86, 'Chúng ta đã ly hôn tập 20', 85, 'update later', 3.2, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2, 2, 'Chúng ta đã ly hôn', 20);