import { loadTemplate } from "/e-commerce/client/src/js/utils/helpers.js";
import { products } from "/e-commerce/client/src/js/utils/constants.js";
import { productCard } from "/e-commerce/client/src/js/components/productCard.js";
import { manageMenu } from "/e-commerce/client/src/js/utils/helpers.js";
import { headerUrl, footerUrl } from "/e-commerce/client/src/js/utils/constants.js";
import { header, footer } from "/e-commerce/client/src/js/utils/constants.js";

const productsContainer = document.getElementById("products-container");

/* Carga de templates */
loadTemplate(headerUrl, header);
loadTemplate(footerUrl, footer);

/* Carga de productos */
products.forEach((product) => {
  productsContainer.innerHTML += productCard(product);
})

/* Manejo de menu */
manageMenu();