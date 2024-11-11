import { loadTemplate, manageMenu, isUserAuthenticated } from "/e-commerce/client/src/js/utils/helpers.js";
import { header, footer, headerUrl, footerUrl, userHeaderUrl } from "/e-commerce/client/src/js/utils/constants.js";

// Load appropriate header
if (isUserAuthenticated()) {
  loadTemplate(userHeaderUrl, header);
} else {
  loadTemplate(headerUrl, header);
}

// Load footer
loadTemplate(footerUrl, footer);

/* Manejo de menu */
manageMenu();