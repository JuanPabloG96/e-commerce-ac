import { loadTemplate, manageMenu, isUserAuthenticated } from "/e-commerce/client/src/js/utils/helpers.js";
import { header, footer, headerUrl, footerUrl } from "/e-commerce/client/src/js/utils/constants.js";
import { editShippingBtn, editPaymentBtn, editShippingForm, editPaymentForm } from "/e-commerce/client/src/js/utils/constants.js";
import { showShippingForm, showPaymentForm } from "/e-commerce/client/src/js/utils/helpers.js";

/* Carga de templates */
loadTemplate(headerUrl, header);
loadTemplate(footerUrl, footer);
/* Manejo de menu */
manageMenu();

// Manejo de cambios de sección
editShippingBtn.addEventListener("click", () => showShippingForm(editShippingBtn, editPaymentBtn, editShippingForm, editPaymentForm));
editPaymentBtn.addEventListener("click", () => showPaymentForm(editShippingBtn, editPaymentBtn, editShippingForm, editPaymentForm));

// Muestra el formulario de dirección por defecto y aplica la clase 'selected' al botón correspondiente
showShippingForm(editShippingBtn, editPaymentBtn, editShippingForm, editPaymentForm);
