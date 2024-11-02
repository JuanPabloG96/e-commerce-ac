import { loadTemplate, manageMenu } from "/e-commerce/client/src/js/utils/helpers.js";
import { header, footer, headerUrl, footerUrl } from "/e-commerce/client/src/js/utils/constants.js";

/* Carga de templates */
loadTemplate(headerUrl, header);
loadTemplate(footerUrl, footer);
/* Manejo de menu */
manageMenu();