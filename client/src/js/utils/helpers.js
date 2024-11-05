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

export function loadProducts(products, container, component) {
  // Crea un fragmento HTML para evitar múltiples re-renderizaciones
  let html = '';
  products.forEach(product => {
    html += component(product);
  });
  // Inserta el fragmento de una sola vez en el contenedor
  container.innerHTML = html;
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

export function showShippingForm(editShippingBtn, editPaymentBtn, editShippingForm, editPaymentForm) {
  editShippingForm.classList.remove("hidden");
  editPaymentForm.classList.add("hidden");
  editShippingBtn.classList.add("selected");
  editPaymentBtn.classList.remove("selected");
}

export function showPaymentForm(editShippingBtn, editPaymentBtn, editShippingForm, editPaymentForm) {
  editPaymentForm.classList.remove("hidden");
  editShippingForm.classList.add("hidden");
  editPaymentBtn.classList.add("selected");
  editShippingBtn.classList.remove("selected");
}