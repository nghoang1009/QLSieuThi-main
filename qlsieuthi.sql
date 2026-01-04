-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th1 04, 2026 lúc 10:17 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `qlsieuthi`
--
DROP DATABASE IF EXISTS QLSieuThi;
CREATE DATABASE QLSieuThi;

USE QLSieuThi;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `danhmuc`
--

CREATE TABLE `danhmuc` (
  `maDM` int(11) NOT NULL,
  `tenDM` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `danhmuc`
--

INSERT INTO `danhmuc` (`maDM`, `tenDM`) VALUES
(1, 'Thực phẩm tươi sống'),
(2, 'Đồ uống'),
(3, 'Bánh kẹo'),
(4, 'Gia vị'),
(5, 'Đồ dùng gia đình'),
(6, 'Chăm sóc cá nhân');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `giaohang`
--

CREATE TABLE `giaohang` (
  `maGiaoHang` int(11) NOT NULL,
  `maKH` int(11) DEFAULT NULL,
  `ngayTao` date DEFAULT NULL,
  `tinhTrang` varchar(16) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `giaohang`
--

INSERT INTO `giaohang` (`maGiaoHang`, `maKH`, `ngayTao`, `tinhTrang`) VALUES
(1, 7, '2024-12-18', 'Đã giao'),
(2, 8, '2024-12-19', 'Đang giao'),
(3, 10, '2024-12-21', 'Chờ xử lý'),
(4, 11, '2024-12-22', 'Đã giao');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `giohang`
--

CREATE TABLE `giohang` (
  `maGioHang` int(11) NOT NULL,
  `maKH` int(11) DEFAULT NULL,
  `ngayTao` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `giohang`
--

INSERT INTO `giohang` (`maGioHang`, `maKH`, `ngayTao`) VALUES
(1, 7, '2024-12-20'),
(2, 8, '2024-12-21'),
(3, 9, '2024-12-22');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hoadon`
--

CREATE TABLE `hoadon` (
  `maHoaDon` int(11) NOT NULL,
  `maNV` int(11) DEFAULT NULL,
  `ngayThanhToan` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `hoadon`
--

INSERT INTO `hoadon` (`maHoaDon`, `maNV`, `ngayThanhToan`) VALUES
(1, 1, '2024-12-18'),
(2, 2, '2024-12-19'),
(3, 3, '2024-12-20'),
(4, 4, '2024-12-21');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `item_giaohang`
--

CREATE TABLE `item_giaohang` (
  `maItemGiaoHang` int(11) NOT NULL,
  `maGiaoHang` int(11) DEFAULT NULL,
  `maSP` int(11) DEFAULT NULL,
  `soLuong` int(11) DEFAULT NULL,
  `maKhM` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `item_giaohang`
--

INSERT INTO `item_giaohang` (`maItemGiaoHang`, `maGiaoHang`, `maSP`, `soLuong`, `maKhM`) VALUES
(1, 1, 2, 2, NULL),
(2, 1, 3, 5, NULL),
(3, 2, 8, 3, 2),
(4, 3, 11, 2, NULL),
(5, 4, 15, 1, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `item_giohang`
--

CREATE TABLE `item_giohang` (
  `maItemGioHang` int(11) NOT NULL,
  `maGioHang` int(11) DEFAULT NULL,
  `maSP` int(11) DEFAULT NULL,
  `soLuong` int(11) DEFAULT NULL,
  `maKhM` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `item_giohang`
--

INSERT INTO `item_giohang` (`maItemGioHang`, `maGioHang`, `maSP`, `soLuong`, `maKhM`) VALUES
(1, 1, 1, 2, 1),
(2, 1, 4, 3, NULL),
(3, 2, 7, 5, 2),
(4, 2, 10, 1, NULL),
(5, 3, 13, 2, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `item_hoadon`
--

CREATE TABLE `item_hoadon` (
  `maItemHoaDon` int(11) NOT NULL,
  `maHoaDon` int(11) DEFAULT NULL,
  `tenSP` varchar(32) DEFAULT NULL,
  `gia` int(11) DEFAULT NULL,
  `soLuong` int(11) DEFAULT NULL,
  `phanTramGiam` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `item_hoadon`
--

INSERT INTO `item_hoadon` (`maItemHoaDon`, `maHoaDon`, `tenSP`, `gia`, `soLuong`, `phanTramGiam`) VALUES
(1, 1, 'Cá thu tươi', 150000, 2, 0),
(2, 1, 'Rau cải xanh', 15000, 5, 0),
(3, 2, 'Kẹo Alpenliebe', 35000, 3, 15),
(4, 3, 'Dầu ăn Simply', 55000, 2, 0),
(5, 4, 'Dầu gội Head & Shoulders', 120000, 1, 0),
(6, 2, 'Thịt heo ba chỉ', 120000, 1, 0),
(7, 3, 'Nước rửa chén Sunlight', 32000, 2, 0),
(8, 1, 'Thịt heo ba chỉ', 120000, 1, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khachhang`
--

CREATE TABLE `khachhang` (
  `maKH` int(11) NOT NULL,
  `ten` varchar(32) DEFAULT NULL,
  `sdt` varchar(12) DEFAULT NULL,
  `diachi` varchar(128) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `khachhang`
--

INSERT INTO `khachhang` (`maKH`, `ten`, `sdt`, `diachi`) VALUES
(7, 'Nguyễn Văn An', '0912345678', '123 Phố Huế, Hai Bà Trưng, Hà Nội'),
(8, 'Trần Thị Bình', '0923456789', '45 Đường Láng, Đống Đa, Hà Nội'),
(9, 'Lê Văn Cường', '0934567890', '78 Nguyễn Trãi, Thanh Xuân, Hà Nội'),
(10, 'Phạm Thị Dung', '0945678901', '12 Giải Phóng, Hoàng Mai, Hà Nội'),
(11, 'Hoàng Văn Em', '0956789012', '234 Trường Chinh, Thanh Xuân, Hà Nội');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khuyenmai`
--

CREATE TABLE `khuyenmai` (
  `maKhM` int(11) NOT NULL,
  `tenKhM` varchar(32) DEFAULT NULL,
  `phanTramGiam` int(11) DEFAULT NULL,
  `ngayHieuLuc` date DEFAULT NULL,
  `ngayKetThuc` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `khuyenmai`
--

INSERT INTO `khuyenmai` (`maKhM`, `tenKhM`, `phanTramGiam`, `ngayHieuLuc`, `ngayKetThuc`) VALUES
(1, 'Giảm giá cuối tuần', 10, '2024-12-20', '2024-12-22'),
(2, 'Khuyến mãi Noel', 15, '2024-12-24', '2024-12-26'),
(3, 'Giảm giá đầu năm', 20, '2025-01-01', '2025-01-07'),
(4, 'Flash sale', 25, '2024-12-23', '2024-12-23');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhacungcap`
--

CREATE TABLE `nhacungcap` (
  `maNCC` int(11) NOT NULL,
  `tenNCC` varchar(32) DEFAULT NULL,
  `diaChi` varchar(64) DEFAULT NULL,
  `thanhPho` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `nhacungcap`
--

INSERT INTO `nhacungcap` (`maNCC`, `tenNCC`, `diaChi`, `thanhPho`) VALUES
(1, 'Công ty TNHH Thực phẩm Sạch', '45 Minh Khai, Hai Bà Trưng', 'Hà Nội'),
(2, 'Công ty CP Đồ uống Việt', '78 Phạm Ngọc Thạch, Đống Đa', 'Hà Nội'),
(3, 'Công ty TNHH Bánh kẹo Hà Nội', '123 Giảng Võ, Ba Đình', 'Hà Nội'),
(4, 'Công ty CP Gia vị Việt Nam', '56 Lê Duẩn, Hoàn Kiếm', 'Hà Nội'),
(5, 'Công ty TNHH Hóa mỹ phẩm', '89 Nguyễn Chí Thanh, Đống Đa', 'Hà Nội');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhanvien`
--

CREATE TABLE `nhanvien` (
  `maNV` int(11) DEFAULT NULL,
  `ten` varchar(32) DEFAULT NULL,
  `ngaySinh` date DEFAULT NULL,
  `gioiTinh` varchar(10) DEFAULT NULL,
  `sdt` varchar(12) DEFAULT NULL,
  `diaChi` varchar(128) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `nhanvien`
--

INSERT INTO `nhanvien` (`maNV`, `ten`, `ngaySinh`, `gioiTinh`, `sdt`, `diaChi`) VALUES
(1, 'Đỗ Thị Hoa', '1995-03-15', 'Nữ', '0967890123', '56 Xã Đàn, Đống Đa, Hà Nội'),
(2, 'Vũ Văn Hùng', '1992-07-20', 'Nam', '0978901234', '89 Láng Hạ, Ba Đình, Hà Nội'),
(3, 'Ngô Thị Lan', '1998-11-08', 'Nữ', '0989012345', '23 Kim Mã, Ba Đình, Hà Nội'),
(4, 'Bùi Văn Minh', '1994-05-25', 'Nam', '0990123456', '67 Tây Sơn, Đống Đa, Hà Nội'),
(5, 'Đinh Thị Nga', '1996-09-12', 'Nữ', '0901234567', '145 Chùa Bộc, Đống Đa, Hà Nội'),
(6, 'Admin', '1990-01-01', 'Nam', '0900000000', 'Hà Nội');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sanpham`
--

CREATE TABLE `sanpham` (
  `maSP` int(11) NOT NULL,
  `maDM` int(11) DEFAULT NULL,
  `maNCC` int(11) DEFAULT NULL,
  `tenSP` varchar(32) DEFAULT NULL,
  `gia` int(11) DEFAULT NULL,
  `soLuong` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `sanpham`
--

INSERT INTO `sanpham` (`maSP`, `maDM`, `maNCC`, `tenSP`, `gia`, `soLuong`) VALUES
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

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `spkhuyenmai`
--

CREATE TABLE `spkhuyenmai` (
  `maSP` int(11) DEFAULT NULL,
  `maKhM` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `spkhuyenmai`
--

INSERT INTO `spkhuyenmai` (`maSP`, `maKhM`) VALUES
(1, 1),
(2, 1),
(4, 2),
(5, 2),
(7, 2),
(8, 2),
(10, 3),
(11, 3),
(13, 4),
(14, 4),
(15, 4);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `taikhoan`
--

CREATE TABLE `taikhoan` (
  `maTK` int(11) NOT NULL,
  `tenTK` varchar(32) DEFAULT NULL,
  `matKhau` varchar(64) DEFAULT NULL,
  `chucVu` varchar(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `taikhoan`
--

INSERT INTO `taikhoan` (`maTK`, `tenTK`, `matKhau`, `chucVu`) VALUES
(1, 'nv001', '123456', '2'),
(2, 'nv002', '123456', '2'),
(3, 'nv003', '123456', '2'),
(4, 'nv004', '123456', '2'),
(5, 'nv005', '123456', '2'),
(6, 'admin', '123456', '1'),
(7, 'kh001', '123456', NULL),
(8, 'kh002', '123456', NULL),
(9, 'kh003', '123456', NULL),
(10, 'kh004', '123456', NULL),
(11, 'kh005', '123456', NULL);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `danhmuc`
--
ALTER TABLE `danhmuc`
  ADD PRIMARY KEY (`maDM`);

--
-- Chỉ mục cho bảng `giaohang`
--
ALTER TABLE `giaohang`
  ADD PRIMARY KEY (`maGiaoHang`),
  ADD KEY `maKH` (`maKH`);

--
-- Chỉ mục cho bảng `giohang`
--
ALTER TABLE `giohang`
  ADD PRIMARY KEY (`maGioHang`),
  ADD KEY `maKH` (`maKH`);

--
-- Chỉ mục cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  ADD PRIMARY KEY (`maHoaDon`),
  ADD KEY `maNV` (`maNV`);

--
-- Chỉ mục cho bảng `item_giaohang`
--
ALTER TABLE `item_giaohang`
  ADD PRIMARY KEY (`maItemGiaoHang`),
  ADD KEY `maKhM` (`maKhM`),
  ADD KEY `maGiaoHang` (`maGiaoHang`),
  ADD KEY `maSP` (`maSP`);

--
-- Chỉ mục cho bảng `item_giohang`
--
ALTER TABLE `item_giohang`
  ADD PRIMARY KEY (`maItemGioHang`),
  ADD KEY `maKhM` (`maKhM`),
  ADD KEY `maGioHang` (`maGioHang`),
  ADD KEY `maSP` (`maSP`);

--
-- Chỉ mục cho bảng `item_hoadon`
--
ALTER TABLE `item_hoadon`
  ADD PRIMARY KEY (`maItemHoaDon`),
  ADD KEY `maHoaDon` (`maHoaDon`);

--
-- Chỉ mục cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  ADD PRIMARY KEY (`maKH`);

--
-- Chỉ mục cho bảng `khuyenmai`
--
ALTER TABLE `khuyenmai`
  ADD PRIMARY KEY (`maKhM`);

--
-- Chỉ mục cho bảng `nhacungcap`
--
ALTER TABLE `nhacungcap`
  ADD PRIMARY KEY (`maNCC`);

--
-- Chỉ mục cho bảng `nhanvien`
--
ALTER TABLE `nhanvien`
  ADD UNIQUE KEY `maNV` (`maNV`);

--
-- Chỉ mục cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD PRIMARY KEY (`maSP`),
  ADD KEY `maDM` (`maDM`),
  ADD KEY `maNCC` (`maNCC`);

--
-- Chỉ mục cho bảng `spkhuyenmai`
--
ALTER TABLE `spkhuyenmai`
  ADD KEY `maSP` (`maSP`),
  ADD KEY `maKhM` (`maKhM`);

--
-- Chỉ mục cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD PRIMARY KEY (`maTK`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `danhmuc`
--
ALTER TABLE `danhmuc`
  MODIFY `maDM` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `giaohang`
--
ALTER TABLE `giaohang`
  MODIFY `maGiaoHang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `giohang`
--
ALTER TABLE `giohang`
  MODIFY `maGioHang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  MODIFY `maHoaDon` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `item_giaohang`
--
ALTER TABLE `item_giaohang`
  MODIFY `maItemGiaoHang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `item_giohang`
--
ALTER TABLE `item_giohang`
  MODIFY `maItemGioHang` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `item_hoadon`
--
ALTER TABLE `item_hoadon`
  MODIFY `maItemHoaDon` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  MODIFY `maKH` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT cho bảng `khuyenmai`
--
ALTER TABLE `khuyenmai`
  MODIFY `maKhM` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `nhacungcap`
--
ALTER TABLE `nhacungcap`
  MODIFY `maNCC` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  MODIFY `maSP` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  MODIFY `maTK` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `giaohang`
--
ALTER TABLE `giaohang`
  ADD CONSTRAINT `giaohang_ibfk_1` FOREIGN KEY (`maKH`) REFERENCES `khachhang` (`maKH`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `giohang`
--
ALTER TABLE `giohang`
  ADD CONSTRAINT `giohang_ibfk_1` FOREIGN KEY (`maKH`) REFERENCES `khachhang` (`maKH`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  ADD CONSTRAINT `hoadon_ibfk_1` FOREIGN KEY (`maNV`) REFERENCES `nhanvien` (`maNV`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `item_giaohang`
--
ALTER TABLE `item_giaohang`
  ADD CONSTRAINT `item_giaohang_ibfk_1` FOREIGN KEY (`maKhM`) REFERENCES `khuyenmai` (`maKhM`) ON DELETE CASCADE,
  ADD CONSTRAINT `item_giaohang_ibfk_2` FOREIGN KEY (`maGiaoHang`) REFERENCES `giaohang` (`maGiaoHang`) ON DELETE CASCADE,
  ADD CONSTRAINT `item_giaohang_ibfk_3` FOREIGN KEY (`maSP`) REFERENCES `sanpham` (`maSP`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `item_giohang`
--
ALTER TABLE `item_giohang`
  ADD CONSTRAINT `item_giohang_ibfk_1` FOREIGN KEY (`maKhM`) REFERENCES `khuyenmai` (`maKhM`) ON DELETE CASCADE,
  ADD CONSTRAINT `item_giohang_ibfk_2` FOREIGN KEY (`maGioHang`) REFERENCES `giohang` (`maGioHang`) ON DELETE CASCADE,
  ADD CONSTRAINT `item_giohang_ibfk_3` FOREIGN KEY (`maSP`) REFERENCES `sanpham` (`maSP`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `item_hoadon`
--
ALTER TABLE `item_hoadon`
  ADD CONSTRAINT `item_hoadon_ibfk_1` FOREIGN KEY (`maHoaDon`) REFERENCES `hoadon` (`maHoaDon`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `khachhang`
--
ALTER TABLE `khachhang`
  ADD CONSTRAINT `khachhang_ibfk_1` FOREIGN KEY (`maKH`) REFERENCES `taikhoan` (`maTK`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `nhanvien`
--
ALTER TABLE `nhanvien`
  ADD CONSTRAINT `nhanvien_ibfk_1` FOREIGN KEY (`maNV`) REFERENCES `taikhoan` (`maTK`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD CONSTRAINT `sanpham_ibfk_1` FOREIGN KEY (`maDM`) REFERENCES `danhmuc` (`maDM`) ON DELETE CASCADE,
  ADD CONSTRAINT `sanpham_ibfk_2` FOREIGN KEY (`maNCC`) REFERENCES `nhacungcap` (`maNCC`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `spkhuyenmai`
--
ALTER TABLE `spkhuyenmai`
  ADD CONSTRAINT `spkhuyenmai_ibfk_1` FOREIGN KEY (`maSP`) REFERENCES `sanpham` (`maSP`) ON DELETE CASCADE,
  ADD CONSTRAINT `spkhuyenmai_ibfk_2` FOREIGN KEY (`maKhM`) REFERENCES `khuyenmai` (`maKhM`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
