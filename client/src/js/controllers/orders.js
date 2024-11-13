import { loadTemplate, manageMenu, isUserAuthenticated } from "/e-commerce/client/src/js/utils/helpers.js";
import { header, footer, headerUrl, footerUrl, orders, ordersContainer } from "/e-commerce/client/src/js/utils/constants.js";
import { orderCard } from "/e-commerce/client/src/js/components/orderCard.js";

/* Carga de templates */
if (isUserAuthenticated()) {
  loadTemplate(userHeaderUrl, header);
} else {
  loadTemplate(headerUrl, header);
}
loadTemplate(footerUrl, footer);
/* Manejo de menu */
manageMenu();

// Carga de ordenes
if (orders) {
  let html = '';
  orders.forEach(order => {
    html += orderCard(order);
  });
  ordersContainer.innerHTML = html;
} else {
  ordersContainer.innerHTML = "<h1>No hay pedidos</h1>";
}