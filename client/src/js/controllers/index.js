import { productCard } from "/e-commerce/client/src/js/components/productCard.js";
import { loadTemplate, manageMenu, loadFeaturedProducts } from "/e-commerce/client/src/js/utils/helpers.js";
import { header, footer, headerUrl, footerUrl, API_URL, products, productsContainer, userHeaderUrl } from "/e-commerce/client/src/js/utils/constants.js";
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

// Obtener todos los productos
/* const products = await fetchProducts(API_URL); */

/* Carga de productos destacados */
loadFeaturedProducts(products, productsContainer, productCard);

window.toggleMenu = function () {
  const menu = document.getElementById("menu");
  console.log(menu);
}
