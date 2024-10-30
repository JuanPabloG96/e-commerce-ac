import { loadTemplate } from "/e-commerce/client/src/js/utils/helpers.js";

const header = document.getElementById("header");
const headerUrl = `src/templates/header.html`;
const footer = document.getElementById("footer");
const footerUrl = `src/templates/footer.html`;

loadTemplate(headerUrl, header);
loadTemplate(footerUrl, footer);