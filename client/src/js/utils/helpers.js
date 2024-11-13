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
    const menu = document.getElementById("menu");
    const menuTrigger = event.target.closest('[onclick="toggleMenu()"]');

    if (!menuTrigger && menu) {
      menu.classList.add("hidden");
    }
  });
}


export function showProducts(products, container, component) {
  // Crea un fragmento HTML para evitar múltiples re-renderizaciones
  let html = '';
  products.forEach(product => {
    html += component(product);
  });
  // Inserta el fragmento de una sola vez en el contenedor
  container.innerHTML = html;
}

// Función para inicializar la página y cargar los productos
export function loadProducts(products, productsContainer, productCard) {
  if (products && products.length > 0) {
    showProducts(products, productsContainer, productCard);
  } else {
    productsContainer.innerHTML = "<h1>No hay productos disponibles</h1>";
  }
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

export function showNotification(message, isSuccess = true) {
  const notification = document.getElementById('notification');
  const notificationInner = notification.querySelector('div');
  const notificationMessage = document.getElementById('notification-message');

  notificationMessage.textContent = message;
  notificationInner.className = `max-w-md mx-auto m-4 p-4 rounded-lg shadow-lg text-center text-white font-semibold ${isSuccess ? 'bg-green-400' : 'bg-red-400'}`;
  notification.classList.remove('hidden');
  notification.classList.add('block');
}

export function handleAuthResponse(successMessage) {
  showNotification(successMessage, true);
  setTimeout(() => {
    window.location.href = "http://localhost:8080/e-commerce/client/";
  }, 1500);
}

export function handleCartResponse(successMessage) {
  showNotification(successMessage, true);
}

export function isUserAuthenticated() {
  return localStorage.getItem('userSession') !== null;
};

export function logout() {
  const logoutButton = document.getElementById('logout');
  logoutButton.addEventListener('click', () => {
    localStorage.removeItem('userSession');
    window.location.href = "http://localhost:8080/e-commerce/client/";
  });
}

export function handleButtonState(buttonId, isSuccess = true) {
  const button = document.getElementById(buttonId);
  if (isSuccess) {
    button.textContent = '¡Agregado!';
    button.classList.remove('bg-blue-500', 'hover:bg-violet-500');
    button.classList.add('bg-green-400');

    setTimeout(() => {
      button.textContent = 'Añadir al Carrito';
      button.classList.remove('bg-green-400');
      button.classList.add('bg-blue-500', 'hover:bg-violet-500');
    }, 2000);
  } else {
    button.textContent = 'Error al agregar';
    button.classList.remove('bg-blue-500', 'hover:bg-violet-500');
    button.classList.add('bg-red-400');

    setTimeout(() => {
      button.textContent = 'Añadir al Carrito';
      button.classList.remove('bg-red-400');
      button.classList.add('bg-blue-500', 'hover:bg-violet-500');
    }, 2000);
  }
}
