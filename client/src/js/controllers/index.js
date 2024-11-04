import { productCard } from "/e-commerce/client/src/js/components/productCard.js";
import { loadTemplate, manageMenu, loadFeaturedProducts } from "/e-commerce/client/src/js/utils/helpers.js";
import { header, footer, headerUrl, footerUrl, API_URL, products } from "/e-commerce/client/src/js/utils/constants.js";
import { fetchProducts } from "/e-commerce/client/src/js/services/apiService.js";

const productsContainer = document.getElementById("products-container");

/* Carga de templates */
loadTemplate(headerUrl, header);
loadTemplate(footerUrl, footer);
/* Manejo de menu */
manageMenu();

// Obtener todos los productos
/* const products = await fetchProducts(API_URL); */

/* Carga de productos destacados */
loadFeaturedProducts(products, productsContainer, productCard);

