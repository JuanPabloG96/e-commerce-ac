// Carga de templates
export async function loadTemplate(templateUrl, container) {
  fetch(templateUrl)
    .then(response => response.text())
    .then(data => {
      container.innerHTML = data;
    })
    .catch(error => {
      console.error("Error loading template:", error);
    });
}

// Manejo de menu
export function manageMenu() {
  window.toggleMenu = function () {
    const menu = document.getElementById("menu");
    menu.classList.toggle("hidden");
  }

  window.addEventListener("click", (event) => {
    if (!event.target.matches("#user-icon")) {
      const menu = document.getElementById("menu");
      menu.classList.add("hidden");
    }
  })
}

// Carga de productos destacados
export function loadFeaturedProducts(products, container, component) {
  const topProducts = [...products]
    .sort((a, b) => b.stock - a.stock)
    .slice(0, 4);

  // Crea un fragmento HTML para evitar múltiples re-renderizaciones
  let html = '';
  topProducts.forEach(product => {
    html += component(product);
  });
  // Inserta el fragmento de una sola vez en el contenedor
  container.innerHTML = html;
}

// Manejo de secciones
export function showRegister(loginSection, registerSection) {
  loginSection.classList.add("hidden");
  registerSection.classList.remove("hidden");
}
export function showLogin(loginSection, registerSection) {
  loginSection.classList.remove("hidden");
  registerSection.classList.add("hidden");
}