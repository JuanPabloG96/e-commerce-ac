import { loadTemplate } from "/e-commerce/client/src/js/utils/helpers.js";
import { products } from "/e-commerce/client/src/js/utils/constants.js";
import { productCard } from "/e-commerce/client/src/js/components/productCard.js";

const header = document.getElementById("header");
const headerUrl = `src/templates/header.html`;
const footer = document.getElementById("footer");
const footerUrl = `src/templates/footer.html`;
const productsContainer = document.getElementById("products-container");

loadTemplate(headerUrl, header);
loadTemplate(footerUrl, footer);

products.forEach((product) => {
  productsContainer.innerHTML += productCard(product);
})
