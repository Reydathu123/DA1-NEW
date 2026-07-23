USE [da1nhom9jav102]
GO

-- 1. HỦY BỎ CÁC KHÓA NGOẠI TRƯỚC ĐỂ TRÁNH LỖI KHI DROP TABLE
IF OBJECT_ID('dbo.cart_items', 'U') IS NOT NULL ALTER TABLE [dbo].[cart_items] DROP CONSTRAINT IF EXISTS FK_cart_items_carts;
IF OBJECT_ID('dbo.cart_items', 'U') IS NOT NULL ALTER TABLE [dbo].[cart_items] DROP CONSTRAINT IF EXISTS FK_cart_items_variants;
IF OBJECT_ID('dbo.carts', 'U') IS NOT NULL ALTER TABLE [dbo].[carts] DROP CONSTRAINT IF EXISTS FK_carts_users;
IF OBJECT_ID('dbo.customers', 'U') IS NOT NULL ALTER TABLE [dbo].[customers] DROP CONSTRAINT IF EXISTS FK_customers_users;
IF OBJECT_ID('dbo.order_details', 'U') IS NOT NULL ALTER TABLE [dbo].[order_details] DROP CONSTRAINT IF EXISTS FK_order_details_orders;
IF OBJECT_ID('dbo.order_details', 'U') IS NOT NULL ALTER TABLE [dbo].[order_details] DROP CONSTRAINT IF EXISTS FK_order_details_variants;
IF OBJECT_ID('dbo.orders', 'U') IS NOT NULL ALTER TABLE [dbo].[orders] DROP CONSTRAINT IF EXISTS FK_orders_users;
IF OBJECT_ID('dbo.rackets', 'U') IS NOT NULL ALTER TABLE [dbo].[rackets] DROP CONSTRAINT IF EXISTS FK_rackets_categories;
IF OBJECT_ID('dbo.rackets', 'U') IS NOT NULL ALTER TABLE [dbo].[rackets] DROP CONSTRAINT IF EXISTS FK_rackets_brands;
IF OBJECT_ID('dbo.reviews', 'U') IS NOT NULL ALTER TABLE [dbo].[reviews] DROP CONSTRAINT IF EXISTS FK_reviews_users;
IF OBJECT_ID('dbo.reviews', 'U') IS NOT NULL ALTER TABLE [dbo].[reviews] DROP CONSTRAINT IF EXISTS FK_reviews_rackets;
IF OBJECT_ID('dbo.variants', 'U') IS NOT NULL ALTER TABLE [dbo].[variants] DROP CONSTRAINT IF EXISTS FK_variants_rackets;
IF OBJECT_ID('dbo.variants', 'U') IS NOT NULL ALTER TABLE [dbo].[variants] DROP CONSTRAINT IF EXISTS FK_variants_colors;
IF OBJECT_ID('dbo.variants', 'U') IS NOT NULL ALTER TABLE [dbo].[variants] DROP CONSTRAINT IF EXISTS FK_variants_sizes;

-- 2. DROP CÁC BẢNG CŨ
DROP TABLE IF EXISTS [dbo].[cart_items];
DROP TABLE IF EXISTS [dbo].[carts];
DROP TABLE IF EXISTS [dbo].[order_details];
DROP TABLE IF EXISTS [dbo].[orders];
DROP TABLE IF EXISTS [dbo].[reviews];
DROP TABLE IF EXISTS [dbo].[customers];
DROP TABLE IF EXISTS [dbo].[variants];
DROP TABLE IF EXISTS [dbo].[rackets];
DROP TABLE IF EXISTS [dbo].[categories];
DROP TABLE IF EXISTS [dbo].[brands];
DROP TABLE IF EXISTS [dbo].[colors];
DROP TABLE IF EXISTS [dbo].[sizes];
DROP TABLE IF EXISTS [dbo].[users];
GO

