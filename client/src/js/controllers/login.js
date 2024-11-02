import { loadTemplate, manageMenu } from "/e-commerce/client/src/js/utils/helpers.js";
import { header, footer, headerUrl, footerUrl } from "/e-commerce/client/src/js/utils/constants.js";
import { loginSection, registerSection, registerLink, loginLink } from "/e-commerce/client/src/js/utils/constants.js";
import { showRegister, showLogin } from "/e-commerce/client/src/js/utils/helpers.js";

// Carga de templates
loadTemplate(headerUrl, header);
loadTemplate(footerUrl, footer);
// Manejo de menu
manageMenu();

// Manejo de cambios de sección
registerLink.addEventListener("focus", function () {
  showRegister(loginSection, registerSection);
});
loginLink.addEventListener("focus", function () {
  showLogin(loginSection, registerSection);
});


