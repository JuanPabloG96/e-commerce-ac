import { products } from "/e-commerce/client/src/js/utils/constants.js";
import { productCard } from "/e-commerce/client/src/js/components/productCard.js";
import { loadTemplate, manageMenu, loadFeaturedProducts } from "/e-commerce/client/src/js/utils/helpers.js";
import { header, footer, headerUrl, footerUrl } from "/e-commerce/client/src/js/utils/constants.js";

const productsContainer = document.getElementById("products-container");

/* Carga de templates */
loadTemplate(headerUrl, header);
loadTemplate(footerUrl, footer);
/* Manejo de menu */
manageMenu();
/* Carga de productos destacados */
loadFeaturedProducts(products, productsContainer, productCard);