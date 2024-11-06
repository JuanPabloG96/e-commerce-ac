import { productCard } from "/e-commerce/client/src/js/components/productCard.js";
import { loadTemplate, manageMenu, showProducts, loadProducts } from "/e-commerce/client/src/js/utils/helpers.js";
import { header, footer, headerUrl, footerUrl, productsContainer, API_URL } from "/e-commerce/client/src/js/utils/constants.js";
import { fetchProducts } from "/e-commerce/client/src/js/services/apiService.js";

// Carga de templates
loadTemplate(headerUrl, header);
loadTemplate(footerUrl, footer);

// Manejo de menú
manageMenu();

// Llamada a la función de inicialización
document.addEventListener("DOMContentLoaded", () => {
  loadProducts(fetchProducts, showProducts, API_URL, productsContainer, productCard);
});

