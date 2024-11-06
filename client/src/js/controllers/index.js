import { productCard } from "/e-commerce/client/src/js/components/productCard.js";
import { loadTemplate, manageMenu, loadFeaturedProducts } from "/e-commerce/client/src/js/utils/helpers.js";
import { header, footer, headerUrl, footerUrl, API_URL, productsContainer, userHeaderUrl } from "/e-commerce/client/src/js/utils/constants.js";
import { fetchProducts } from "/e-commerce/client/src/js/services/apiService.js";

let userActive = true;

/* Carga de templates */
if (userActive) {
  loadTemplate(userHeaderUrl, header);
} else {
  loadTemplate(headerUrl, header);
}
loadTemplate(footerUrl, footer);
/* Manejo de menu */
manageMenu();

// Función para inicializar la página y cargar los productos
async function loadProducts() {
  const products = await fetchProducts(API_URL);
  loadFeaturedProducts(products, productsContainer, productCard);
}
document.addEventListener("DOMContentLoaded", () => {
  loadProducts();
});

window.toggleMenu = function () {
  const menu = document.getElementById("menu");
  console.log(menu);
}
