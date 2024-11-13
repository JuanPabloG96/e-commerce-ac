import { loadTemplate, manageMenu, isUserAuthenticated } from "/e-commerce/client/src/js/utils/helpers.js";
import { header, footer, headerUrl, footerUrl } from "/e-commerce/client/src/js/utils/constants.js";

/* Carga de templates */
if (isUserAuthenticated()) {
  loadTemplate(userHeaderUrl, header);
} else {
  loadTemplate(headerUrl, header);
}
loadTemplate(footerUrl, footer);
/* Manejo de menu */
manageMenu();

if (isUserAuthenticated()) {
  logout();
}