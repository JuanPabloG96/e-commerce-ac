import { loadTemplate, manageMenu, showRegister, showLogin, showNotification, handleAuthResponse, isUserAuthenticated } from "/e-commerce/client/src/js/utils/helpers.js";
import { header, footer, headerUrl, userHeaderUrl, footerUrl } from "/e-commerce/client/src/js/utils/constants.js";
import { loginSection, registerSection, registerLink, loginLink } from "/e-commerce/client/src/js/utils/constants.js";
import { fetchData } from "/e-commerce/client/src/js/services/apiService.js";

async function initApp() {
  // Load templates
  if (isUserAuthenticated()) {
    await loadTemplate(userHeaderUrl, header);
  } else {
    await loadTemplate(headerUrl, header);
  }
  await loadTemplate(footerUrl, footer);
  manageMenu();

  // Get form elements after DOM is loaded
  const registerForm = document.getElementById("register-form");
  const loginForm = document.getElementById("login-form");

  // Section changes
  registerLink.addEventListener("focus", () => showRegister(loginSection, registerSection));
  loginLink.addEventListener("focus", () => showLogin(loginSection, registerSection));

  // Register form handling
  registerForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    const userData = {
      username: document.getElementById("reg-name").value,
      email: document.getElementById("reg-email").value,
      password: document.getElementById("reg-password").value
    };

    try {
      const response = await fetchData("users/register", "POST", userData, "Error al registrar usuario");
      if (response.sessionToken) {
        localStorage.setItem("userSession", response.sessionToken);
      }
      handleAuthResponse("¡Registro exitoso! Redirigiendo...");
    } catch (error) {
      showNotification(`Error al registrar usuario: ${error.message}`, false);
    }
  });

  // Login form handling
  loginForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    const loginData = {
      email: document.getElementById("email").value,
      password: document.getElementById("password").value
    };

    try {
      const response = await fetchData("users/login", "POST", loginData, "Error al iniciar sesión");
      if (response.sessionToken) {
        localStorage.setItem("userSession", response.sessionToken);
      }
      handleAuthResponse("¡Inicio de sesión exitoso! Redirigiendo...");
    } catch (error) {
      showNotification("Error al iniciar sesión: Usuario o contraseña incorrectos", false);
    }
  });

}

// Start app when DOM is ready
document.addEventListener("DOMContentLoaded", initApp);
