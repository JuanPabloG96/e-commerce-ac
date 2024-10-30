import { loadTemplate } from '/e-commerce/client/src/js/utils/helpers.js'
import { headerUrl, header } from '/e-commerce/client/src/js/utils/constants.js'
import { manageMenu } from '/e-commerce/client/src/js/utils/helpers.js'

// Carga de templates
loadTemplate(headerUrl, header);
manageMenu();

// Manejo de cambios de sección
const loginSection = document.getElementById("login");
const registerSection = document.getElementById("register");
const registerLink = document.querySelector('a[href="#register"]');
const loginLink = document.querySelector('a[href="#login"]');

function showRegister() {
  loginSection.classList.add("hidden");
  registerSection.classList.remove("hidden");
}

function showLogin() {
  registerSection.classList.add("hidden");
  loginSection.classList.remove("hidden");
}

registerLink.addEventListener("focus", function () {
  showRegister();
});

loginLink.addEventListener("focus", function () {
  showLogin();
});


