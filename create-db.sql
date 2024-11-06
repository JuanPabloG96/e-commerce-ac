CREATE DATABASE IF NOT EXISTS chilemonroll_db;
USE chilemonroll_db;

CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS products (
    products_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description TEXT,
    stock INT DEFAULT 0,
    img_src VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS cart (
    cart_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    status ENUM('active', 'completed', 'abandoned') DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)  -- Asegúrate de agregar la relación
);

-- Crear la tabla de artículos del carrito si no existe
CREATE TABLE IF NOT EXISTS cart_items (
    cart_items_id INT AUTO_INCREMENT PRIMARY KEY,
    cart_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (cart_id) REFERENCES cart(cart_id),
    FOREIGN KEY (product_id) REFERENCES products(products_id)
);

CREATE TABLE IF NOT EXISTS shipping (
    shipping_id INT AUTO_INCREMENT PRIMARY KEY,
    cart_id INT NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    country VARCHAR(100) NOT NULL,
    status ENUM('Pending', 'Shipped', 'Delivered', 'Cancelled') DEFAULT 'Pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (cart_id) REFERENCES cart(cart_id)
);

CREATE TABLE IF NOT EXISTS payment (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    cart_id INT NOT NULL,
    shipping_id INT NOT NULL,
    card_number VARCHAR(255) NOT NULL,
    card_expiration VARCHAR(5) NOT NULL,
    payment_method ENUM('Credit Card', 'Debit Card') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (cart_id) REFERENCES cart(cart_id),
    FOREIGN KEY (shipping_id) REFERENCES shipping(shipping_id)
);

CREATE TABLE IF NOT EXISTS orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    cart_id INT NOT NULL,
    shipping_id INT,
    payment_id INT,
    total_amount DECIMAL(10, 2) NOT NULL,
    order_status ENUM('Pending', 'Completed', 'Cancelled') DEFAULT 'Pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (cart_id) REFERENCES cart(cart_id),
    FOREIGN KEY (shipping_id) REFERENCES shipping(shipping_id),
    FOREIGN KEY (payment_id) REFERENCES payment(payment_id)
);

INSERT INTO products (products_id, name, category, price, stock, description, img_src) VALUES
(1, 'Cinnamoroll Plush Toy', 'Juguetes', 25.99, 15, 'Peluche suave y adorable de Cinnamoroll, perfecto para fans.', '/e-commerce/client/assets/products/cinnamoroll_plush_toy.webp'),
(2, 'Cinnamoroll Mug', 'Vajilla', 12.99, 30, 'Taza de cerámica con un lindo diseño de Cinnamoroll.', '/e-commerce/client/assets/products/cinnamoroll_mug.webp'),
(3, 'Cinnamoroll Backpack', 'Accesorios', 45.0, 10, 'Mochila adorable de Cinnamoroll para llevar tus cosas esenciales.', '/e-commerce/client/assets/products/cinnamoroll_backpack.jpg'),
(4, 'Cinnamoroll Keychain', 'Accesorios', 5.99, 50, 'Llavero pequeño con la adorable cara de Cinnamoroll.', '/e-commerce/client/assets/products/cinnamoroll_keychain.webp'),
(5, 'Cinnamoroll T-shirt', 'Ropa', 19.99, 20, 'Camiseta cómoda con un estampado colorido de Cinnamoroll.', '/e-commerce/client/assets/products/cinnamoroll_tshirt.webp'),
(6, 'Cinnamoroll Blanket', 'Decoración del hogar', 30.99, 12, 'Manta cálida y acogedora con el diseño de Cinnamoroll.', '/e-commerce/client/assets/products/cinnamoroll_blanket.jpg'),
(7, 'Cinnamoroll Water Bottle', 'Vajilla', 15.0, 25, 'Botella de acero inoxidable con el diseño de Cinnamoroll.', '/e-commerce/client/assets/products/cinnamoroll_water_bottle.jpg'),
(8, 'Cinnamoroll Notebook', 'Papelería', 8.99, 40, 'Cuaderno con adorables diseños de Cinnamoroll en cada página.', '/e-commerce/client/assets/products/cinnamoroll_notebook.jpg'),
(9, 'Cinnamoroll Pillow', 'Decoración del hogar', 18.0, 15, 'Cojín suave con la cara de Cinnamoroll.', '/e-commerce/client/assets/products/cinnamoroll_pillow.jpg'),
(10, 'Cinnamoroll Phone Case', 'Accesorios', 10.99, 30, 'Funda protectora para teléfono con temática de Cinnamoroll.', '/e-commerce/client/assets/products/cinnamoroll_phone_case.webp'),
(11, 'Cinnamoroll Tote Bag', 'Accesorios', 16.5, 18, 'Bolsa ecológica con la imagen de Cinnamoroll.', '/e-commerce/client/assets/products/cinnamoroll_tote_bag.webp'),
(12, 'Cinnamoroll Stickers', 'Papelería', 3.99, 100, 'Paquete de 20 pegatinas con varias poses de Cinnamoroll.', '/e-commerce/client/assets/products/cinnamoroll_stickers.jpg'),
(13, 'Cinnamoroll Pen Set', 'Papelería', 7.99, 40, 'Set de bolígrafos con diseños de Cinnamoroll.', '/e-commerce/client/assets/products/cinnamoroll_pen_set.jpg'),
(14, 'Cinnamoroll Hoodie', 'Ropa', 39.99, 10, 'Sudadera cómoda con un gran estampado de Cinnamoroll.', '/e-commerce/client/assets/products/cinnamoroll_hoodie.jpg'),
(15, 'Cinnamoroll Socks', 'Ropa', 4.99, 60, 'Calcetines cómodos con la cara de Cinnamoroll.', '/e-commerce/client/assets/products/cinnamoroll_socks.webp'),
(16, 'Cinnamoroll Wall Poster', 'Decoración del hogar', 12.0, 25, 'Póster grande de pared con un lindo diseño de Cinnamoroll.', '/e-commerce/client/assets/products/cinnamoroll_wall_poster.jpg'),
(17, 'Cinnamoroll Hair Clips', 'Accesorios', 6.5, 35, 'Set de 4 clips para el cabello con adornos de Cinnamoroll.', '/e-commerce/client/assets/products/cinnamoroll_hair_clips.jpg'),
(18, 'Cinnamoroll Lunch Box', 'Vajilla', 14.99, 20, 'Lonchera compacta con el lindo diseño de Cinnamoroll.', '/e-commerce/client/assets/products/cinnamoroll_lunch_box.webp'),
(19, 'Cinnamoroll Face Mask', 'Ropa', 9.0, 50, 'Mascarilla con un patrón de Cinnamoroll.', '/e-commerce/client/assets/products/cinnamoroll_face_mask.jpg'),
(20, 'Cinnamoroll Mouse Pad', 'Papelería', 11.5, 30, 'Alfombrilla de ratón con un diseño suave de Cinnamoroll.', '/e-commerce/client/assets/products/cinnamoroll_mouse_pad.webp');
