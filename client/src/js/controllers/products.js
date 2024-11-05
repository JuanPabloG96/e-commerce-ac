import { productCard } from "/e-commerce/client/src/js/components/productCard.js";
import { loadTemplate, manageMenu, loadProducts } from "/e-commerce/client/src/js/utils/helpers.js";
import { header, footer, headerUrl, footerUrl, productsContainer, products } from "/e-commerce/client/src/js/utils/constants.js";

// Carga de templates
loadTemplate(headerUrl, header);
loadTemplate(footerUrl, footer);
// Manejo de menu
manageMenu();

// Carga de productos
if (products) {
  loadProducts(products, productsContainer, productCard);
} else {
  productsContainer.innerHTML = "<h1>No hay productos</h1>";
}