-- 3. TẠO LẠI CÁC BẢNG VỚI ĐỊNH DẠNG NVARCHAR CHUẨN TIẾNG VIỆT
CREATE TABLE [dbo].[categories](
    [id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
    [name] [nvarchar](250) NOT NULL,
    [active] [bit] DEFAULT 1
);

CREATE TABLE [dbo].[brands](
    [id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
    [name] [nvarchar](250) NOT NULL,
    [logo] [nvarchar](250) NULL,
    [active] [bit] DEFAULT 1
);

CREATE TABLE [dbo].[rackets](
    [id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
    [category_id] [int] REFERENCES categories(id),
    [brand_id] [int] REFERENCES brands(id),
    [name] [nvarchar](250) NOT NULL,
    [price] [float] NOT NULL,
    [discount] [float] DEFAULT 0,
    [image] [nvarchar](250) NULL,
    [description] [nvarchar](max) NULL,
    [material] [nvarchar](100) NULL,
    [gender] [nvarchar](10) NULL,
    [active] [bit] DEFAULT 1
);

CREATE TABLE [dbo].[colors](
    [id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
    [name] [nvarchar](30) NOT NULL
);

CREATE TABLE [dbo].[sizes](
    [id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
    [size_value] [float] NOT NULL UNIQUE
);

CREATE TABLE [dbo].[variants](
    [id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
    [racket_id] [int] REFERENCES rackets(id) ON DELETE CASCADE,
    [color_id] [int] REFERENCES colors(id),
    [size_id] [int] REFERENCES sizes(id),
    [stock] [int] DEFAULT 0,
    [grip_size] [nvarchar](10) NULL,
    [flex] [nvarchar](30) NULL,
    [balance] [nvarchar](30) NULL,
    [weight] [float] NULL
);

CREATE TABLE [dbo].[users](
    [id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
    [email] [varchar](100) UNIQUE,
    [password] [varchar](255),
    [full_name] [nvarchar](60),
    [phone] [varchar](10),
    [address] [nvarchar](200),
    [role] [bit],
    [active] [bit] DEFAULT 1
);

CREATE TABLE [dbo].[customers](
    [id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
    [user_id] [int] UNIQUE REFERENCES users(id),
    [membership] [nvarchar](50),
    [points] [int] DEFAULT 0
);

CREATE TABLE [dbo].[orders](
    [id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
    [user_id] [int] REFERENCES users(id),
    [code] [varchar](10) NOT NULL,
    [created_at] [date],
    [total] [float],
    [status] [varchar](20),
    [payment_method] [nvarchar](50),
    [shipping_address] [nvarchar](200) NOT NULL,
    [shipping_date] [date],
    [delivery_date] [date]
);

CREATE TABLE [dbo].[order_details](
    [id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
    [order_id] [int] REFERENCES orders(id) ON DELETE CASCADE,
    [variant_id] [int] REFERENCES variants(id),
    [quantity] [int],
    [unit_price] [float] NOT NULL,
    [discount] [float] DEFAULT 0
);

CREATE TABLE [dbo].[reviews](
    [id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
    [user_id] [int] REFERENCES users(id),
    [racket_id] [int] REFERENCES rackets(id),
    [rating] [int] CHECK (rating BETWEEN 1 AND 5),
    [comment] [nvarchar](max) NULL,
    [created_at] [date]
);

CREATE TABLE [dbo].[carts](
    [id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
    [user_id] [int] UNIQUE REFERENCES users(id),
    [session_id] [varchar](100) NULL,
    [created_at] [date],
    [expires_at] [date]
);

CREATE TABLE [dbo].[cart_items](
    [id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
    [cart_id] [int] REFERENCES carts(id) ON DELETE CASCADE,
    [variant_id] [int] REFERENCES variants(id),
    [quantity] [int],
    [price] [float] NOT NULL
);
GO

-- 4. CHÈN DỮ LIỆU UNICODE CÓ DẤU TIẾNG VIỆT
SET IDENTITY_INSERT [dbo].[categories] ON
INSERT [dbo].[categories] ([id], [name], [active]) VALUES
(1, N'Vợt cầu lông', 1),
(2, N'Giày cầu lông', 1),
(3, N'Phụ kiện', 1)
SET IDENTITY_INSERT [dbo].[categories] OFF
GO

SET IDENTITY_INSERT [dbo].[brands] ON
INSERT [dbo].[brands] ([id], [name], [logo], [active]) VALUES
(1, N'Yonex', 'yonex.png', 1),
(2, N'Victor', 'victor.png', 1),
(3, N'Li-Ning', 'lining.png', 1)
SET IDENTITY_INSERT [dbo].[brands] OFF
GO

SET IDENTITY_INSERT [dbo].[colors] ON
INSERT [dbo].[colors] ([id], [name]) VALUES
(1, N'Đỏ'),
(2, N'Xanh'),
(3, N'Vàng'),
(4, N'Trắng')
SET IDENTITY_INSERT [dbo].[colors] OFF
GO

SET IDENTITY_INSERT [dbo].[rackets] ON
INSERT [dbo].[rackets] ([id], [category_id], [brand_id], [name], [price], [discount], [image], [description], [material], [gender], [active]) VALUES
(1, 1, 1, N'Vợt Yonex Astrox 99', 2900000, 10, 'astrox99.jpg', N'Vợt tấn công hàng đầu, phù hợp với người chơi mạnh', N'Graphite', N'Unisex', 1),
(2, 1, 2, N'Vợt Victor Thruster K', 2200000, 5, 'thrusterk.jpg', N'Vợt đa năng, kiểm soát tốt', N'Carbon', N'Nam', 1)
SET IDENTITY_INSERT [dbo].[rackets] OFF
GO

SET IDENTITY_INSERT [dbo].[variants] ON
INSERT [dbo].[variants] ([id], [racket_id], [color_id], [size_id], [stock], [grip_size], [flex], [balance], [weight]) VALUES
(1, 1, 1, NULL, 10, 'G5', N'Cứng', N'Đầu nặng', 85.0),
(2, 1, 2, NULL, 5, 'G5', N'Cứng', N'Đầu nặng', 85.0),
(3, 2, 3, NULL, 8, 'G4', N'Trung bình', N'Cân bằng', 88.0)
SET IDENTITY_INSERT [dbo].[variants] OFF
GO

SET IDENTITY_INSERT [dbo].[users] ON
INSERT [dbo].[users] ([id], [email], [password], [full_name], [phone], [address], [role], [active]) VALUES
(1, 'admin@gmail.com', '123', N'Quản trị viên', '0919123123', N'Hà Nội', 1, 1),
(2, 'customer@gmail.com', '123', N'Nguyễn Văn A', '0907828123', N'Hà Nội', 0, 1)
SET IDENTITY_INSERT [dbo].[users] OFF
GO

-- 5. TẠO STORED PROCEDURES THỐNG KÊ
DROP PROCEDURE IF EXISTS sp_revenue_by_day;
GO
CREATE PROCEDURE sp_revenue_by_day
    @from_date DATE,
    @to_date DATE
AS
BEGIN
    SET NOCOUNT ON;
    SELECT
        created_at AS revenue_date,
        COUNT(id) AS total_orders,
        ISNULL(SUM(total), 0) AS total_revenue
    FROM orders
    WHERE status = 'delivered' AND created_at BETWEEN @from_date AND @to_date
    GROUP BY created_at
    ORDER BY revenue_date;
END
GO

DROP PROCEDURE IF EXISTS sp_top_5_best_selling_rackets;
GO
CREATE PROCEDURE sp_top_5_best_selling_rackets
    @from_date DATE = NULL,
    @to_date DATE = NULL
AS
BEGIN
    SET NOCOUNT ON;
    SELECT TOP 5
        r.id AS racket_id,
        r.name AS racket_name,
        ISNULL(SUM(od.quantity), 0) AS total_quantity_sold,
        ISNULL(SUM(od.quantity * od.unit_price), 0) AS total_revenue
    FROM order_details od
         INNER JOIN variants v ON od.variant_id = v.id
         INNER JOIN rackets r ON v.racket_id = r.id
         INNER JOIN orders o ON od.order_id = o.id
    WHERE o.status = 'delivered'
      AND (@from_date IS NULL OR o.created_at >= @from_date)
      AND (@to_date IS NULL OR o.created_at <= @to_date)
    GROUP BY r.id, r.name
    ORDER BY total_quantity_sold DESC;
END
GO