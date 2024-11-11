import { productCard } from "/e-commerce/client/src/js/components/productCard.js";
import { loadTemplate, loadFeaturedProducts, manageMenu, isUserAuthenticated, logout } from "/e-commerce/client/src/js/utils/helpers.js";
import { header, footer, headerUrl, footerUrl, productsContainer, userHeaderUrl } from "/e-commerce/client/src/js/utils/constants.js";
import { fetchData } from "/e-commerce/client/src/js/services/apiService.js";

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
  // Load products
  try {
    const products = await fetchData("products", "GET", null, "Error al obtener los productos");
    loadFeaturedProducts(products, productsContainer, productCard);
  } catch (error) {
    console.error("Error loading products:", error);
  }
  if (isUserAuthenticated()) {
    logout();
  }
}

// Start app when DOM is ready
document.addEventListener("DOMContentLoaded", initApp);

