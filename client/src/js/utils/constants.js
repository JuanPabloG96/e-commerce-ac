export const orders = [
  {
    order_id: 12345,
    created_at: "2024-10-12",
    total: 1200,
    status: "Entregado"
  },
  {
    order_id: 67890,
    created_at: "2024-10-05",
    total: 800,
    status: "En camino"
  },
  {
    order_id: 11223,
    created_at: "2024-10-01",
    total: 950,
    status: "Procesando"
  }
];

export const header = document.getElementById("header");
export const headerUrl = "/e-commerce/client/src/templates/header.html";
export const userHeaderUrl = "/e-commerce/client/src/templates/user-header.html";
export const footer = document.getElementById("footer");
export const footerUrl = "/e-commerce/client/src/templates/footer.html";
export const productsContainer = document.getElementById("products-container");
export const loginSection = document.getElementById("login");
export const registerSection = document.getElementById("register");
export const registerLink = document.querySelector('a[href="#register"]');
export const loginLink = document.querySelector('a[href="#login"]');
export const editShippingBtn = document.getElementById("edit-shipping-btn");
export const editPaymentBtn = document.getElementById("edit-payment-btn");
export const editShippingForm = document.getElementById("edit-shipping-form");
export const editPaymentForm = document.getElementById("edit-payment-form");
export const ordersContainer = document.getElementById("orders-container");

export const API_URL = "http://localhost:8085/api";

