import { loadTemplate } from '/e-commerce/client/src/js/utils/helpers.js'
import { headerUrl, header } from '/e-commerce/client/src/js/utils/constants.js'
import { footerUrl, footer } from '/e-commerce/client/src/js/utils/constants.js'
import { manageMenu } from '/e-commerce/client/src/js/utils/helpers.js'

// Carga de templates
loadTemplate(headerUrl, header);
loadTemplate(footerUrl, footer);

// Manejo de menu
manageMenu();