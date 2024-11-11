import { productCard } from "/e-commerce/client/src/js/components/productCard.js";
import { loadTemplate, manageMenu, loadProducts, isUserAuthenticated } from "/e-commerce/client/src/js/utils/helpers.js";
import { header, footer, headerUrl, footerUrl, userHeaderUrl, productsContainer } from "/e-commerce/client/src/js/utils/constants.js";
import { fetchData } from "/e-commerce/client/src/js/services/apiService.js";

const endpoint = "products";

// Initialize application
async function initApp() {
  // Load appropriate header
  if (isUserAuthenticated()) {
    await loadTemplate(userHeaderUrl, header);
  } else {
    await loadTemplate(headerUrl, header);
  }

  // Load footer
  await loadTemplate(footerUrl, footer);

  // Menu management
  manageMenu();

  // Fetch and load all products
  const products = await fetchData(endpoint, "GET", null, "Error al obtener los productos");
  loadProducts(products, productsContainer, productCard);
}

document.addEventListener("DOMContentLoaded", initApp);


