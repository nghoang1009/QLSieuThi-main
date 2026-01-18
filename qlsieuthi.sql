DROP DATABASE IF EXISTS QLSieuThi;
CREATE DATABASE QLSieuThi;
USE QLSieuThi;


-- =================== DANH MỤC ===================
CREATE TABLE danhmuc (
  maDM INT PRIMARY KEY AUTO_INCREMENT,
  tenDM VARCHAR(32)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO danhmuc (maDM, tenDM) VALUES
(1, 'Thực phẩm tươi sống'),
(2, 'Đồ uống'),
(3, 'Bánh kẹo'),
(4, 'Gia vị'),
(5, 'Đồ dùng gia đình'),
(6, 'Chăm sóc cá nhân');


-- =================== NHÀ CUNG CẤP ===================
CREATE TABLE nhacungcap (
  maNCC INT PRIMARY KEY AUTO_INCREMENT,
  tenNCC VARCHAR(32),
  diaChi VARCHAR(64),
  thanhPho VARCHAR(32)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO nhacungcap (maNCC, tenNCC, diaChi, thanhPho) VALUES
(1, 'Công ty TNHH Thực phẩm Sạch', '45 Minh Khai, Hai Bà Trưng', 'Hà Nội'),
(2, 'Công ty CP Đồ uống Việt', '78 Phạm Ngọc Thạch, Đống Đa', 'Hà Nội'),
(3, 'Công ty TNHH Bánh kẹo Hà Nội', '123 Giảng Võ, Ba Đình', 'Hà Nội'),
(4, 'Công ty CP Gia vị Việt Nam', '56 Lê Duẩn, Hoàn Kiếm', 'Hà Nội'),
(5, 'Công ty TNHH Hóa mỹ phẩm', '89 Nguyễn Chí Thanh, Đống Đa', 'Hà Nội');


-- =================== KHUYẾN MÃI ===================
CREATE TABLE khuyenmai (
  maKhM INT PRIMARY KEY AUTO_INCREMENT,
  tenKhM VARCHAR(32),
  phanTramGiam INT,
  ngayHieuLuc DATE,
  ngayKetThuc DATE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO khuyenmai (maKhM, tenKhM, phanTramGiam, ngayHieuLuc, ngayKetThuc) VALUES
(1, 'Giảm giá cuối tuần', 10, '2024-12-20', '2024-12-22'),
(2, 'Khuyến mãi Noel', 15, '2024-12-24', '2024-12-26'),
(3, 'Giảm giá đầu năm', 20, '2025-01-01', '2025-01-07'),
(4, 'Flash sale', 25, '2024-12-23', '2024-12-23');


-- =================== TÀI KHOẢN ===================
CREATE TABLE taikhoan (
  maTK INT PRIMARY KEY AUTO_INCREMENT,
  tenTK VARCHAR(32),
  matKhau VARCHAR(64),
  chucVu VARCHAR(16)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO taikhoan (maTK, tenTK, matKhau, chucVu) VALUES
(1, 'nv001', '123456', 'nhanvien'),
(2, 'nv002', '123456', 'nhanvien'),
(3, 'nv003', '123456', 'nhanvien'),
(4, 'nv004', '123456', 'nhanvien'),
(5, 'nv005', '123456', 'nhanvien'),
(6, 'admin', '123456', 'admin');


-- =================== NHÂN VIÊN ===================
CREATE TABLE nhanvien (
  maNV INT UNIQUE,
  ten VARCHAR(32),
  ngaySinh DATE,
  gioiTinh VARCHAR(10),
  sdt VARCHAR(12),
  diaChi VARCHAR(128),
  CONSTRAINT nhanvien_ibfk_1 FOREIGN KEY (maNV) REFERENCES taikhoan (maTK) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO nhanvien (maNV, ten, ngaySinh, gioiTinh, sdt, diaChi) VALUES
(1, 'Đỗ Thị Hoa', '1995-03-15', 'Nữ', '0967890123', '56 Xã Đàn, Đống Đa, Hà Nội'),
(2, 'Vũ Văn Hùng', '1992-07-20', 'Nam', '0978901234', '89 Láng Hạ, Ba Đình, Hà Nội'),
(3, 'Ngô Thị Lan', '1998-11-08', 'Nữ', '0989012345', '23 Kim Mã, Ba Đình, Hà Nội'),
(4, 'Bùi Văn Minh', '1994-05-25', 'Nam', '0990123456', '67 Tây Sơn, Đống Đa, Hà Nội'),
(5, 'Đinh Thị Nga', '1996-09-12', 'Nữ', '0901234567', '145 Chùa Bộc, Đống Đa, Hà Nội'),
(6, 'Admin', '1990-01-01', 'Nam', '0900000000', 'Hà Nội');


-- =================== KHÁCH HÀNG ===================
CREATE TABLE khachhang (
  maKH INT PRIMARY KEY AUTO_INCREMENT,
  ten VARCHAR(32),
  sdt VARCHAR(12),
  diachi VARCHAR(128),
  CONSTRAINT khachhang_ibfk_1 FOREIGN KEY (maKH) REFERENCES taikhoan (maTK) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO khachhang (maKH, ten, sdt, diachi) VALUES
(7, 'Nguyễn Văn An', '0912345678', '123 Phố Huế, Hai Bà Trưng, Hà Nội'),
(8, 'Trần Thị Bình', '0923456789', '45 Đường Láng, Đống Đa, Hà Nội'),
(9, 'Lê Văn Cường', '0934567890', '78 Nguyễn Trãi, Thanh Xuân, Hà Nội'),
(10, 'Phạm Thị Dung', '0945678901', '12 Giải Phóng, Hoàng Mai, Hà Nội'),
(11, 'Hoàng Văn Em', '0956789012', '234 Trường Chinh, Thanh Xuân, Hà Nội');


-- =================== SẢN PHẨM ===================
CREATE TABLE sanpham (
  maSP INT PRIMARY KEY AUTO_INCREMENT,
  maDM INT,
  maNCC INT,
  tenSP VARCHAR(32),
  gia INT,
  soLuong INT,
  CONSTRAINT sanpham_ibfk_1 FOREIGN KEY (maDM) REFERENCES danhmuc (maDM) ON DELETE CASCADE,
  CONSTRAINT sanpham_ibfk_2 FOREIGN KEY (maNCC) REFERENCES nhacungcap (maNCC) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO sanpham (maSP, maDM, maNCC, tenSP, gia, soLuong) VALUES
(1, 1, 1, 'Thịt heo ba chỉ', 120000, 48),
(2, 1, 1, 'Cá thu tươi', 150000, 30),
(3, 1, 1, 'Rau cải xanh', 15000, 100),
(4, 2, 2, 'Nước ngọt Coca Cola', 10000, 200),
(5, 2, 2, 'Bia Hà Nội', 12000, 150),
(6, 2, 2, 'Nước khoáng Lavie', 5000, 300),
(7, 3, 3, 'Bánh quy Cosy', 25000, 80),
(8, 3, 3, 'Kẹo Alpenliebe', 35000, 60),
(9, 3, 3, 'Bánh snack Oishi', 8000, 120),
(10, 4, 4, 'Nước mắm Nam Ngư', 45000, 70),
(11, 4, 4, 'Dầu ăn Simply', 55000, 90),
(12, 4, 4, 'Muối I-ốt', 8000, 150),
(13, 5, 5, 'Nước rửa chén Sunlight', 32000, 98),
(14, 5, 5, 'Bột giặt OMO', 95000, 60),
(15, 6, 5, 'Dầu gội Head & Shoulders', 120000, 50),
(16, 6, 5, 'Kem đánh răng PS', 28000, 80);


-- =================== KHUYẾN MÃIs ===================
CREATE TABLE spkhuyenmai (
  maSP INT,
  maKhM INT,
  CONSTRAINT spkhuyenmai_ibfk_1 FOREIGN KEY (maSP) REFERENCES sanpham (maSP) ON DELETE CASCADE,
  CONSTRAINT spkhuyenmai_ibfk_2 FOREIGN KEY (maKhM) REFERENCES khuyenmai (maKhM) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO spkhuyenmai (maSP, maKhM) VALUES
(1, 1), (2, 1), (4, 2), (5, 2), (7, 2), (8, 2),
(10, 3), (11, 3), (13, 4), (14, 4), (15, 4);


-- =================== HÓA ĐƠNs ===================
CREATE TABLE hoadon (
  maHoaDon INT PRIMARY KEY AUTO_INCREMENT,
  maNV INT,
  ngayThanhToan DATE,
  CONSTRAINT hoadon_ibfk_1 FOREIGN KEY (maNV) REFERENCES nhanvien (maNV) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO hoadon (maHoaDon, maNV, ngayThanhToan) VALUES
(1, 1, '2024-12-18'),
(2, 2, '2024-12-19'),
(3, 3, '2024-12-20'),
(4, 4, '2024-12-21');


-- =================== ĐỒ TRONG HÓA ĐƠN ===================
CREATE TABLE item_hoadon (
  maItemHoaDon INT PRIMARY KEY AUTO_INCREMENT,
  maHoaDon INT,
  tenSP VARCHAR(32),
  gia INT,
  soLuong INT,
  phanTramGiam INT,
  CONSTRAINT item_hoadon_ibfk_1 FOREIGN KEY (maHoaDon) REFERENCES hoadon (maHoaDon) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO item_hoadon (maItemHoaDon, maHoaDon, tenSP, gia, soLuong, phanTramGiam) VALUES
(1, 1, 'Cá thu tươi', 150000, 2, 0),
(2, 1, 'Rau cải xanh', 15000, 5, 0),
(3, 2, 'Kẹo Alpenliebe', 35000, 3, 15),
(4, 3, 'Dầu ăn Simply', 55000, 2, 0),
(5, 4, 'Dầu gội Head & Shoulders', 120000, 1, 0),
(6, 2, 'Thịt heo ba chỉ', 120000, 1, 0),
(7, 3, 'Nước rửa chén Sunlight', 32000, 2, 0),
(8, 1, 'Thịt heo ba chỉ', 120000, 1, 0);


-- =================== GIỎ HÀNG ===================
CREATE TABLE giohang (
  maGioHang INT PRIMARY KEY AUTO_INCREMENT,
  maKH INT,
  ngayTao DATE,
  CONSTRAINT giohang_ibfk_1 FOREIGN KEY (maKH) REFERENCES khachhang (maKH) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO giohang (maGioHang, maKH, ngayTao) VALUES
(1, 7, '2024-12-20'),
(2, 8, '2024-12-21'),
(3, 9, '2024-12-22');


-- =================== ĐỒ TRONG GIỎ HÀNG ===================
CREATE TABLE item_giohang (
  maItemGioHang INT PRIMARY KEY AUTO_INCREMENT,
  maGioHang INT,
  maSP INT,
  soLuong INT,
  maKhM INT,
  CONSTRAINT item_giohang_ibfk_1 FOREIGN KEY (maKhM) REFERENCES khuyenmai (maKhM) ON DELETE CASCADE,
  CONSTRAINT item_giohang_ibfk_2 FOREIGN KEY (maGioHang) REFERENCES giohang (maGioHang) ON DELETE CASCADE,
  CONSTRAINT item_giohang_ibfk_3 FOREIGN KEY (maSP) REFERENCES sanpham (maSP) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO item_giohang (maItemGioHang, maGioHang, maSP, soLuong, maKhM) VALUES
(1, 1, 1, 2, 1),
(2, 1, 4, 3, NULL),
(3, 2, 7, 5, 2),
(4, 2, 10, 1, NULL),
(5, 3, 13, 2, NULL);


-- =================== GIAO HÀNG ===================
CREATE TABLE giaohang (
  maGiaoHang INT PRIMARY KEY AUTO_INCREMENT,
  maKH INT,
  ngayTao DATE,
  tinhTrang VARCHAR(16),
  CONSTRAINT giaohang_ibfk_1 FOREIGN KEY (maKH) REFERENCES khachhang (maKH) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO giaohang (maGiaoHang, maKH, ngayTao, tinhTrang) VALUES
(1, 7, '2024-12-18', 'Đã giao'),
(2, 8, '2024-12-19', 'Đang giao'),
(3, 10, '2024-12-21', 'Chờ xử lý'),
(4, 11, '2024-12-22', 'Đã giao');


-- =================== ĐỒ ĐƯỢC GIAO ===================
CREATE TABLE item_giaohang (
  maItemGiaoHang INT PRIMARY KEY AUTO_INCREMENT,
  maGiaoHang INT,
  maSP INT,
  soLuong INT,
  maKhM INT,
  CONSTRAINT item_giaohang_ibfk_1 FOREIGN KEY (maKhM) REFERENCES khuyenmai (maKhM) ON DELETE CASCADE,
  CONSTRAINT item_giaohang_ibfk_2 FOREIGN KEY (maGiaoHang) REFERENCES giaohang (maGiaoHang) ON DELETE CASCADE,
  CONSTRAINT item_giaohang_ibfk_3 FOREIGN KEY (maSP) REFERENCES sanpham (maSP) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO item_giaohang (maItemGiaoHang, maGiaoHang, maSP, soLuong, maKhM) VALUES
(1, 1, 2, 2, NULL),
(2, 1, 3, 5, NULL),
(3, 2, 8, 3, 2),
(4, 3, 11, 2, NULL),
(5, 4, 15, 1, NULL);
